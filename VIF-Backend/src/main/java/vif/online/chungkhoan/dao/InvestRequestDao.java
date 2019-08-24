package vif.online.chungkhoan.dao;

import java.math.BigDecimal;
import java.util.List;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.AssetHistory;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.entities.User;

public interface InvestRequestDao {


	List<InvestRequest> getAllInvestRequest();

	InvestRequest getInvestRequestById(int id);

	boolean addInvestRequest(InvestRequest request);

	List<InvestRequest> SearchInvestRequestByCondition(int page, int pageSize, Boolean asc, Integer typeOfRequest,
			Integer status, String fromDate, String toDate);

	void updateInvestRequest(InvestRequest request);

	boolean logicalRequest(InvestRequest request);

	int getRowCount(Integer typeOfRequest, Integer status, String fromDate, String toDate);

	BigDecimal getPriceMaxDate();
	
	Customer getCustomerByUsername(String userName);

}
