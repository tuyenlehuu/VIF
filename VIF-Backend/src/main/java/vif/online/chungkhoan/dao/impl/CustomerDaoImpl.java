package vif.online.chungkhoan.dao.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.CustomerAsset;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.IContaints;

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
	public boolean updateCCQCustomer(Customer customer) {
		// TODO Auto-generated method stub

		entityManager.merge(customer);
		return true;

	}

	@Override
	public List<User> getListUserById(int id) {
		Customer cus = getCustomerById(id);
		if (cus != null) {
			return cus.getUsers();
		}
		return null;
	}





	@Override
	public BigDecimal getTotalMoneyOfCustomers() {
		// TODO Auto-generated method stub
		String sql = "SELECT SUM(total_ccq*orginal_ccq_price) FROM customer";
		Query query = entityManager.createNativeQuery(sql);
		BigDecimal totalMoney = (BigDecimal) query.getSingleResult();
		return totalMoney;

	}
	// doing_customer

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> SearchCustomerByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String code, String fullName, Integer activeFlg, String email) {


		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<Customer> from = criteriaQuery.from(Customer.class);

		CriteriaQuery<Object> select = criteriaQuery.select(from);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (fullName != null && !fullName.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("fullName"), "%" + fullName + "%"));
		}

		if (code != null && !code.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("code"), "%" + code + "%"));
		}

		if (activeFlg != null) {
			predicates.add(criteriaBuilder.equal(from.get("activeFlg"), activeFlg));
		}

		if (email != null && !email.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("email"), "%" + email + "%"));
		}


		select.select(from).where(predicates.toArray(new Predicate[] {}));

		if (columnSortName != null && !columnSortName.equals("")) {
			if (asc == null || asc) {
				select.orderBy(criteriaBuilder.asc(from.get(columnSortName)));
			} else {
				select.orderBy(criteriaBuilder.desc(from.get(columnSortName)));
			}
		}

		Query query = entityManager.createQuery(criteriaQuery);
		if (page >= 0 && pageSize >= 0) {
			query.setFirstResult((page - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		List<Customer> lstResult = query.getResultList();

		return lstResult;
	}

	@SuppressWarnings("unchecked")
	@Override

	public int getRowCount(String fullName, Integer activeFlg, String code, String email) {

		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<Customer> from = criteriaQuery.from(Customer.class);

		CriteriaQuery<Object> select = criteriaQuery.select(from);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (fullName != null && !fullName.equals("")) {

			predicates.add(criteriaBuilder.like(from.get("fullName"), "%" + fullName + "%"));

		}

		if (activeFlg != null) {
			predicates.add(criteriaBuilder.equal(from.get("activeFlg"), activeFlg));
		}

		if (code != null && !code.equals("")) {

			predicates.add(criteriaBuilder.like(from.get("code"), "%" + code + "%"));
		}

		if (email != null) {
			predicates.add(criteriaBuilder.like(from.get("email"), "%" + email + "%"));

		}

		select.select(from).where(predicates.toArray(new Predicate[] {}));

		Query query = entityManager.createQuery(select);

		List<Customer> lstResult = query.getResultList();
		return lstResult.size();
	}


	@Override
	public String saveFileAvatar(MultipartFile file) {
		String path = IContaints.FILE_UPLOAD.AVATAR_UPLOAD_DIRECTORY;
		try {
			String filename = file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(path + "_time_" + System.currentTimeMillis() + "_time_" + filename)));
			stream.write(bytes);
			stream.flush();
			stream.close();

		} catch (IOException e) {
			return "Something wrong";
		}

		return IContaints.RESPONSE_CODE.SUCCESS;
	}

	@Override
	public String saveFileDocBack(MultipartFile file) {
		String path = IContaints.FILE_UPLOAD.DOC_BACK_UPLOAD_DIRECTORY;
		try {
			String filename = file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(path + "_time_" + System.currentTimeMillis() + "_time_" + filename)));
			stream.write(bytes);
			stream.flush();
			stream.close();

		} catch (IOException e) {
			return "Something wrong";
		}

		return IContaints.RESPONSE_CODE.SUCCESS;
	}

	@Override
	public String saveFileDocFront(MultipartFile file) {
		String path = IContaints.FILE_UPLOAD.DOC_FRONT_UPLOAD_DIRECTORY;
		try {
			String filename = file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(path + "_time_" + System.currentTimeMillis() + "_time_" + filename)));
			stream.write(bytes);
			stream.flush();
			stream.close();

		} catch (IOException e) {
			return "Something wrong";
		}

		return IContaints.RESPONSE_CODE.SUCCESS;
	}

	@Override
	public CustomerAsset getCusAssetByCusAndAssetId(Integer customerId, Integer assetId) {
		// TODO Auto-generated method stub
		String hql = "FROM CustomerAsset as ca WHERE ca.customerId = :customerId AND ca.assetId = :assetId";
		List<CustomerAsset> cusAssetLst = (List<CustomerAsset>) entityManager.createQuery(hql).setParameter("customerId", customerId).setParameter("assetId", assetId).getResultList();
		if(cusAssetLst != null && cusAssetLst.size()>0) {
			return cusAssetLst.get(0);
		}
		return null;
	}

	@Override
	public void addCustomerAsset(CustomerAsset cusAsset) {
		// TODO Auto-generated method stub
		entityManager.persist(cusAsset);
	}

	@Override
	public void updateCustomerAsset(CustomerAsset cusAsset) {
		// TODO Auto-generated method stub
		entityManager.merge(cusAsset);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerAsset> getListCusAssetByCusId(Integer customerId) {
		// TODO Auto-generated method stub
		String hql = "FROM CustomerAsset as ca WHERE ca.customerId = :customerId";
		List<CustomerAsset> cusAssetLst = (List<CustomerAsset>) entityManager.createQuery(hql).setParameter("customerId", customerId).getResultList();
		if(cusAssetLst != null && cusAssetLst.size()>0) {
			return cusAssetLst;
		}
		return null;
	}

}
