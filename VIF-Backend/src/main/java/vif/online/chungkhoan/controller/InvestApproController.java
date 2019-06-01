package vif.online.chungkhoan.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import vif.online.chungkhoan.entities.AppParam;
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
			@RequestParam(value = "typeOfInvest", required = false) Integer typeOfInvest,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "status", required = false) Integer status){
		ApiResponse object = new ApiResponse();
		List<InvestRequest> list = investApproService.SearchInvestRequestByCondition(page, pageSize, asc,
				typeOfRequest, typeOfInvest, fromDate, toDate, status);
		int rowCount = investApproService.getRowCount(typeOfRequest, typeOfInvest, fromDate, toDate, status);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		object.setPage(page);
		object.setPageSize(pageSize);
		object.setTotalRow(rowCount);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@DeleteMapping("reject/{id}")
	public ResponseEntity<Void> reject(@PathVariable("id") Integer id) {
		investApproService.reject(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@PutMapping("accept")
	public @ResponseBody ResponseEntity<ApiResponse> accept(@RequestBody InvestRequest investRequest) {
		
		investApproService.accept(investRequest);
		
		return new ResponseEntity<ApiResponse>(HttpStatus.OK);
	}
	
}
