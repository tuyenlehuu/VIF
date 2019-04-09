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
import org.springframework.web.util.UriComponentsBuilder;

import vif.online.chungkhoan.entities.Branch;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.services.BranchService;

@Controller
@RequestMapping("branch")
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	@GetMapping("getAlls")
	public ResponseEntity<List<Branch>> getAllBranchs() {
		List<Branch> list = branchService.getAllBranchs();
		return new ResponseEntity<List<Branch>>(list, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Branch> getBranchById(@PathVariable("id") Integer id) {
		Branch cp = branchService.getBranchById(id);
		return new ResponseEntity<Branch>(cp, HttpStatus.OK);
	}
	@GetMapping("/code/{code}")	
	public ResponseEntity<Branch> getUserByUserName(@PathVariable("code") String code) {
		Branch branch = branchService.getBranchByCode(code);
		return new ResponseEntity<Branch>(branch, HttpStatus.OK);
	}
	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addBranch(@RequestBody Branch branch, UriComponentsBuilder builder) {
		boolean flag = branchService.addBranch(branch);
		ApiResponse object=new ApiResponse();
		if (flag == false) {
			object.setCode(409);
			object.setStatus(true);
			return new ResponseEntity<ApiResponse>(object,HttpStatus.OK);
		}
		object.setCode(200);
		object.setStatus(true);
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(builder.path("/branch/{id}").buildAndExpand(branch.getId()).toUri());
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}
	@PutMapping("update")
	public ResponseEntity<Branch> updateBranch(@RequestBody Branch branch) {
		branchService.updateBranch(branch);
		return new ResponseEntity<Branch>(branch, HttpStatus.OK);
	}
	@DeleteMapping("deleteByName/{code}")
	public ResponseEntity<Void> deleteBranchByUsername(@PathVariable("code") String code) {
		branchService.deleteBranchByCode(code);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@DeleteMapping("deleteById/{id}")
	public ResponseEntity<Void> deleteBranchById(@PathVariable("id") Integer id) {
		branchService.deleteBranchById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
}
