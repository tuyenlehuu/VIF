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
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.dao.HolderEquityDao;
import vif.online.chungkhoan.entities.HolderEquity;

@Transactional
@Repository("holderEquityDao")
public class HolderEquityDaoImpl implements HolderEquityDao {
	
	@PersistenceContext
	EntityManager entityManager;
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	@Override
	public List<HolderEquity> getAllsHolder() {
		// TODO Auto-generated method stub
		String hql = "SELECT h FROM HolderEquity h";
		return (List<HolderEquity>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public HolderEquity getHolderbyId(Integer id) {
		// TODO Auto-generated method stub
		return entityManager.find(HolderEquity.class, id);
	}

	@Override
	public boolean addHolder(HolderEquity holderEquity) {
		// TODO Auto-generated method stub
		if (holderEquity == null) {
			return false;
		}
		entityManager.persist(holderEquity);
		return true;
	}

	@Override
	public void updateHolder(HolderEquity holderEquity) {
		// TODO Auto-generated method stub
		entityManager.merge(holderEquity);

	}

	@Override
	public void deleteHolderById(Integer id) {
		// TODO Auto-generated method stub
		HolderEquity holderEquity = entityManager.find(HolderEquity.class, id);
		if (holderEquity != null) {
			entityManager.remove(holderEquity);
		}

	}

	@Override
	public List<HolderEquity> SearchHolderByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String fromDate, String toDate, String fullName) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
			Root<HolderEquity> from = criteriaQuery.from(HolderEquity.class);

			CriteriaQuery<Object> select = criteriaQuery.select(from);

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (fromDate != null && !fromDate.equals("")) {
				Date fDate = formatter.parse(fromDate);
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("lastUpdate"), fDate));
			}
			if (toDate != null && !toDate.equals("")) {
				Date tDate = formatter.parse(toDate);
				predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("lastUpdate"), tDate));
			}
			if (fullName != null && !fullName.equals("")) {

				predicates.add(criteriaBuilder.like(from.get("fullName"), "%" + fullName + "%"));
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
			List<HolderEquity> lstResult = query.getResultList();

			return lstResult;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getRowCount(String fromDate, String toDate, String fullName) {
		// TODO Auto-generated method stub
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
			Root<HolderEquity> from = criteriaQuery.from(HolderEquity.class);

			CriteriaQuery<Object> select = criteriaQuery.select(from);

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (fromDate != null && !fromDate.equals("")) {
				Date fDate = formatter.parse(fromDate);
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("lastUpdate"), fDate));
			}
			if (toDate != null && !toDate.equals("")) {
				Date tDate = formatter.parse(toDate);
				predicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("lastUpdate"), tDate));
			}
			if (fullName != null && !fullName.equals("")) {

				predicates.add(criteriaBuilder.like(from.get("fullName"), "%" + fullName + "%"));
			}

			select.select(from).where(predicates.toArray(new Predicate[] {}));

			Query query = entityManager.createQuery(criteriaQuery);

			List<HolderEquity> lstResult = query.getResultList();

			return lstResult.size();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 0;
	}
}
