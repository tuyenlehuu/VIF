package vif.online.chungkhoan.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.entities.Customer;

@Transactional
@Repository
public class CustomerDaoImpl implements CustomerDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		String hql = "FROM Customer as c WHERE c.activeFlg = 1 ORDER BY c.code asc";
		return (List<Customer>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public Customer getCustomerById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(Customer.class, id);
	}

	@Override
	public Customer getCustomerByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateCustomer(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomerByCode(String code) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCustomerById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean updateCCQCustomer(Customer customer, BigDecimal newCCQPrice, BigDecimal newTotalCCQ) {
		// TODO Auto-generated method stub
		Customer cus = entityManager.find(Customer.class, customer.getId());
		if(cus != null) {
			cus.setOrginalCCQPrice(newCCQPrice);
			cus.setTotalCcq(newTotalCCQ);
			entityManager.merge(cus);
			return true;
		}
		
		return false;
	}

}
