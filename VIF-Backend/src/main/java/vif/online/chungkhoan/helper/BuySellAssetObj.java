package vif.online.chungkhoan.helper;

import java.io.Serializable;
import java.math.BigDecimal;

public class BuySellAssetObj implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer assetId;
	BigDecimal money;
	BigDecimal amount;
	BigDecimal price;
	public Integer getAssetId() {
		return assetId;
	}
	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	
}
