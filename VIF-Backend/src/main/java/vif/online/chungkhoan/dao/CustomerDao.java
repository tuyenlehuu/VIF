package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.Customer;

public interface CustomerDao {
List<Customer> getAllCustomers();
	
	Customer getCustomerById(int id);

	Customer getCustomerByCode(String code);
	
	Customer getCustomerByFullName (String name);

	boolean addCustomer(Customer customer);

	void updateCustomer(Customer customer);

	void deleteCustomerByCode(String code);

	void deleteCustomerById(Integer id);
}
