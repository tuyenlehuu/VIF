package vif.online.chungkhoan.services;

import java.math.BigDecimal;

public interface InvestorTransService {
	public boolean buyCCQ(Integer customerId, BigDecimal money, BigDecimal priceCCQ);
	
	public boolean sellCCQ(Integer customerId, BigDecimal amountCCQ, BigDecimal priceCCQ);
}
