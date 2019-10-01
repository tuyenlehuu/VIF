package vif.online.chungkhoan.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import vif.online.chungkhoan.entities.InvestorHistory;
import vif.online.chungkhoan.helper.ApiResponse;
import vif.online.chungkhoan.helper.BuySellDTO;

@Transactional
public interface InvestorTransService {
	public ApiResponse buyCCQ(Integer customerId, BigDecimal money, BigDecimal priceCCQ);
	
	public ApiResponse sellCCQ(Integer customerId, BigDecimal amountCCQ, BigDecimal priceCCQ, String feeSell);
	
	public List<InvestorHistory> getAllInvestorHistory();

	public List<InvestorHistory> searchInvestorHistoryByCondition(int page, int pageSize, String columnSortName,
			Boolean asc, Integer customerId, String fromDate, String toDate);

	int getRowCount(Integer customerId, String fromDate, String toDate);

	public ApiResponse buyEnsureCCQ(BuySellDTO buyObject);

	public ApiResponse sellEnsureCCQ(BuySellDTO buyObject);

	public ApiResponse getEnsureCCQByCusAsset(Integer customerId, String assetCode);
	
	public ApiResponse cCommissionDivide(Integer customerId, BigDecimal amount, String cType);
	
}
