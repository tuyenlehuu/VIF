package vif.online.chungkhoan.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.TransactionHistoryDao;
import vif.online.chungkhoan.entities.TransactionHistory;

@Transactional
@Repository
public class TransactionHistoryDaoImpl implements TransactionHistoryDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public boolean addTransactionHistory(TransactionHistory transHistory) {
		// TODO Auto-generated method stub
		entityManager.persist(transHistory);
		return true;
	}

}
