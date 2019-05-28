package vif.online.chungkhoan.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.dao.InvestRequestDao;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.services.CustomerService;
import vif.online.chungkhoan.services.InvestRequestService;

@Service(value = "investRequestService")
public class InvestRequestServiceImpl implements InvestRequestService {

	@Autowired
	private InvestRequestDao investRequestDao;

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
	
		if(request.getTypeOfRequest()==1) {
			investRequestDao.addInvestRequest(request);
			return true;
		}else 
		if (request.getTypeOfRequest()==2&&investRequestDao.logicalRequest(request)) {
		    investRequestDao.addInvestRequest(request);
		    return true;
			
		} else {
			return false;
		}
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
			Integer typeOfRequest, Integer status) {
		// TODO Auto-generated method stub
		return investRequestDao.SearchInvestRequestByCondition(page, pageSize, asc, typeOfRequest, status);
	}

	@Override
	public int getRowCount(Integer typeOfRequest, Integer status) {
		// TODO Auto-generated method stub
		return investRequestDao.getRowCount(typeOfRequest, status);
	}

}
