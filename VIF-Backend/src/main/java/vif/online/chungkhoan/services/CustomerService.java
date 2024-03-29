package vif.online.chungkhoan.services;

import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import vif.online.chungkhoan.entities.Customer;

import vif.online.chungkhoan.entities.User;

public interface CustomerService {
	List<Customer> getAllCustomers();

	Customer getCustomerById(int id);

	Customer getCustomerByCode(String code);

	boolean addCustomer(Customer customer);

	void updateCustomer(Customer customer);

	void deleteCustomerByCode(String code);

	void deleteCustomerById(Integer id);

	List<User> getListUserById(int id);

	List<Customer> SearchCustomerByCondition(int page, int pageSize, String columnSortName, Boolean asc, String code,

			String fullName, Integer activeFlg, String email);

	int getRowCount(String fullName, Integer activeFlg, String code, String email);

	String saveFileAvatar(MultipartFile file);

	String saveFileDocBack(MultipartFile file);

	String saveFileDocFront(MultipartFile file);


}
