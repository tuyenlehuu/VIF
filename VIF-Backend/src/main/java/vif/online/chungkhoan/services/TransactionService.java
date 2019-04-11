package vif.online.chungkhoan.services;

import java.util.Date;
import java.util.List;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.TransactionHistory;

public interface TransactionService {
	List<TransactionHistory> SearchTransactionByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			Date createDate, String typeOfTransaction, Asset asset);

	int getRowCount(Date createDate, String typeOfTransaction, Asset asset);
}
