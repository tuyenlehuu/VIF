package vif.online.chungkhoan.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import vif.online.chungkhoan.entities.InvestorHistory;
import vif.online.chungkhoan.helper.ApiResponse;

@Transactional
public interface InvestorTransService {
	public ApiResponse buyCCQ(Integer customerId, BigDecimal money, BigDecimal priceCCQ);
	
	public ApiResponse sellCCQ(Integer customerId, BigDecimal amountCCQ, BigDecimal priceCCQ);
	
	public List<InvestorHistory> getAllInvestorHistory();

	public List<InvestorHistory> searchInvestorHistoryByCondition(int page, int pageSize, String columnSortName,
			Boolean asc, Integer customerId, String fromDate, String toDate);

	int getRowCount(Integer customerId, String fromDate, String toDate);
}
