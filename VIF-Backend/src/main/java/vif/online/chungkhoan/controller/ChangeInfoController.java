package vif.online.chungkhoan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.IContaints;
import vif.online.chungkhoan.services.ChangeInfoService;

@Controller
@RequestMapping("change-info")
@CrossOrigin(origins= {"http://localhost:8080", "http://18.136.211.82:8080"})
public class ChangeInfoController {
	
	@Autowired
	private ChangeInfoService changeInfoService;
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
		User cp = changeInfoService.getUserById(id);
		return new ResponseEntity<User>(cp, HttpStatus.OK);
	}
	
	@PutMapping("update")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		changeInfoService.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@PostMapping("uploadAvatar")
	public ResponseEntity<String> saveFileAvatar(MultipartFile file) {
		if (file.isEmpty()) {
			return new ResponseEntity<String>("empty file", HttpStatus.OK);
		}

		if (file.getSize() > IContaints.FILE_UPLOAD.MAX_SIZE_FILE) {
			return new ResponseEntity<String>("size too limited", HttpStatus.OK);
		}

		String status = changeInfoService.saveFileAvatar(file);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
}
