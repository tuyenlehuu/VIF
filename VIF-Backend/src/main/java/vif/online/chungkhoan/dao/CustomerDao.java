package vif.online.chungkhoan.dao;

import java.math.BigDecimal;
import java.util.List;

import vif.online.chungkhoan.entities.Customer;

public interface CustomerDao {
List<Customer> getAllCustomers();
	
	Customer getCustomerById(int id);

	Customer getCustomerByCode(String code);

	boolean addCustomer(Customer customer);

	void updateCustomer(Customer customer);

	void deleteCustomerByCode(String code);

	void deleteCustomerById(Integer id);

	boolean updateCCQCustomer(Customer customer, BigDecimal newCCQPrice, BigDecimal newTotalCCQ);
}
