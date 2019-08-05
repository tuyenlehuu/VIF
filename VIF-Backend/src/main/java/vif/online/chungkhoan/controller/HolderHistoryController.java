package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import vif.online.chungkhoan.entities.HolderEquity;
import vif.online.chungkhoan.entities.HolderHistory;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.services.HolderEquityService;
import vif.online.chungkhoan.services.HolderHistoryService;

@Controller
@RequestMapping("holder_history")
@CrossOrigin(origins= {"*"})
public class HolderHistoryController {
	@Autowired
	private HolderHistoryService holderHistoryService;
	
	@Autowired
	private HolderEquityService holderEquityService;
	
	@GetMapping("getAlls")
	public ResponseEntity<ApiResponse> getAllsHolder() {
		ApiResponse object = new ApiResponse();
		List<HolderHistory> list = holderHistoryService.getAllsHolder();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	@PostMapping("/add/{id}")
	public ResponseEntity<ApiResponse> addHolder(@RequestBody HolderHistory holderHistory,@PathVariable("id") Integer id, UriComponentsBuilder builder) {
		HolderEquity holderEquity= holderEquityService.getHolderbyId(id);
		holderHistory.setHolderEquity(holderEquity);
		boolean flag = holderHistoryService.addHolder(holderHistory);
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
}
