package vif.online.chungkhoan.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vif.online.chungkhoan.dao.HolderEquityDao;
import vif.online.chungkhoan.entities.Branch;
import vif.online.chungkhoan.entities.HolderEquity;

@Transactional
@Repository("holderEquityDao")
public class HolderEquityDaoImpl implements HolderEquityDao {
	@Autowired
	EntityManager entityManager;

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
		HolderEquity holderEquity=entityManager.find(HolderEquity.class,id);
		if(holderEquity!=null) {
			entityManager.remove(holderEquity);
		}

	}

	@Override
	public List<HolderEquity> SearchHolderByCondition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
