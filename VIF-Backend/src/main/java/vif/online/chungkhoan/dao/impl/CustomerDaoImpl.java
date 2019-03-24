package vif.online.chungkhoan.dao.impl;

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
		String hql = "FROM Customer as c WHERE c.id = :id";
		@SuppressWarnings("unchecked")
		List<Customer> lstResult = entityManager.createQuery(hql).setParameter("id", id).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return null;
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
	public Customer getCustomerByFullName(String name) {
		String hql = "FROM Customer as c WHERE c.fullName = :name";
		@SuppressWarnings("unchecked")
		List<Customer> lstResult = entityManager.createQuery(hql).setParameter("name",name).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return null;
		
	}
	
	
	

	@Override
	public boolean addCustomer(Customer customer) {
		entityManager.persist(customer);
		return true;
	}

	@Override
	public void updateCustomer(Customer customer) {
		entityManager.flush();
		entityManager.merge(customer);
		
	}

	@Override
	public void deleteCustomerByCode(String code) {
		entityManager.remove(getCustomerByCode(code));
		
	}

	@Override
	public void deleteCustomerById(Integer id) {
		entityManager.remove(getCustomerById(id));
		
	}

}
