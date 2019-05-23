package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.InvestRequest;

public interface InvestApproDao {
	List<InvestRequest> getAllInvestRequest();
	List<InvestRequest> SearchInvestRequestByCondition(int page, int pageSize, Boolean asc, Integer typeOfRequest,
			 String fromDate, String toDate);
	int getRowCount(Integer typeOfRequest, String fromDate, String toDate);
}
