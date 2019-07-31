package vif.online.chungkhoan.services;

import java.util.Date;
import java.util.List;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.TransactionHistory;

public interface TransactionService {
	List<TransactionHistory> getAll();
	List<TransactionHistory> SearchTransactionByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String fromDate,String toDate, String typeOfTransaction,Integer assetId);

	int getRowCount(String fromDate,String toDate, String typeOfTransaction,Integer assetId);

}
