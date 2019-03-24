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

import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.repositories.CustomerRepository;
import vif.online.chungkhoan.services.CustomerService;

@Controller
@RequestMapping("customer")
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
	
	
	@GetMapping("fullname/{name}")
	public ResponseEntity<Customer> getCustomerByFullName(@PathVariable("name") String name) {
		Customer customer = customerService.getCustomerByFullName(name);
		return new ResponseEntity(customer, HttpStatus.OK);
	}


	@GetMapping("getAlls")
	public ResponseEntity<List<Customer>> getAllUsers() {
		ApiResponse object = new ApiResponse();
		List<Customer> list = customerService.getAllCustomers();
		object.setCode(200);
		object.setErrors(null);
		object.setStatus(true);
		object.setData(list);
		return new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
	}
	
	@PostMapping("add")
	public ResponseEntity<Void> addUser(@RequestBody Customer customer, UriComponentsBuilder builder) {
		boolean flag = customerService.addCustomer(customer);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/customer/id/{id}").buildAndExpand(customer.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.OK);
	}
	
	@PutMapping("update")
	public ResponseEntity<Customer> updateUser(@RequestBody Customer customer) {
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
	
}
