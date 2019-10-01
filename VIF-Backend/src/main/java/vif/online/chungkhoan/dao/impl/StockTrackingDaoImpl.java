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

import vif.online.chungkhoan.dao.StockTrackingDao;
import vif.online.chungkhoan.entities.StockTracking;

@Repository
@Transactional
public class StockTrackingDaoImpl implements StockTrackingDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockTracking> getAllStockTracking() {
		// TODO Auto-generated method stub
		String hql = "FROM StockTracking as st WHERE st.status = 1 ORDER BY st.code asc";
		return (List<StockTracking>) entityManager.createQuery(hql).getResultList();
	}
	
	@Override
	public boolean stockTrackingExists(StockTracking stockTracking) {
		// TODO Auto-generated method stub
		String hql = "FROM StockTracking as st WHERE st.status = 1 AND st.code = :stockCode";
		return entityManager.createQuery(hql).setParameter("stockCode", stockTracking.getCode()).getResultList().size() >0?true:false;
	}

	@Override
	public StockTracking getStockTrackById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(StockTracking.class, id);
	}

	@Override
	public boolean addStockTracking(StockTracking stockTracking) {
		// TODO Auto-generated method stub
		stockTracking.setStatus(1);
		entityManager.persist(stockTracking);
		return true;
	}

	@Override
	public void updateStockTracking(StockTracking stockTracking) {
		stockTracking.setLastUpdate(new Date());
		// TODO Auto-generated method stub
		entityManager.merge(stockTracking);
	}

	@Override
	public void deleteStockTrackingById(int id) {
		// TODO Auto-generated method stub
		StockTracking mStock = entityManager.find(StockTracking.class, id);
		mStock.setStatus(0);
		entityManager.merge(mStock);
	}

	@Override
	public void deleteStockTrackingByCode(String stockCode) {
		// TODO Auto-generated method stub
		StockTracking mStock = getStockTrackingByCode(stockCode);
		mStock.setStatus(0);
		entityManager.merge(mStock);
	}
	
	@Override
	public StockTracking getStockTrackingByCode(String stockCode) {
		// TODO Auto-generated method stub
		String hql = "FROM StockTracking as st WHERE st.code = :stockCode AND st.status=1";
		@SuppressWarnings("unchecked")
		List<StockTracking> lstResult = entityManager.createQuery(hql).setParameter("stockCode", stockCode).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getRowCount(String stockCode) {
		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<StockTracking> from = criteriaQuery.from(StockTracking.class);

		CriteriaQuery<Object> select = criteriaQuery.select(from);
		List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(criteriaBuilder.equal(from.get("status"), 1));

		if (stockCode != null && !stockCode.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("code"), stockCode));
		}

//
//		if (assetName != null && !assetName.equals("")) {
//			predicates.add(criteriaBuilder.like(from.get("assetName"), assetName));
//		}

		select.select(from).where(predicates.toArray(new Predicate[] {}));

		Query query = entityManager.createQuery(select);

		List<StockTracking> lstResult = query.getResultList();
		return lstResult.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockTracking> getStockTrackingByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String stockCode) {
		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<StockTracking> from = criteriaQuery.from(StockTracking.class);

		CriteriaQuery<Object> select = criteriaQuery.select(from);

		List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(criteriaBuilder.equal(from.get("status"), 1));

		if (stockCode != null && !stockCode.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("code"), "%"+stockCode+"%"));
		}

//		if (assetName != null && !assetName.equals("")) {
//			predicates.add(criteriaBuilder.like(from.get("assetName"), "%"+assetName+"%"));
//		}

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
		List<StockTracking> lstResult = query.getResultList();
		return lstResult;
	}

}
