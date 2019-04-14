package vif.online.chungkhoan.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import vif.online.chungkhoan.entities.InvestorHistory;
import vif.online.chungkhoan.entities.TransactionHistory;

@Transactional
@Repository
public class TransactionHistoryDaoImpl implements TransactionHistoryDao {

	@PersistenceContext
	private EntityManager entityManager;

	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	@Override
	public boolean addTransactionHistory(TransactionHistory transHistory) {
		// TODO Auto-generated method stub
		entityManager.persist(transHistory);
		return true;
	}

	@Override
	public List<TransactionHistory> SearchTransactionByCondition(int page, int pageSize, String columnSortName,
			Boolean asc, String creatDate, String typeOfTransaction, Integer assetId) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
			Root<TransactionHistory> from = criteriaQuery.from(TransactionHistory.class);
			
			CriteriaQuery<Object> select = criteriaQuery.select(from);
			
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if(assetId != null && assetId>0) {
				predicates.add(criteriaBuilder.equal(from.get("asset"), assetId));
			}
			
			if(creatDate != null && !creatDate.equals("")) {
				Date cDate = formatter.parse(creatDate);
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("createDate"), cDate));
			}
			
			if(typeOfTransaction != null && !typeOfTransaction.equals("")) {
				
				predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("typeOfTransaction"), typeOfTransaction));
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
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int getRowCount(String creatDate, String typeOfTransaction, Integer assetId) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
			Root<TransactionHistory> from = criteriaQuery.from(TransactionHistory.class);

			CriteriaQuery<Object> select = criteriaQuery.select(from);

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (creatDate != null && !creatDate.equals("")) {
				Date cDate = formatter.parse(creatDate);
				predicates.add(criteriaBuilder.equal(from.get("creatDate"), cDate));
			}

			if (typeOfTransaction != null && !typeOfTransaction.equals("")) {
				predicates.add(criteriaBuilder.equal(from.get("typeOfTransaction"), typeOfTransaction));
			}
			if (assetId != null && assetId > 0) {
				predicates.add(criteriaBuilder.equal(from.get("asset"), assetId));
			}

			select.select(from).where(predicates.toArray(new Predicate[] {}));

			Query query = entityManager.createQuery(criteriaQuery);

			List<TransactionHistory> lstResult = query.getResultList();

			return lstResult.size();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
}
