package vif.online.chungkhoan.services;

import java.math.BigDecimal;

import vif.online.chungkhoan.helper.ApiResponse;

public interface InvestorTransService {
	public ApiResponse buyCCQ(Integer customerId, BigDecimal money, BigDecimal priceCCQ);
	
	public boolean sellCCQ(Integer customerId, BigDecimal amountCCQ, BigDecimal priceCCQ);
}
