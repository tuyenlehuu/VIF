package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.helper.ApiResponse;

public interface InvestApproDao {
	List<InvestRequest> getAllInvestRequest();
	List<InvestRequest> SearchInvestRequestByCondition(int page, int pageSize, Boolean asc, Integer typeOfRequest,Integer typeOfInvest,
			 String fromDate, String toDate);
	void reject(Integer id);
	void accept(Integer id);
	int getRowCount(Integer typeOfRequest, Integer typeOfInvest, String fromDate, String toDate);
}
