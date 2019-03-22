package vif.online.chungkhoan.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.services.InvestorTransService;

@Controller
@RequestMapping("investor-transaction")
public class InvestorTransController {
	@Autowired
	private InvestorTransService investorTransService;
	
	@GetMapping("buyCCQ")
	public ResponseEntity<ApiResponse> buyCCQ(Integer customerId, BigDecimal money,BigDecimal priceCCQ) {
		ApiResponse result = new ApiResponse();
		boolean isBuySuccess = investorTransService.buyCCQ(customerId, money, priceCCQ);
		if(isBuySuccess) {
			result.setCode(200);
			result.setStatus(true);
			result.setData("Buy CCQ success!");
		}else {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("Buy CCQ failed!");
		}
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}
	
	@GetMapping("sellCCQ")
	public ResponseEntity<ApiResponse> sellCCQ(Integer customerId, BigDecimal amountCCQ, BigDecimal priceCCQ) {
		ApiResponse result = new ApiResponse();
		boolean isSellSuccess = investorTransService.sellCCQ(customerId, amountCCQ, priceCCQ);
		if(isSellSuccess) {
			result.setCode(200);
			result.setStatus(true);
			result.setData("Sell CCQ success!");
		}else {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("Sell CCQ failed!");
		}
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}
}
