package vif.online.chungkhoan.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		String hql = "FROM AppParam as a WHERE a.activeFlg = 1";
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
		// TODO Auto-generated method stub
		String hql = "FROM AppParam as a WHERE a.activeFlg=1 AND a.propKey = :propKey AND a.propType = :propType";
		return entityManager.createQuery(hql).setParameter("propKey", appParam.getPropKey()).setParameter("propType", appParam.getPropType()).getResultList().size() >0?true:false;
	}
}
