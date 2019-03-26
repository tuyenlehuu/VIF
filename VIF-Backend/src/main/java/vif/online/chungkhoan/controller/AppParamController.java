package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.util.UriComponentsBuilder;

import vif.online.chungkhoan.entities.AppParam;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.repositories.AppParamRepository;
import vif.online.chungkhoan.services.AppParamService;

@Controller
@RequestMapping("app_param")
public class AppParamController {
	@Autowired
	private AppParamService appParamService;
	
//	@Autowired
//	private AppParamRepository appParamRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<AppParam> getAppParamById(@PathVariable("id") Integer id) {
		AppParam cp = appParamService.getAppParamById(id);
		return new ResponseEntity<AppParam>(cp, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Void> addAppParan(@RequestBody AppParam appParam, UriComponentsBuilder builder) {
		boolean flag = appParamService.addAppParam(appParam);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/appParam/{id}").buildAndExpand(appParam.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	
	@PutMapping("update/{id}")
	public ResponseEntity<AppParam> updateAppParam(@RequestBody AppParam appParam) {
		appParamService.updateAppParam(appParam);
		return new ResponseEntity<AppParam>(appParam, HttpStatus.OK);
	}
	
	@DeleteMapping("deleteById/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable("id") Integer id) {
//		appParamRepository.deleteById(id);
		// update lai column active flag
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@GetMapping("getAlls")
	public ResponseEntity<ApiResponse> getAllAppParam() {
		ApiResponse object = new ApiResponse();
		List<AppParam> list = appParamService.getAllAppParam();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
}
