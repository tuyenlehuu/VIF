package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	
	@GetMapping("username/{name}")
	public ResponseEntity<Customer> getCustomerByFullName(@PathVariable("name") String name) {
		Customer customer = customerService.getCustomerByCode(name);
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
}
