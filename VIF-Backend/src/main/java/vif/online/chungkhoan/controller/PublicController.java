package vif.online.chungkhoan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.services.UserService;

@Controller
@RequestMapping("public")
public class PublicController {
	@Autowired
	private UserService userService;
	
	@PostMapping("register")
	public ResponseEntity<ApiResponse> addUser(@RequestBody User user, UriComponentsBuilder builder) {
		boolean flag = userService.addUser(user);
		ApiResponse response = new ApiResponse();
		if (flag == false) {
			response.setCode(409);
			response.setErrors("User is exists!");
			response.setStatus(false);
			return new ResponseEntity<ApiResponse>(response, HttpStatus.CONFLICT);
		}
		
		response.setCode(200);
		response.setData("Register success");
		response.setStatus(true);
		return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("test")
	public ResponseEntity<Void> test() {
		System.out.println("test success!");
		return new ResponseEntity<Void>(new HttpHeaders(), HttpStatus.OK);
	}
}
