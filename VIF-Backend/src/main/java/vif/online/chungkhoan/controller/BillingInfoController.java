package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import vif.online.chungkhoan.entities.BillingInfo;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.services.BillingInfoService;

@Controller
@RequestMapping("billing_info")
@CrossOrigin(origins= {"*"})
public class BillingInfoController {

	@Autowired
	private BillingInfoService billingInfoService;
	
	@GetMapping("id/{id}")
	public ResponseEntity<BillingInfo> getById(@PathVariable("id") int id) {
		BillingInfo bInfo = billingInfoService.getById(id);
		return new ResponseEntity<BillingInfo>(bInfo, HttpStatus.OK);
	}

	@GetMapping("getAlls")
	public ResponseEntity<List<BillingInfo>> getAlls() {
		List<BillingInfo> list = billingInfoService.getAlls();

		return new ResponseEntity<List<BillingInfo>>(list, HttpStatus.OK);
	}

	@PostMapping("add")
	public ResponseEntity<BillingInfo> add(@RequestBody BillingInfo bInfo) {
		billingInfoService.addBillingInfo(bInfo);
		return new ResponseEntity<BillingInfo>(bInfo, HttpStatus.OK);
	}

	@PutMapping("update")
	public ResponseEntity<BillingInfo> update(@RequestBody BillingInfo bInfo) {
		billingInfoService.updateBillingInfo(bInfo);
		return new ResponseEntity<BillingInfo>(bInfo, HttpStatus.OK);
	}

	@DeleteMapping("deleteById/{id}")
	public ResponseEntity<Void> deleteCustomerByCode(@PathVariable("id") int id) {
		billingInfoService.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	@GetMapping("getByCondition")
	public ResponseEntity<ApiResponse> SearchByCondition(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "accountName", required = false) String accountName,
			@RequestParam(value = "activeFlg", required = false) Integer activeFlg,
			@RequestParam(value = "bankName", required = false) String bankName,
			@RequestParam(value = "bankBranch", required = false) String bankBranch,
			@RequestParam(value = "bankAccount", required = false) String bankAccount ) {
		ApiResponse object = new ApiResponse();
		List<BillingInfo> list = billingInfoService.SearchByCondition(page, pageSize, asc, accountName, 
				bankName, bankBranch, bankAccount, activeFlg);
		int rowCount = billingInfoService.getRowCount(accountName, bankName, bankBranch, bankAccount, activeFlg);
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
