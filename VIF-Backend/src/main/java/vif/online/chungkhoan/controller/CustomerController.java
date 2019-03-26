package vif.online.chungkhoan.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.repositories.CustomerRepository;
import vif.online.chungkhoan.services.CustomerService;

@Controller
@RequestMapping("customer")
public class CustomerController {

	private static final String UPLOAD_DIRECTORY = "D:\\VIF\\DB Diagram\\server\\";
	private static final int MAX_SIZE_FILE = 1024*1024*5;
	

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
		boolean flag = customerService.addCustomer(customer);
		if (flag == false) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
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

	@PostMapping("upFile")
	public ResponseEntity<String> saveFile(@RequestParam("file") MultipartFile file) {

		String path = UPLOAD_DIRECTORY;
		if (file.isEmpty()) {
			return new ResponseEntity<String>("empty file", HttpStatus.OK);
		}
		
		if (file.getSize() > MAX_SIZE_FILE) {
			return new ResponseEntity<String>("size too limited", HttpStatus.OK);
		}
		try {
			String filename = file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path + filename)));
			stream.write(bytes);
			stream.flush();
			stream.close();
			

		} catch (IOException e) {
			return new ResponseEntity<String>("something went wrong", HttpStatus.OK);
		}

		return new ResponseEntity<String>("You successfully uploaded!", HttpStatus.OK);
	}

}
