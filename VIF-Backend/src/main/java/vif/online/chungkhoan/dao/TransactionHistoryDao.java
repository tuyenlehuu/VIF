package vif.online.chungkhoan.dao;

import java.util.Date;
import java.util.List;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.TransactionHistory;
import vif.online.chungkhoan.entities.User;

public interface TransactionHistoryDao {

	public boolean addTransactionHistory(TransactionHistory transHistory);

	List<TransactionHistory> SearchTransactionByCondition(int page, int pageSize, String columnSortName, Boolean asc, Date creatDate,
		 String typeOfTransaction);

	int getRowCount( Date creatDate,String typeOfTransaction);
}
