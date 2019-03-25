package vif.online.chungkhoan.services;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.helper.ApiResponse;

@Transactional
public interface InvestorTransService {
	public ApiResponse buyCCQ(Integer customerId, BigDecimal money, BigDecimal priceCCQ);
	
	public ApiResponse sellCCQ(Integer customerId, BigDecimal amountCCQ, BigDecimal priceCCQ);
}
