package vif.online.chungkhoan.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.AppParamDao;
import vif.online.chungkhoan.entities.AppParam;
import vif.online.chungkhoan.repositories.AppParamRepository;

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
		entityManager.remove(getAppParambyId(id));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppParam> getAllAppParam() {
		// TODO Auto-generated method stub
		String hql = "FROM AppParam as a where a.activeFlg = 1";
		return (List<AppParam>) entityManager.createQuery(hql).getResultList();
	}

	@Override
	public void updateAppParam(AppParam appParam) {
		// TODO Auto-generated method stub
		//entityManager.flush();
		entityManager.merge(appParam);
	}
}
