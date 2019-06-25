package vif.online.chungkhoan.services.impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import vif.online.chungkhoan.dao.CustomerDao;
import vif.online.chungkhoan.dao.InvestApproDao;
import vif.online.chungkhoan.dao.InvestorHistoryDao;
import vif.online.chungkhoan.dao.TransactionHistoryDao;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.Customer;
import vif.online.chungkhoan.entities.GroupAsset;
import vif.online.chungkhoan.entities.InvestRequest;
import vif.online.chungkhoan.entities.InvestorHistory;
import vif.online.chungkhoan.entities.TransactionHistory;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.BuySellDTO;
import vif.online.chungkhoan.helper.IContaints;
import vif.online.chungkhoan.services.AssetService;
import vif.online.chungkhoan.services.InvestApproService;
import vif.online.chungkhoan.services.InvestorTransService;

@Service(value = "investApproService")

public class InvestApproServiceImpl implements InvestApproService {

	@Autowired
	private InvestApproDao investApproDao;
	
	@Autowired
	private InvestorTransService investorTransService;


	private BuySellDTO buyObject;

	@Override
	public List<InvestRequest> getAllInvestRequest() {
		// TODO Auto-generated method stub
		return investApproDao.getAllInvestRequest();
	}

	@Override
	public List<InvestRequest> SearchInvestRequestByCondition(int page, int pageSize, Boolean asc,
			Integer typeOfRequest,Integer typeOfInvest, String fromDate, String toDate, Integer status) {
		// TODO Auto-generated method stub
		return investApproDao.SearchInvestRequestByCondition(page, pageSize, asc, typeOfRequest, typeOfInvest, fromDate, toDate, status);
	}

	@Override
	public int getRowCount(Integer typeOfRequest, Integer typeOfInvest, String fromDate, String toDate, Integer status) {
		// TODO Auto-generated method stub
		return investApproDao.getRowCount(typeOfRequest, typeOfInvest, fromDate, toDate, status);
	}

	@Override
	public void reject(Integer id) {
		// TODO Auto-generated method stub
		investApproDao.reject(id);
	}
	//day nua
	@Override
	public void accept(InvestRequest investRequest) {
		// TODO Auto-generated method stub
//		
		investRequest.setStatus(2);
		investApproDao.accept(investRequest);
		// hàm này để làm j nhiều vậy ô ?
		//mấy cái if chỉ call đến thằng mua bán ccq với mua bán ccq đảm bảo a Tuyên viết rồi thôi
	
	}
	
}
