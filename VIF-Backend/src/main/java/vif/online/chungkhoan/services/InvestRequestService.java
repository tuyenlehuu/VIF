package vif.online.chungkhoan.services;

import java.math.BigDecimal;
import java.util.List;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.AssetHistory;
import vif.online.chungkhoan.entities.InvestRequest;



public interface InvestRequestService {

	List<InvestRequest> getAllInvestRequest();

	void updateInvestRequest(InvestRequest request);

	boolean addInvestRequest(InvestRequest request);

	InvestRequest getInvestRequestById(int id);

	List<InvestRequest> SearchInvestRequestByCondition(int page, int pageSize, Boolean asc, Integer typeOfRequest,
			Integer status);

	int getRowCount(Integer typeOfRequest, Integer status);

	BigDecimal getPriceMaxDate();

	

}