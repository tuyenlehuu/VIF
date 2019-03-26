package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.Customer;

public interface CustomerService {
	List<Customer> getAllCustomers();
	
	Customer getCustomerById(int id);

	Customer getCustomerByCode(String code);

	boolean addCustomer(Customer customer);

	void updateCustomer(Customer customer);

	void deleteCustomerByCode(String code);

	void deleteCustomerById(Integer id);

}
