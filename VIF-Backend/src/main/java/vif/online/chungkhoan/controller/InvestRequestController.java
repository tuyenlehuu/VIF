package vif.online.chungkhoan.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.AssetHistory;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.services.CustomerService;
import vif.online.chungkhoan.services.InvestRequestService;

@Controller
@RequestMapping("invest_request")
@CrossOrigin(origins = { "*" })
public class InvestRequestController {

	@Autowired
	private InvestRequestService investRequestService;

	@GetMapping("getAlls")
	public ResponseEntity<List<InvestRequest>> getAllInvestRequest() {
		List<InvestRequest> list = investRequestService.getAllInvestRequest();

		return new ResponseEntity<List<InvestRequest>>(list, HttpStatus.OK);
	}

	@GetMapping("id/{id}")
	public ResponseEntity<InvestRequest> getInvestRequestById(@PathVariable("id") Integer id) {
		InvestRequest iq = investRequestService.getInvestRequestById(id);
		return new ResponseEntity<InvestRequest>(iq, HttpStatus.OK);
	}

	@GetMapping("getInvestRequestsByCondition")
	public ResponseEntity<ApiResponse> SearchInvestRequestByCondition(
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "typeOfRequest", required = false) Integer typeOfRequest,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		ApiResponse object = new ApiResponse();
		List<InvestRequest> list = investRequestService.SearchInvestRequestByCondition(page, pageSize, asc,
				typeOfRequest, status, fromDate, toDate);
		int rowCount = investRequestService.getRowCount(typeOfRequest, status, fromDate, toDate);
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		object.setPage(page);
		object.setPageSize(pageSize);
		object.setTotalRow(rowCount);
		return new ResponseEntity<ApiResponse>(object, HttpStatus.OK);
	}

	@GetMapping("getPriceCCQ")
	public ResponseEntity<BigDecimal> getPriceCCQ() {
		BigDecimal asset = investRequestService.getPriceMaxDate();

		return new ResponseEntity<BigDecimal>(asset, HttpStatus.OK);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<InvestRequest> add(@RequestBody InvestRequest request, UriComponentsBuilder builder) {
		boolean bol = investRequestService.addInvestRequest(request);
		if (bol) {
			return new ResponseEntity<InvestRequest>(request, HttpStatus.OK);
		} else {
			request = null;
			return new ResponseEntity<InvestRequest>(request, HttpStatus.OK);
		}

	}

	@PutMapping("update")
	public ResponseEntity<InvestRequest> updateInvestRequest(@RequestBody InvestRequest request) {
		investRequestService.updateInvestRequest(request);
		return new ResponseEntity<InvestRequest>(request, HttpStatus.OK);
	}

	@GetMapping("getEnsureCCQByCusAsset/{customerId}/{assetCode}")
	public ResponseEntity<ApiResponse> getEnsureCCQByCusAsset(@PathVariable("customerId") Integer customerId,
			@PathVariable("assetCode") String assetCode) {
		ApiResponse result = new ApiResponse();

		if (customerId == null || assetCode == null) {
			result.setCode(500);
			result.setStatus(false);
			result.setErrors("wrong parameters!");
			return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
		}
		result = investRequestService.getEnsureCCQByCusAsset(customerId, assetCode);
		return new ResponseEntity<ApiResponse>(result, HttpStatus.OK);
	}

	@GetMapping("getCustomerByUsername")
	public ResponseEntity<Customer> getCustomerByUsername(
			@RequestParam(value = "userName", required = true) String userName) {
		Customer customer = investRequestService.getCustomerByUsername(userName);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

}
