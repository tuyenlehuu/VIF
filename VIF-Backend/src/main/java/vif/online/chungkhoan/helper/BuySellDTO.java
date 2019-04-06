package vif.online.chungkhoan.helper;

import java.io.Serializable;
import java.math.BigDecimal;

public class BuySellDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer customerId;
	BigDecimal money;
	BigDecimal amountCCQ;
	BigDecimal priceCCQ;
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getAmountCCQ() {
		return amountCCQ;
	}
	public void setAmountCCQ(BigDecimal amountCCQ) {
		this.amountCCQ = amountCCQ;
	}
	public BigDecimal getPriceCCQ() {
		return priceCCQ;
	}
	public void setPriceCCQ(BigDecimal priceCCQ) {
		this.priceCCQ = priceCCQ;
	}
	
}
