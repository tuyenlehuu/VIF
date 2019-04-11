package vif.online.chungkhoan.dao.impl;

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

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.TransactionHistoryDao;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.TransactionHistory;
import vif.online.chungkhoan.entities.User;

@Transactional
@Repository
public class TransactionHistoryDaoImpl implements TransactionHistoryDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public boolean addTransactionHistory(TransactionHistory transHistory) {
		// TODO Auto-generated method stub
		entityManager.persist(transHistory);
		return true;
	}

	@Override
	public List<TransactionHistory> SearchTransactionByCondition(int page, int pageSize, String columnSortName,
			Boolean asc, Date createDate, String typeOfTransaction, Asset asset) {
		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<TransactionHistory> from = criteriaQuery.from(TransactionHistory.class);
		
		CriteriaQuery<Object> select = criteriaQuery.select(from);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		
		if(createDate != null && !createDate.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("createDate"), createDate));
		}
		
		
		if(typeOfTransaction != null && !typeOfTransaction.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("typeOfTransaction"), typeOfTransaction));
		}
		
		if(asset != null && !asset.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("asset"), asset.getAssetCode()));
		}
		
		select.select(from).where(predicates.toArray(new Predicate[]{}));
		
		if(columnSortName != null && !columnSortName.equals("")) {
			if(asc== null || asc) {
				select.orderBy(criteriaBuilder.asc(from.get(columnSortName)));
			}else {
				select.orderBy(criteriaBuilder.desc(from.get(columnSortName)));
			}
		}
		
		Query query = entityManager.createQuery(criteriaQuery);
		if (page >= 0 && pageSize >= 0) {
			query.setFirstResult((page - 1) * pageSize);
			query.setMaxResults(pageSize);
		}
		List<TransactionHistory> lstResult = query.getResultList();

		return lstResult;
	}

	@Override
	public int getRowCount(Date createDate, String typeOfTransaction, Asset asset) {
		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<TransactionHistory> from = criteriaQuery.from(TransactionHistory.class);
		
		CriteriaQuery<Object> select = criteriaQuery.select(from);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		
		if(createDate != null && !createDate.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("createDate"), createDate));
		}
		
		
		if(typeOfTransaction != null && !typeOfTransaction.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("typeOfTransaction"), typeOfTransaction));
		}
		
		if(asset != null && !asset.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("asset"), asset.getAssetCode()));
		}
		
		select.select(from).where(predicates.toArray(new Predicate[]{}));
		
		Query query = entityManager.createQuery(criteriaQuery);
		
		List<TransactionHistory> lstResult = query.getResultList();

		return lstResult.size();
	}

}
