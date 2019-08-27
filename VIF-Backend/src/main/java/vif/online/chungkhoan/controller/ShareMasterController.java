package vif.online.chungkhoan.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.ShareMaster;
import vif.online.chungkhoan.entities.Stock;
import vif.online.chungkhoan.helper.IContaints;
import vif.online.chungkhoan.services.AssetService;
import vif.online.chungkhoan.services.ShareMasterService;

@Controller
@RequestMapping("cophieu")
@CrossOrigin(origins= {"*"})
public class ShareMasterController {

	@Autowired
	private ShareMasterService cophieuService;
	
	@Autowired
	private AssetService assetService;

	@Autowired
	private Environment env;

	@GetMapping("/code/{code}")
	public ResponseEntity<ShareMaster> getCophieuByCode(@PathVariable("code") String code) {
		ShareMaster cp = cophieuService.getCophieuByCode(code);
		return new ResponseEntity<ShareMaster>(cp, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ShareMaster> getCophieuById(@PathVariable("id") Integer id) {
		ShareMaster cp = cophieuService.getCophieuById(id);
		return new ResponseEntity<ShareMaster>(cp, HttpStatus.OK);
	}

	@GetMapping("getAlls")
	public ResponseEntity<List<ShareMaster>> getAllCophieu() {
		List<ShareMaster> list = cophieuService.getAllCophieu();
		return new ResponseEntity<List<ShareMaster>>(list, HttpStatus.OK);
	}

	@PostMapping("add")
	public ResponseEntity<Void> addArticle(@RequestBody ShareMaster cp, UriComponentsBuilder builder) {
		boolean flag = cophieuService.addCophieu(cp);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/cophieu/{code}").buildAndExpand(cp.getCpCode()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("update")
	public ResponseEntity<ShareMaster> updateUser(@RequestBody ShareMaster cp) {
		cophieuService.updateCophieu(cp);
		return new ResponseEntity<ShareMaster>(cp, HttpStatus.OK);
	}

	@DeleteMapping("delete/{code}")
	public ResponseEntity<Void> deleteCophieu(@PathVariable("code") String cpCode) {
		cophieuService.deleteCophieu(cpCode);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@GetMapping("get-price-from-market")
	public ResponseEntity<Void> syncData() {
		try {
//			String urlOrigin = env.getProperty("urlOrigin");
//			syncDataCp(cophieuService, urlOrigin);
			String urlStockMarket = IContaints.STOCK_API.URL;
			
			// get list co phieu trong bang asset
			List<Asset> assetList = assetService.getAllShares();
			
			// lay ra gia tren thi truong
			List<Asset> assetListAfterUpdate = new ArrayList<Asset>();
			List<ShareMaster> shareListAfterUpdate = new ArrayList<>();
			
			for (Asset asset : assetList) {
				ShareMaster mShare = new ShareMaster();
				mShare.setCpCode(asset.getAssetCode());
				Stock mStock = getStockInfoFromMarket(urlStockMarket, asset.getAssetCode());
				if(mStock != null && mStock.getLastPrice() != null) {
					asset.setCurrentPrice(new BigDecimal(mStock.getLastPrice()).divide(new BigDecimal("1000")));
					assetListAfterUpdate.add(asset);
					
					mShare.setCpPrice(new BigDecimal(mStock.getLastPrice()).divide(new BigDecimal("1000")).toString());
					shareListAfterUpdate.add(mShare);
				}
			}
			
			// cap nhat gia vao bang asset va sharemaster
			if(assetListAfterUpdate != null && assetListAfterUpdate.size() >0) {
				for (Asset item : assetListAfterUpdate) {
					assetService.updateAsset(item);
				}
			}
			
			if(shareListAfterUpdate != null && shareListAfterUpdate.size() >0) {
				for (ShareMaster shareMaster : shareListAfterUpdate) {
					cophieuService.updateCophieu(shareMaster);
				}
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

//	public void syncDataCp(ShareMasterService cpService, String urlOrigin) throws IOException {
//		Document doc = Jsoup.connect(urlOrigin).maxBodySize(0).timeout(20000).get();
//		Elements elements = doc.select("tbody#sortable tr");
//		// elements.remove(0);
//		List<Element> lstElements = elements.subList(2, elements.size());
//		Map<String, ShareMaster> mapCophieu = new HashMap<>();
//		for (Element e : lstElements) {
//			ShareMaster cophieu = new ShareMaster();
//			Element td = e.select("td").get(0);
//			Elements aLst = td.getElementsByTag("a");
//			if (aLst != null && aLst.size() > 0) {
//				Element a = aLst.get(0);
//				if (a != null) {
//					String myId = a.html().toLowerCase() + "_close";
//					String companyName = a.attr("title");
//					Element price = e.getElementById(myId);
//					if (price != null) {
//						Element pr = price.select("td span").get(0);
//						cophieu.setCpCode(a.html());
//						cophieu.setCpName(companyName);
//						if (pr.html().isEmpty()) {
//							cophieu.setCpPrice("0");
//						} else {
//							cophieu.setCpPrice(pr.html());
//						}
//						cophieu.setLastUpdate(new Date());
//						mapCophieu.put(cophieu.getCpCode(), cophieu);
//					}
//				}
//			}
//		}
//		
//		List<ShareMaster> listFromDB = cophieuService.getAllCophieu();
//		if(listFromDB == null || listFromDB.size() <= 0) {
//			for(Map.Entry<String, ShareMaster> entry : mapCophieu.entrySet()) {
//				cpService.addCophieu(entry.getValue());
//			}
//		}else {
//			for (ShareMaster mCpDB : listFromDB) {
//				String currentPrice = mapCophieu.get(mCpDB.getCpCode()).getCpPrice();
//				if(currentPrice.equals(mCpDB.getCpPrice())) {
//					continue;
//				}else {
//					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//					System.out.println(mCpDB.getCpCode() + " Old Price: " + mCpDB.getCpPrice() + "; New Price: " + currentPrice + "; Time update: " + dateFormat.format(new Date()));
//					mCpDB.setCpPrice(currentPrice);
//					mCpDB.setLastUpdate(new Date());
//					cpService.updateCophieu(mCpDB);
//				}
//			}
//		}
//	}
	
	public static Stock getStockInfoFromMarket(String mUrl, String code) {
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
}
