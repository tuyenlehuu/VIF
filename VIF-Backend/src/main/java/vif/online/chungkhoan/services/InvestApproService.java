package vif.online.chungkhoan.services;

import java.math.BigDecimal;
import java.util.List;

import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.helper.ApiResponse;

public interface InvestApproService {
	List<InvestRequest> getAllInvestRequest();
	List<InvestRequest> SearchInvestRequestByCondition(int page, int pageSize, Boolean asc, Integer typeOfRequest,Integer typeOfInvest,
			 String fromDate, String toDate, Integer status);
	int getRowCount(Integer typeOfRequest,Integer typeOfInvest, String fromDate, String toDate, Integer status);
	void accept(InvestRequest investRequest);
	void reject(Integer id);
}
