package vif.online.chungkhoan.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.BillingInfoDao;
import vif.online.chungkhoan.entities.BillingInfo;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.User;

@Transactional
@Repository
public class BillingInfoDaoImpl implements BillingInfoDao {
	@SuppressWarnings("unchecked")

	@Autowired
	EntityManager entityManager;

	@Override
	public List<BillingInfo> getAlls() {
		// TODO Auto-generated method stub
		String hql = "FROM BillingInfo";
		return (List<BillingInfo>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public BillingInfo getById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(BillingInfo.class, id);
	}

	@Override
	public boolean addBillingInfo(BillingInfo bInfo) {
		// TODO Auto-generated method stub
		entityManager.persist(bInfo);
		return true;
	}

	@Override
	public void updateBillingInfo(BillingInfo bInfo) {
		// TODO Auto-generated method stub
		entityManager.merge(bInfo);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		BillingInfo bInfo = entityManager.find(BillingInfo.class, id);
		bInfo.setActiveFlg(0);
		entityManager.merge(bInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BillingInfo> SearchByCondition(int page, int pageSize, Boolean asc, String accountName, String bankName,
			String bankBranch, String bankAccount, Integer activeFlg) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<BillingInfo> from = criteriaQuery.from(BillingInfo.class);

		CriteriaQuery<Object> select = criteriaQuery.select(from);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (accountName != null && !accountName.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("accountName"), "%" + accountName + "%"));
		}

		if (bankName != null && !bankName.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("bankName"), "%" + bankName + "%"));
		}

		if (activeFlg != null) {
			predicates.add(criteriaBuilder.equal(from.get("activeFlg"), activeFlg));
		}

		if (bankBranch != null && !bankBranch.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("bankBranch"), "%" + bankBranch + "%"));
		}

		if (bankAccount != null && !bankAccount.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("bankAccount"), "%" + bankAccount + "%"));
		}

		select.select(from).where(predicates.toArray(new Predicate[] {}));

		Query query = entityManager.createQuery(criteriaQuery);
		if (page >= 0 && pageSize >= 0) {
			query.setFirstResult((page - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		List<BillingInfo> lstResult = query.getResultList();

		return lstResult;
	}

	@SuppressWarnings("unchecked")
	@Override

	public int getRowCount(String accountName, String bankName, String bankBranch, String bankAccount,
			Integer activeFlg) {

		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<BillingInfo> from = criteriaQuery.from(BillingInfo.class);

		CriteriaQuery<Object> select = criteriaQuery.select(from);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (accountName != null && !accountName.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("accountName"), "%" + accountName + "%"));
		}

		if (bankName != null && !bankName.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("bankName"), "%" + bankName + "%"));
		}

		if (activeFlg != null) {
			predicates.add(criteriaBuilder.equal(from.get("activeFlg"), activeFlg));
		}

		if (bankBranch != null && !bankBranch.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("bankBranch"), "%" + bankBranch + "%"));
		}

		if (bankAccount != null && !bankAccount.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("bankAccount"), "%" + bankAccount + "%"));
		}

		select.select(from).where(predicates.toArray(new Predicate[] {}));

		Query query = entityManager.createQuery(select);

		List<BillingInfo> lstResult = query.getResultList();
		return lstResult.size();
	}

}
