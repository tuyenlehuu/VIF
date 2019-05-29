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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.IContaints;
import vif.online.chungkhoan.repositories.CustomerRepository;
import vif.online.chungkhoan.services.CustomerService;

@Controller
@RequestMapping("customer")
@CrossOrigin(origins = { "http://localhost:8080", "http://18.136.211.82:8080" })
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CustomerRepository customerRepository;

	@GetMapping("id/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") int id) {
		Customer customer = customerService.getCustomerById(id);
		return new ResponseEntity(customer, HttpStatus.OK);
	}
	

	@GetMapping("code/{code}")
	public ResponseEntity<Customer> getCustomerByCode(@PathVariable("code") String code) {
		Customer customer = customerService.getCustomerByCode(code);
		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@GetMapping("getAlls")
	public ResponseEntity<List<Customer>> getAllCustomer() {
		List<Customer> list = customerService.getAllCustomers();

		return new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
	}

	@PostMapping("add")
	public ResponseEntity<Void> addCustomer(@RequestBody Customer customer, UriComponentsBuilder builder) {
		customerService.addCustomer(customer);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/customer/id/{id}").buildAndExpand(customer.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}

	@PutMapping("update")
	public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
		customerService.updateCustomer(customer);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}

	@DeleteMapping("deleteByCode/{code}")
	public ResponseEntity<Void> deleteCustomerByCode(@PathVariable("code") String code) {
		customerService.deleteCustomerByCode(code);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@DeleteMapping("deleteById/{id}")
	public ResponseEntity<Void> deleteCustomerByCode(@PathVariable("id") int id) {
		customerService.deleteCustomerById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}


	@PostMapping("upFileDocBack")
	public ResponseEntity<String> saveFileDocBack(MultipartFile file) {
		if (file.isEmpty()) {
			return new ResponseEntity<String>("empty file", HttpStatus.OK);
		}

		if (file.getSize() > IContaints.FILE_UPLOAD.MAX_SIZE_FILE) {
			return new ResponseEntity<String>("size too limited", HttpStatus.OK);
		}

		String status = customerService.saveFileDocBack(file);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}

	@PostMapping("upFileDocFront")
	public ResponseEntity<String> saveFileDocFront(MultipartFile file) {

		if (file.isEmpty()) {
			return new ResponseEntity<String>("empty file", HttpStatus.OK);
		}

		if (file.getSize() > IContaints.FILE_UPLOAD.MAX_SIZE_FILE) {
			return new ResponseEntity<String>("size too limited", HttpStatus.OK);
		}


		String status = customerService.saveFileDocFront(file);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}


	@GetMapping("UsersById/{id}")
	public ResponseEntity<List<User>> getListUserById(@PathVariable("id") int id) {
		List<User> users = customerService.getListUserById(id);
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);

	}

	@GetMapping("getCustomersByCondition")
	public ResponseEntity<ApiResponse> SearchUserByCondition(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "columnSortName", required = false) String columnSortName,
			@RequestParam(value = "asc", required = false) Boolean asc,
			@RequestParam(value = "fullName", required = false) String fullName,
			@RequestParam(value = "activeFlg", required = false) Integer activeFlg,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "email", required = false) String email) {
		ApiResponse object = new ApiResponse();
		List<Customer> list = customerService.SearchCustomerByCondition(page, pageSize, columnSortName, asc, code,
				fullName, activeFlg, email);
		int rowCount = customerService.getRowCount(fullName, activeFlg, code, email);

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
