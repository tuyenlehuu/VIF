package vif.online.chungkhoan.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.BuySellDTO;
import vif.online.chungkhoan.services.InvestApproService;

@Controller
@RequestMapping("invest-appro")
@CrossOrigin(origins= {"http://localhost:8080", "http://18.136.211.82:8080"})
public class InvestApproController {
	@Autowired
	private InvestApproService investApproService;
	
	@GetMapping("getAlls")
	public ResponseEntity<List<InvestRequest>> getAllInvestRequest() {
		List<InvestRequest> list = investApproService.getAllInvestRequest();

		return new ResponseEntity<List<InvestRequest>>(list, HttpStatus.OK);
	}
	
	@GetMapping("getInvestRequestsByCondition")
	public ResponseEntity<ApiResponse> SearchInvestRequestByCondition(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "typeOfRequest", required = false) Integer typeOfRequest,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate){
		ApiResponse object = new ApiResponse();
		List<InvestRequest> list = investApproService.SearchInvestRequestByCondition(page, pageSize, asc,
				typeOfRequest, fromDate, toDate);
		int rowCount = investApproService.getRowCount(typeOfRequest, fromDate, toDate);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		object.setPage(page);
		object.setPageSize(pageSize);
		object.setTotalRow(rowCount);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
//	@PostMapping("buyCCQ")
	@RequestMapping(value = "/buyCCQ", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<ApiResponse> buyCCQ(@RequestBody BuySellDTO buyObject) {
		ApiResponse result = new ApiResponse();
		if(buyObject==null || buyObject.getCustomerId() == null || buyObject.getMoney()==null || buyObject.getPriceCCQ() == null) {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("missing parameters!");
			return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
		}
		
		result = investApproService.buyCCQ(buyObject.getCustomerId(), buyObject.getMoney(), buyObject.getPriceCCQ());
		/*
		 * if(isBuySuccess) { result.setCode(200); result.setStatus(true);
		 * result.setData("Buy CCQ success!"); }else { result.setCode(500);
		 * result.setStatus(false); result.setErrors("Buy CCQ failed!"); }
		 */
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}
	
//	@PostMapping("sellCCQ")
	@RequestMapping(value = "/sellCCQ", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody ResponseEntity<ApiResponse> sellCCQ(@RequestBody BuySellDTO sellObject) {
		ApiResponse result = new ApiResponse();
		
		if(sellObject==null || sellObject.getCustomerId() == null || sellObject.getAmountCCQ() == null || (sellObject.getAmountCCQ()!=null && sellObject.getAmountCCQ().compareTo(new BigDecimal(0))<=0) || sellObject.getPriceCCQ() == null) {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("wrong parameters!");
			return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
		}
		
		result = investApproService.sellCCQ(sellObject.getCustomerId(), sellObject.getAmountCCQ(), sellObject.getPriceCCQ());
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}
}
