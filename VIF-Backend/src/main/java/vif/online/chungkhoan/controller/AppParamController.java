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

import vif.online.chungkhoan.entities.AppParam;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.ApiResponse;
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
	
	@GetMapping("getAppParamsByCondition")
	public ResponseEntity<ApiResponse> SearchAppParamByCondition(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "columnSortName", required = false) String columnSortName,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "propKey", required = false) String propKey,
			@RequestParam(value = "activeFlg", required = false) Integer activeFlg,
			@RequestParam(value = "propType", required = false) String propType,
			@RequestParam(value = "propValue", required = false) String propValue,
			@RequestParam(value = "description", required = false) String description){
		ApiResponse object = new ApiResponse();
		List<AppParam> list = appParamService.SearchAppParamByCondition(page, pageSize, columnSortName, asc, propKey, activeFlg, propType, propValue, description);
		int rowCount = appParamService.getRowCount(propKey, activeFlg, propType, propValue, description);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		object.setPage(page);
		object.setPageSize(pageSize);
		object.setTotalRow(rowCount);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addAppParan(@RequestBody AppParam appParam, UriComponentsBuilder builder) {
		boolean flag = appParamService.addAppParam(appParam);
		ApiResponse object = new ApiResponse();
		if (flag == false) {
			object.setCode(409);
			object.setErrors(null);
			object.setStatus(true);
			object.setData(null);
			return new ResponseEntity<ApiResponse>(object,HttpStatus.OK);
		}
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(builder.path("/appParam/{id}").buildAndExpand(appParam.getId()).toUri());
		object.setCode(200);
		object.setData("them moi thanh cong");
		object.setStatus(true);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	
	@PutMapping("update")
	public ResponseEntity<AppParam> updateAppParam(@RequestBody AppParam appParam) {
		appParamService.updateAppParam(appParam);
		return new ResponseEntity<AppParam>(appParam, HttpStatus.OK);
	}
	
	@DeleteMapping("deleteById/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable("id") Integer id) {
		appParamService.deleteAppParamById(id);
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
