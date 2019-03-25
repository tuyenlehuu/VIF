package vif.online.chungkhoan.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.InvestorHistoryDao;
import vif.online.chungkhoan.entities.InvestorHistory;

@Transactional
@Repository
public class InvestorHistoryDaoImpl implements InvestorHistoryDao{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public boolean addInvestorHistory(InvestorHistory investorHis) {
		// TODO Auto-generated method stub
		entityManager.persist(investorHis);
		return true;
	}

}
