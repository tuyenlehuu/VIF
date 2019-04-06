package vif.online.chungkhoan.helper;

import java.io.Serializable;
import java.math.BigDecimal;

public class NAVDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer customerId;
	private String customerFullName; // Ten NDT
	private BigDecimal amountCCQBefore; // So CCQ dau ky
	private BigDecimal priceCCQBefore; // Gia dau ky
	private BigDecimal amountCCQAfter; // So CCQ cuoi ky
	private BigDecimal priceCCQAfter; // Gia cuoi ky
	private BigDecimal priceCCQMarket; // Gia thi truong cua CCQ
	private BigDecimal realAssetOfCus; // Tai san rong cua NDT
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerFullName() {
		return customerFullName;
	}
	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}
	public BigDecimal getAmountCCQBefore() {
		return amountCCQBefore;
	}
	public void setAmountCCQBefore(BigDecimal amountCCQBefore) {
		this.amountCCQBefore = amountCCQBefore;
	}
	public BigDecimal getPriceCCQBefore() {
		return priceCCQBefore;
	}
	public void setPriceCCQBefore(BigDecimal priceCCQBefore) {
		this.priceCCQBefore = priceCCQBefore;
	}
	public BigDecimal getAmountCCQAfter() {
		return amountCCQAfter;
	}
	public void setAmountCCQAfter(BigDecimal amountCCQAfter) {
		this.amountCCQAfter = amountCCQAfter;
	}
	public BigDecimal getPriceCCQAfter() {
		return priceCCQAfter;
	}
	public void setPriceCCQAfter(BigDecimal priceCCQAfter) {
		this.priceCCQAfter = priceCCQAfter;
	}
	public BigDecimal getPriceCCQMarket() {
		return priceCCQMarket;
	}
	public void setPriceCCQMarket(BigDecimal priceCCQMarket) {
		this.priceCCQMarket = priceCCQMarket;
	}
	public BigDecimal getRealAssetOfCus() {
		return realAssetOfCus;
	}
	public void setRealAssetOfCus(BigDecimal realAssetOfCus) {
		this.realAssetOfCus = realAssetOfCus;
	}
	
	
}
