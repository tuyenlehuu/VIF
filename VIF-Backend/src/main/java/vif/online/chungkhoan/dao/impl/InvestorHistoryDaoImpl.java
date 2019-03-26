package vif.online.chungkhoan.dao.impl;

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

import vif.online.chungkhoan.dao.InvestorHistoryDao;
import vif.online.chungkhoan.entities.InvestorHistory;

@Transactional
@Repository
public class InvestorHistoryDaoImpl implements InvestorHistoryDao{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	@Override
	public boolean addInvestorHistory(InvestorHistory investorHis) {
		// TODO Auto-generated method stub
		entityManager.persist(investorHis);
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvestorHistory> getAllInvestorHistory() {
		// TODO Auto-generated method stub
		String hql = "FROM InvestorHistory as ih ORDER BY ih.createDate desc";
		return (List<InvestorHistory>) entityManager.createQuery(hql).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvestorHistory> searchInvestorHistoryByCondition(int page, int pageSize, String columnSortName,
			Boolean asc, Integer customerId, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
			Root<InvestorHistory> from = criteriaQuery.from(InvestorHistory.class);
			
			CriteriaQuery<Object> select = criteriaQuery.select(from);
			
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if(customerId != null && customerId>0) {
				predicates.add(criteriaBuilder.equal(from.get("customer"), customerId));
			}
			
			if(fromDate != null && !fromDate.equals("")) {
				Date fDate = formatter.parse(fromDate);
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("createDate"), fDate));
			}
			
			if(toDate != null && !toDate.equals("")) {
				Date tDate = formatter.parse(toDate);
				predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("createDate"), tDate));
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
			List<InvestorHistory> lstResult = query.getResultList();
			
			return lstResult;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getRowCount(Integer customerId, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
			Root<InvestorHistory> from = criteriaQuery.from(InvestorHistory.class);
			
			CriteriaQuery<Object> select = criteriaQuery.select(from);
			
			List<Predicate> predicates = new ArrayList<Predicate>();
			
			if(customerId != null && customerId>0) {
				predicates.add(criteriaBuilder.equal(from.get("customer"), customerId));
			}
			
			if(fromDate != null && !fromDate.equals("")) {
				Date fDate = formatter.parse(fromDate);
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("createDate"), fDate));
			}
			
			if(toDate != null && !toDate.equals("")) {
				Date tDate = formatter.parse(toDate);
				predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("createDate"), tDate));
			}
			
			select.select(from).where(predicates.toArray(new Predicate[]{}));
			
			Query query = entityManager.createQuery(criteriaQuery);
			List<InvestorHistory> lstResult = query.getResultList();
			
			return lstResult.size();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

}
