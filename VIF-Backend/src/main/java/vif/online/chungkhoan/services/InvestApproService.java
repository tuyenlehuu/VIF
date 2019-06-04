package vif.online.chungkhoan.services;

import java.math.BigDecimal;
import java.util.List;

import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.helper.ApiResponse;

public interface InvestApproService {
	List<InvestRequest> getAllInvestRequest();
	List<InvestRequest> SearchInvestRequestByCondition(int page, int pageSize, Boolean asc, Integer typeOfRequest,Integer typeOfInvest,
			 String fromDate, String toDate);
	int getRowCount(Integer typeOfRequest,Integer typeOfInvest, String fromDate, String toDate);
	
	void reject(Integer id);
	void accept(Integer id); 
	public ApiResponse buyCCQ(Integer customerId, BigDecimal money, BigDecimal priceCCQ);
	
	public ApiResponse sellCCQ(Integer customerId, BigDecimal amountCCQ, BigDecimal priceCCQ);
}
