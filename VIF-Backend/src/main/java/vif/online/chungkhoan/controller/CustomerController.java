package vif.online.chungkhoan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.repositories.CustomerRepository;

@Controller
@RequestMapping("customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("getAlls")
	public ResponseEntity<List<Customer>> getAllUsers() {
		List<Customer> list = customerRepository.findAll();
		return new ResponseEntity<List<Customer>>(list, HttpStatus.OK);
	}
}
