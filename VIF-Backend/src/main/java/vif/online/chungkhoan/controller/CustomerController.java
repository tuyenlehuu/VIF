package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.repositories.CustomerRepository;
import vif.online.chungkhoan.services.CustomerService;

@Controller
@RequestMapping("customer")
@CrossOrigin(origins= {"http://localhost:8080", "http://18.136.211.82:8080"})
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("getAlls")
	public ResponseEntity<List<Customer>> getAllUsers() {
		List<Customer> list = customerService.getAllCustomers();
		return new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
	}
}
