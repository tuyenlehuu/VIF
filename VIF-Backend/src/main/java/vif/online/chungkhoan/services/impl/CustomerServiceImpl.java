package vif.online.chungkhoan.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.services.CustomerService;
import vif.online.chungkhoan.entities.User;
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return customerDao.getAllCustomers();
	}

	@Override
	public Customer getCustomerById(int id) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerById(id);
	}

	@Override
	public Customer getCustomerByCode(String code) {
		// TODO Auto-generated method stub
		return customerDao.getCustomerByCode(code);
	}

	@Override
	public boolean addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return customerDao.addCustomer(customer);
	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		customerDao.updateCustomer(customer);
	}

	@Override
	public void deleteCustomerByCode(String code) {
		// TODO Auto-generated method stub
		customerDao.deleteCustomerByCode(code);
	}

	@Override
	public void deleteCustomerById(Integer id) {
		// TODO Auto-generated method stub
		customerDao.deleteCustomerById(id);
	}
	
	@Override
	public List<User> getListUserById(int id){
		return customerDao.getListUserById(id);
	}
	
	@Override
	public List<Customer> SearchCustomerByCondition(int page, int pageSize, String columnSortName, Boolean asc, String code,
			String fullName, Integer activeFlg){
		return customerDao.SearchCustomerByCondition(page, pageSize, columnSortName, asc, code, fullName, activeFlg);
	}
	
	@Override
	public int getRowCount(String fullName, Integer activeFlg, String code){
		
		return customerDao.getRowCount(fullName,  activeFlg, code);
	}

}
