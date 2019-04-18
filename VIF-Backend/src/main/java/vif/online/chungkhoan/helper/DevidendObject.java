package vif.online.chungkhoan.helper;

import java.io.Serializable;
import java.math.BigDecimal;

public class DevidendObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer assetId;
	BigDecimal amount;
	BigDecimal type;
	BigDecimal rate;

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getType() {
		return type;
	}

	public void setType(BigDecimal type) {
		this.type = type;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

}
