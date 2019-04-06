package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.InvestorHistory;

public interface InvestorHistoryDao {
	
	public boolean addInvestorHistory(InvestorHistory investorHis);

	public List<InvestorHistory> getAllInvestorHistory();

	public List<InvestorHistory> searchInvestorHistoryByCondition(int page, int pageSize, String columnSortName,
			Boolean asc, Integer customerId, String fromDate, String toDate);

	int getRowCount(Integer customerId, String fromDate, String toDate);
	
}
