package vif.online.chungkhoan.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.dao.HolderHistoryDao;
import vif.online.chungkhoan.entities.HolderHistory;

@Repository
@Transactional
public class HolderHistoryDaoImpl implements HolderHistoryDao {

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<HolderHistory> getAllsHolder() {
		// TODO Auto-generated method stub
		String hql="SELECT h FROM HolderHistory AS h";
		return (List<HolderHistory>)entityManager.createQuery(hql).getResultList();
	}

	@Override
	public boolean addHolders(HolderHistory holderHistory) {
		// TODO Auto-generated method stub
		if(holderHistory==null) {
			return false;
		}
		entityManager.persist(holderHistory);
		return true;
	}

}
