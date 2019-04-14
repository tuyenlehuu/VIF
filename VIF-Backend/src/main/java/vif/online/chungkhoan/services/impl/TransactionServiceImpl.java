package vif.online.chungkhoan.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.TransactionHistoryDao;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.TransactionHistory;
import vif.online.chungkhoan.services.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	TransactionHistoryDao transactionHistoryDao;

	@Override
	public List<TransactionHistory> SearchTransactionByCondition(int page, int pageSize, String columnSortName,
			Boolean asc, String creatDate, String typeOfTransaction,Integer assetId) {
		// TODO Auto-generated method stub
		return transactionHistoryDao.SearchTransactionByCondition(page, pageSize, columnSortName, asc, creatDate,
				typeOfTransaction,assetId);
	}

	@Override
	public int getRowCount(String creatDate, String typeOfTransaction,Integer assetId) {
		// TODO Auto-generated method stub
		return transactionHistoryDao.getRowCount(creatDate, typeOfTransaction,assetId);
	}

}
