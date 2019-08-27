package vif.online.chungkhoan.services.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.dao.InvestRequestDao;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.AssetHistory;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.CustomerAsset;
import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.services.AssetService;
import vif.online.chungkhoan.services.CustomerService;
import vif.online.chungkhoan.services.InvestRequestService;

@Service(value = "investRequestService")
public class InvestRequestServiceImpl implements InvestRequestService {

	@Autowired
	private InvestRequestDao investRequestDao;

	@Autowired
	private AssetService assetService;

	@Autowired
	private CustomerDao customerDao;

	@Override
	public List<InvestRequest> getAllInvestRequest() {
		// TODO Auto-generated method stub
		return investRequestDao.getAllInvestRequest();
	}

	@Override
	public InvestRequest getInvestRequestById(int id) {
		// TODO Auto-generated method stub
		return investRequestDao.getInvestRequestById(id);
	}

	@Override
	public boolean addInvestRequest(InvestRequest request) {
		// TODO Auto-generated method stub
		Customer customer = request.getCustomer();
		if (investRequestDao.checkCustomerExist(customer.getId())) {
			if (request.getTypeOfRequest() == 1) {
				investRequestDao.addInvestRequest(request);
				return true;
			} else if (request.getTypeOfRequest() == 2 && investRequestDao.logicalRequest(request)) {
				investRequestDao.addInvestRequest(request);
				return true;
			} else {
				return false;
			}
		}
		return false;
			
	}

	@Override
	public void updateInvestRequest(InvestRequest request) {
		// TODO Auto-generated method stub

		InvestRequest iq = investRequestDao.getInvestRequestById(request.getId());
		request.setCustomer(iq.getCustomer());
		investRequestDao.updateInvestRequest(request);
	}

	@Override
	public List<InvestRequest> SearchInvestRequestByCondition(int page, int pageSize, Boolean asc,
			Integer typeOfRequest, Integer status, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return investRequestDao.SearchInvestRequestByCondition(page, pageSize, asc, typeOfRequest, status, fromDate,
				toDate);
	}

	@Override
	public int getRowCount(Integer typeOfRequest, Integer status, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return investRequestDao.getRowCount(typeOfRequest, status, fromDate, toDate);
	}

	@Override
	public BigDecimal getPriceMaxDate() {
		return investRequestDao.getPriceMaxDate();
	}

	@Override
	public ApiResponse getEnsureCCQByCusAsset(Integer customerId, String assetCode) {
		// TODO Auto-generated method stub
		ApiResponse resultResponse = new ApiResponse();
		Asset myEnsureCCQ = assetService.getAssetByCode(assetCode);
		if (myEnsureCCQ != null) {
			CustomerAsset cusAsset = customerDao.getCusAssetByCusAndAssetId(customerId, myEnsureCCQ.getId());
			if (cusAsset != null) {
				resultResponse.setData(cusAsset.getAmount());
			} else {
				resultResponse.setData(null);
			}
		} else {
			resultResponse.setData(null);
		}

		resultResponse.setCode(200);
		resultResponse.setStatus(true);
		resultResponse.setErrors(null);
		return resultResponse;
	}

	@Override
	public Customer getCustomerByUsername(String userName) {
		// TODO Auto-generated method stub
		return investRequestDao.getCustomerByUsername(userName);
	}

	@Override
	public boolean checkCustomerExist(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
