package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.services.UserService;

/**
 * @author TUYENLH UserController
 */

@Controller
@RequestMapping("user")
@CrossOrigin(origins= {"http://localhost:8080", "http://18.136.211.82:8080"})
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUserName(@PathVariable("username") String username) {
		User user = userService.getUserByUserName(username);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
		User cp = userService.getUserById(id);
		return new ResponseEntity<User>(cp, HttpStatus.OK);
	}

	@GetMapping("getAlls")
	public ResponseEntity<ApiResponse> getAllUsers() {
		ApiResponse object = new ApiResponse();
		List<User> list = userService.getAllUsers();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}

	@GetMapping("getUsersByCondition")
	public ResponseEntity<ApiResponse> SearchUserByCondition(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "columnSortName", required = false) String columnSortName,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "activeFlg", required = false) Integer activeFlg,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "role", required = false) String role) {
		ApiResponse object = new ApiResponse();
		List<User> list = userService.SearchUserByCondition(page, pageSize, columnSortName, asc, username, activeFlg, email, role);
		int rowCount = userService.getRowCount(username, activeFlg, email, role);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		object.setPage(page);
		object.setPageSize(pageSize);
		object.setTotalRow(rowCount);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}

	@PostMapping("add")
	public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder builder) {
		boolean flag = userService.addUser(user);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@PutMapping("update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		userService.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@DeleteMapping("deleteByName/{username}")
	public ResponseEntity<Void> deleteUserByUsername(@PathVariable("username") String userName) {
		userService.deleteUserByUsername(userName);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping("deleteById/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable("id") Integer id) {
		userService.deleteUserById(id);
//		userService.deleteUserById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
