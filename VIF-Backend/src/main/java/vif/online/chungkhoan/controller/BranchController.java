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

import vif.online.chungkhoan.entities.Branch;
import vif.online.chungkhoan.entities.User;
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
		ApiResponse object = new ApiResponse();
		if (flag == false) {
			object.setCode(409);
			object.setStatus(true);
			return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
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

	@GetMapping("getBranchsByCondition")
	public ResponseEntity<ApiResponse> SearchBranchByCondition(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "columnSortName", required = false) String columnSortName,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "branchCode", required = false) String branchCode,
			@RequestParam(value = "activeFlg", required = false) Integer activeFlg,
			@RequestParam(value = "branchName", required = false) String branchName) {
		ApiResponse object = new ApiResponse();

		int rowCount = branchService.getRowCount(branchCode, activeFlg, branchName);
		List<Branch> list = branchService.SearchBranchByCondition(branchCode, activeFlg, branchName);

		List<Branch> list = branchService.SearchBranchByCondition(page, pageSize, columnSortName, asc, branchCode, activeFlg, branchName);
		int rowCount = branchService.getRowCount(branchCode, activeFlg, branchName);

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
