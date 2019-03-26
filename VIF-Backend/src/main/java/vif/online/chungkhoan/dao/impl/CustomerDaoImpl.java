package vif.online.chungkhoan.dao.impl;

import java.math.BigDecimal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.User;

@Transactional
@Repository
public class CustomerDaoImpl implements CustomerDao {

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
		String hql = "FROM Customer as c WHERE c.id = :id";
		@SuppressWarnings("unchecked")
		List<Customer> lstResult = entityManager.createQuery(hql).setParameter("id", id).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return entityManager.find(Customer.class, id);

	}

	@Override
	public Customer getCustomerByCode(String code) {

		String hql = "FROM Customer as c WHERE c.code = :code";
		@SuppressWarnings("unchecked")
		List<Customer> lstResult = entityManager.createQuery(hql).setParameter("code", code).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return null;

	}

	@Override
	public boolean addCustomer(Customer customer) {

		entityManager.persist(customer);
		customer.setCode(customer.getCode() + customer.getId().toString());
		return true;

	}

	@Override
	public void updateCustomer(Customer customer) {
		entityManager.flush();
		Customer mCustomer = getCustomerById(customer.getId());
		// mCustomer.setActiveFlg(0);
		// mCustomer.setId();
		customer.setUsers(mCustomer.getUsers());
		entityManager.merge(customer);
//		for (int i = 0; i < mCustomer.getUsers().size(); i++) {
//			mCustomer.getUsers().get(i).setCustomer(customer);
//		}

	}

	@Override
	public void deleteCustomerByCode(String code) {

		Customer mCustomer = getCustomerByCode(code);
		mCustomer.setActiveFlg(0);

	}

	@Override
	public void deleteCustomerById(Integer id) {
		Customer mCustomer = getCustomerById(id);
		mCustomer.setActiveFlg(0);
	}

	@Override
	public boolean updateCCQCustomer(Customer customer, BigDecimal newCCQPrice, BigDecimal newTotalCCQ) {
		// TODO Auto-generated method stub
		Customer cus = entityManager.find(Customer.class, customer.getId());
		if (cus != null) {
			cus.setOrginalCCQPrice(newCCQPrice);
			cus.setTotalCcq(newTotalCCQ);
			entityManager.merge(cus);
			return true;
		}

		return false;

	}
	
	
	@Override
	public List<User> getListUserById(int id){
		Customer cus = getCustomerById(id);
		if(cus != null) {
			return cus.getUsers();
		}
		return null;
		
	}
	
	

}
