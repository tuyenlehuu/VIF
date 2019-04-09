package vif.online.chungkhoan.dao.impl;

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

import vif.online.chungkhoan.dao.AppParamDao;
import vif.online.chungkhoan.entities.AppParam;

@Transactional
@Repository(value="appParamDao")
public class AppParamDaoImpl implements AppParamDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public AppParam getAppParambyId(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(AppParam.class, id);
	}

	@Override
	public boolean addAppParam(AppParam appParam) {
		// TODO Auto-generated method stub
		if(appParam == null) {
			return false;
		}
		entityManager.persist(appParam);
		return true;
	}

	@Override
	public void deleteAppParamById(int id) {
		// TODO Auto-generated method stub
		AppParam appParam = entityManager.find(AppParam.class, id);
		appParam.setActiveFlg(0);
		entityManager.merge(appParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppParam> getAllAppParam() {
		// TODO Auto-generated method stub
		String hql = "FROM AppParam as a";
		return (List<AppParam>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public boolean updateAppParam(AppParam appParam) {
		// TODO Auto-generated method stub
		//entityManager.flush();
		entityManager.merge(appParam);
		return true;
	}

	@Override
	public boolean isExist(AppParam appParam) {
		// Truong hop update status
		if(appParam.getActiveFlg()==0) {
			return false;
		}		
		// Truong hop update cac truong khac
		// TODO Auto-generated method stub
		String hql = "FROM AppParam as a WHERE a.activeFlg=1 AND a.propKey = :propKey AND a.propType = :propType";
		return entityManager.createQuery(hql).setParameter("propKey", appParam.getPropKey()).setParameter("propType", appParam.getPropType()).getResultList().size() >0?true:false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppParam> SearchAppParamByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String propKey, Integer activeFlg, String propType, String propValue,  String description) {
		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<AppParam> from = criteriaQuery.from(AppParam.class);
		
		CriteriaQuery<Object> select = criteriaQuery.select(from);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(propKey != null && !propKey.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("propKey"), "%"+propKey+"%"));
		}
		
		if(activeFlg != null) {
			predicates.add(criteriaBuilder.equal(from.get("activeFlg"), activeFlg));
		}
		
		if(propType != null && !propType.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("propType"), propType));
		}
		
		if(propValue != null && !propValue.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("propValue"), propValue));
		}
		
		if(description != null && !description.equals("")) {
			predicates.add(criteriaBuilder.like(from.get("description"), "%"+description+"%"));
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
		List<AppParam> lstResult = query.getResultList();

		return lstResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getRowCount(String propKey, Integer activeFlg, String propType, String propValue, String description) {
		// TODO Auto-generated method stub
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
		Root<AppParam> from = criteriaQuery.from(AppParam.class);
		
		CriteriaQuery<Object> select = criteriaQuery.select(from);
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(propKey != null && !propKey.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("propKey"), propKey));
		}
		
		if(activeFlg != null) {
			predicates.add(criteriaBuilder.equal(from.get("activeFlg"), activeFlg));
		}
		
		if(propType != null && !propType.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("propType"), propType));
		}
		
		if(propValue != null && !propValue.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("propValue"), propValue));
		}
		
		if(description != null && !description.equals("")) {
			predicates.add(criteriaBuilder.equal(from.get("description"), description));
		}
		
		select.select(from).where(predicates.toArray(new Predicate[]{}));
		
		Query query = entityManager.createQuery(select);

		List<AppParam> lstResult = query.getResultList();
		return lstResult.size();
	}
}
