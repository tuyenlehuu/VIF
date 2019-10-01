package vif.online.chungkhoan.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.Stock;
import vif.online.chungkhoan.entities.StockTracking;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.IContaints;
import vif.online.chungkhoan.helper.SlackMessage;
import vif.online.chungkhoan.helper.SlackUtils;
import vif.online.chungkhoan.services.StockTradingService;

@Controller
@RequestMapping("stock-tracking")
@CrossOrigin(origins= {"*"})
public class StockTrackingController {
	
	@Autowired
	private StockTradingService stockTrackingService;
	
	@GetMapping("/stock-code/{stockCode}")
	public ResponseEntity<StockTracking> getStockTrackingByCode(@PathVariable("stockCode") String stockCode) {
		StockTracking stockTracking = stockTrackingService.getStockTrackingByCode(stockCode);
		return new ResponseEntity<StockTracking>(stockTracking, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<StockTracking> getUserById(@PathVariable("id") Integer id) {
		StockTracking cp = stockTrackingService.getStockTrackById(id);
		return new ResponseEntity<StockTracking>(cp, HttpStatus.OK);
	}

	@GetMapping("getAlls")
	public ResponseEntity<ApiResponse> getAllStockTracking() {
		ApiResponse object = new ApiResponse();
		List<StockTracking> list = stockTrackingService.getAllStockTracking();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@GetMapping("getStockTrackingByCondition")
	public ResponseEntity<ApiResponse> getStockTrackingByCondition(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "columnSortName", required = false) String columnSortName,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "stockCode", required = false) String stockCode) {
		ApiResponse object = new ApiResponse();
		List<StockTracking> list = stockTrackingService.getStockTrackingByCondition(page, pageSize, columnSortName, asc, stockCode);
		int rowCount = stockTrackingService.getRowCount(stockCode); 
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		object.setPage(page);
		object.setPageSize(pageSize);
		object.setTotalRow(rowCount);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<ApiResponse> addStockTracking(@RequestBody StockTracking stockTracking) {
		
		Stock stock = getStockInfoFromMarket(IContaints.STOCK_API.URL, stockTracking.getCode());
		if(stock != null && stock.getLastPrice() != null) {
			stockTracking.setCreateDate(new Date());
			stockTracking.setLastUpdate(new Date());
			stockTracking.setCurrentPrice(new BigDecimal(stock.getLastPrice()).divide(new BigDecimal(1000)));
			stockTracking.setOrginalPrice(new BigDecimal(stock.getLastPrice()).divide(new BigDecimal(1000)));
			stockTracking.setStatus(1);
		}
		
		boolean flag = stockTrackingService.addStockTracking(stockTracking);
		ApiResponse object = new ApiResponse();
		if (flag == false) {
			object.setCode(409);
			object.setErrors("Stock tracking is exists!");
			object.setStatus(false);
			return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
		}
		
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@PutMapping("update")
	public ResponseEntity<StockTracking> updateStockTracking(@RequestBody StockTracking stockTracking) {
		stockTrackingService.updateStockTracking(stockTracking);
		return new ResponseEntity<StockTracking>(stockTracking, HttpStatus.OK);
	}

	@DeleteMapping("deleteByCode/{stockCode}")
	public ResponseEntity<Void> deleteStockTrackingByCode(@PathVariable("stockCode") String stockCode) {
		stockTrackingService.deleteStockTrackingByCode(stockCode);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping("deleteById/{id}")
	public ResponseEntity<Void> deleteStockTrackingById(@PathVariable("id") Integer id) {
		stockTrackingService.deleteStockTrackingById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("get-price-from-market")
	public ResponseEntity<Void> updateStockTrackingFromMarket() {
		try {
			String urlStockMarket = IContaints.STOCK_API.URL;
			List<String> buyStockLst = new ArrayList<String>();
			List<String> sellStockLst = new ArrayList<String>();
			
			// get list co phieu trong bang asset
			List<StockTracking> stockTrackingList = stockTrackingService.getAllStockTracking();
			
			for (StockTracking stockTracking : stockTrackingList) {
				Stock mStock = getStockInfoFromMarket(urlStockMarket, stockTracking.getCode());
				if(mStock != null && mStock.getLastPrice() != null) {
					stockTracking.setCurrentPrice(new BigDecimal(mStock.getLastPrice()).divide(new BigDecimal("1000")));
					stockTrackingService.updateStockTracking(stockTracking);
					
					BigDecimal rangeRate = new BigDecimal(1.04);
					
					if(stockTracking.getTargetPriceBuy() != null) {
						if((stockTracking.getTargetPriceBuy().multiply(rangeRate)).compareTo(stockTracking.getCurrentPrice()) >= 0) {
							buyStockLst.add(stockTracking.getCode());
						}
					}
					
					if(stockTracking.getTargetPriceSell() != null) {
						if(stockTracking.getTargetPriceSell().compareTo(stockTracking.getCurrentPrice().multiply(rangeRate)) <= 0) {
							sellStockLst.add(stockTracking.getCode());
						}
					}
				}
			}
			
			if(buyStockLst != null && buyStockLst.size()>0) {
				String contentBuyNotice = "Thông báo: \n";
				
				for (String itemBuy : buyStockLst) {
					contentBuyNotice += itemBuy + " đã đến vùng mua! \n";
				}
				pushTextToSlack(contentBuyNotice);
			}
			
			if(sellStockLst != null && sellStockLst.size()>0) {
				String contentSellNotice = "Thông báo: \n";
				for (String itemSell : sellStockLst) {
					contentSellNotice += itemSell + " đã đến vùng bán! \n";
				}
				pushTextToSlack(contentSellNotice);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	private static Stock getStockInfoFromMarket(String mUrl, String code) {
		URL url;
		System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		Stock mStock = null;

		try {
			url = new URL(mUrl);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			Gson gson = new Gson();
			if (con != null) {
				con.setRequestMethod("POST");
				con.setDoOutput(true);

				PrintStream ps = new PrintStream(con.getOutputStream());
				String data = "code=" + code + "&s=1&t=";
				ps.println(data);
				ps.close();
				con.connect();

				if(con.getResponseCode() == HttpsURLConnection.HTTP_OK) {
					BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String result = reader.readLine();
					mStock = gson.fromJson(result, Stock.class);
				}
				con.disconnect();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return mStock;
	}
	
	private static void pushTextToSlack(String text) {
		SlackMessage slackMessage = SlackMessage.builder()
				.username("TuyenLH")
				.text(text)
				.icon_emoji(":twice:")
				.build();
		SlackUtils.sendMessage(slackMessage);
	}
}
