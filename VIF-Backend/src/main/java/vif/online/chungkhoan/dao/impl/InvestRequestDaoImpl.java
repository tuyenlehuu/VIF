package vif.online.chungkhoan.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.InvestRequestDao;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.AssetHistory;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.entities.User;

@Transactional
@Repository
public class InvestRequestDaoImpl implements InvestRequestDao {
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<InvestRequest> getAllInvestRequest() {
		// TODO Auto-generated method stub
		String hql = "FROM InvestRequest as i ORDER BY i.id asc";
		return (List<InvestRequest>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public InvestRequest getInvestRequestById(int id) {

		return entityManager.find(InvestRequest.class, id);
	}

	@Override
	public boolean addInvestRequest(InvestRequest request) {

		entityManager.persist(request);
		return true;
	}

	@Override
	public void updateInvestRequest(InvestRequest request) {
		entityManager.merge(request);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvestRequest> SearchInvestRequestByCondition(int page, int pageSize, Boolean asc,
			Integer typeOfRequest, Integer status) {

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<InvestRequest> from = criteriaQuery.from(InvestRequest.class);

		CriteriaQuery<Object> select = criteriaQuery.select(from);

		List<Predicate> predicates = new ArrayList<Predicate>();

		if (typeOfRequest != null) {
			predicates.add(criteriaBuilder.equal(from.get("typeOfRequest"), typeOfRequest));
		}

		if (status != null && !status.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("status"), status));
		}

		select.select(from).where(predicates.toArray(new Predicate[] {}));

		Query query = entityManager.createQuery(criteriaQuery);
		if (page >= 0 && pageSize >= 0) {
			query.setFirstResult((page - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		List<InvestRequest> lstResult = query.getResultList();

		return lstResult;
	}

	@Override
	public boolean logicalRequest(InvestRequest request) {
		// TODO Auto-generated method stub
		if (request.getCustomer() == null) {
			return true;
		} else {
			BigDecimal ccqCus = request.getCustomer().getTotalCcq();
			BigDecimal ccqReq = request.getAmount();
			if (ccqReq.intValue() <= ccqCus.intValue()) {
				return true;
			} else {
				return false;
			}

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public int getRowCount(Integer typeOfRequest, Integer status) {
		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<InvestRequest> from = criteriaQuery.from(InvestRequest.class);

		CriteriaQuery<Object> select = criteriaQuery.select(from);
		List<Predicate> predicates = new ArrayList<Predicate>();

		if (typeOfRequest != null) {
			predicates.add(criteriaBuilder.equal(from.get("typeOfRequest"), typeOfRequest));
		}

		if (status != null && !status.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("status"), status));
		}

		select.select(from).where(predicates.toArray(new Predicate[] {}));

		Query query = entityManager.createQuery(select);

		List<InvestRequest> lstResult = query.getResultList();
		return lstResult.size();
	}
	
	@Override
	public BigDecimal getPriceMaxDate() {
		String hql = "SELECT a.currentPrice FROM Asset AS a WHERE a.assetCode='VIF_CCQ'";
		List<BigDecimal> price = (List<BigDecimal>) entityManager.createQuery(hql).getResultList();
		return  price.get(0);
		
		
	}

}
