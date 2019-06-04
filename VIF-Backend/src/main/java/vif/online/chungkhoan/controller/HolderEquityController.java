package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import io.netty.handler.codec.http.HttpHeaders;
import vif.online.chungkhoan.entities.Branch;
import vif.online.chungkhoan.entities.HolderEquity;
import vif.online.chungkhoan.entities.TransactionHistory;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.services.HolderEquityService;

@Controller
@RequestMapping("holder")
public class HolderEquityController {

	@Autowired
	private HolderEquityService holderEquityService;

	@GetMapping("getAlls")
	public ResponseEntity<ApiResponse> getAllsHolder() {
		ApiResponse object = new ApiResponse();
		List<HolderEquity> list = holderEquityService.getAllsHolder();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<HolderEquity> getHolderById(@PathVariable("id") Integer id) {
		HolderEquity cp = holderEquityService.getHolderbyId(id);
		return new ResponseEntity<HolderEquity>(cp, HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addHolder(@RequestBody HolderEquity holderEquity, UriComponentsBuilder builder) {
		boolean flag = holderEquityService.addHolder(holderEquity);
		ApiResponse object = new ApiResponse();
		if (flag == false) {
			object.setCode(409);
			object.setStatus(true);
			return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
		}
		object.setCode(200);
		object.setStatus(true);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(builder.path("/holder/{id}").buildAndExpand(holder.getId()).toUri());
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}

	@PutMapping("update")
	public ResponseEntity<HolderEquity> updateHolder(@RequestBody HolderEquity holderEquity) {
		holderEquityService.updateHolder(holderEquity);
		return new ResponseEntity<HolderEquity>(holderEquity, HttpStatus.OK);
	}

	@DeleteMapping("deleteById/{id}")
	public ResponseEntity<Void> deleteHolderById(@PathVariable("id") Integer id) {
		holderEquityService.deleteHolderById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@GetMapping("getHoldersByCondition")
	public ResponseEntity<ApiResponse> SearchInvestorHistoryByCondition(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "columnSortName", required = false) String columnSortName,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "fullName", required = false) String fullName){
		ApiResponse object = new ApiResponse();
		List<HolderEquity> list = holderEquityService.SearchHolderByCondition(page, pageSize, columnSortName, asc, fromDate, toDate, fullName);
		int rowCount = holderEquityService.getRowCount(fromDate, toDate, fullName);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		object.setPage(page);
		object.setPageSize(pageSize);
		object.setTotalRow(rowCount);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	
	

}
