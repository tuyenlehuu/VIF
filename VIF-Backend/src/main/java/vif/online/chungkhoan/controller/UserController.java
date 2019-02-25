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
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.services.UserService;

/**
 * @author TUYENLH
 * UserController
 */

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/username/{username}")
	public ResponseEntity<User> getUserByUserName(@PathVariable("username") String username) {
		User user = userService.getUserByUserName(username);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getCophieuById(@PathVariable("id") Integer id) {
		User cp = userService.getUserById(id);
		return new ResponseEntity<User>(cp, HttpStatus.OK);
	}

	@GetMapping("getAlls")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> list = userService.getAllUsers();
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	@PostMapping("add")
	public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder builder) {
		boolean flag = userService.addUser(user);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@PutMapping("update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		userService.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@DeleteMapping("delete/{code}")
	public ResponseEntity<Void> deleteUser(@PathVariable("code") String userName) {
		userService.deleteUser(userName);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
