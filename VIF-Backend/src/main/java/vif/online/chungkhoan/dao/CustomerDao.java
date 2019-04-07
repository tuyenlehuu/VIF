package vif.online.chungkhoan.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.User;

public interface CustomerDao {
	List<Customer> getAllCustomers();

	Customer getCustomerById(int id);

	Customer getCustomerByCode(String code);

	boolean addCustomer(Customer customer);

	void updateCustomer(Customer customer);

	void deleteCustomerByCode(String code);

	void deleteCustomerById(Integer id);


	boolean updateCCQCustomer(Customer customer);


	List<User> getListUserById(int id);

	List<Customer> SearchCustomerByCondition(int page, int pageSize, String columnSortName, Boolean asc, String code,
			String fullName, Integer activeFlg);

	int getRowCount(String fullName, Integer activeFlg, String code);


	BigDecimal getTotalMoneyOfCustomers();


}
