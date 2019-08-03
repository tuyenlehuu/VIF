package vif.online.chungkhoan.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class DashBoard implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal cash;
	private BigDecimal cs;
	private BigDecimal shares;
	private BigDecimal itRt;
	private BigDecimal debtLong;
	private BigDecimal debtShort;
	private BigDecimal vifCCQPrice;
	private BigDecimal vifAmountCCQ;
	private BigDecimal totalAsset;
	private BigDecimal totalDebt;
	
	public BigDecimal getCash() {
		return cash;
	}
	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}
	public BigDecimal getCs() {
		return cs;
	}
	public void setCs(BigDecimal cs) {
		this.cs = cs;
	}
	public BigDecimal getShares() {
		return shares;
	}
	public void setShares(BigDecimal shares) {
		this.shares = shares;
	}
	public BigDecimal getItRt() {
		return itRt;
	}
	public void setItRt(BigDecimal itRt) {
		this.itRt = itRt;
	}
	public BigDecimal getDebtLong() {
		return debtLong;
	}
	public void setDebtLong(BigDecimal debtLong) {
		this.debtLong = debtLong;
	}
	public BigDecimal getDebtShort() {
		return debtShort;
	}
	public void setDebtShort(BigDecimal debtShort) {
		this.debtShort = debtShort;
	}
	public BigDecimal getVifCCQPrice() {
		return vifCCQPrice;
	}
	public void setVifCCQPrice(BigDecimal vifCCQPrice) {
		this.vifCCQPrice = vifCCQPrice;
	}
	public BigDecimal getVifAmountCCQ() {
		return vifAmountCCQ;
	}
	public void setVifAmountCCQ(BigDecimal vifAmountCCQ) {
		this.vifAmountCCQ = vifAmountCCQ;
	}
	public BigDecimal getTotalAsset() {
		return totalAsset;
	}
	public void setTotalAsset(BigDecimal totalAsset) {
		this.totalAsset = totalAsset;
	}
	public BigDecimal getTotalDebt() {
		return totalDebt;
	}
	public void setTotalDebt(BigDecimal totalDebt) {
		this.totalDebt = totalDebt;
	}
	
}
