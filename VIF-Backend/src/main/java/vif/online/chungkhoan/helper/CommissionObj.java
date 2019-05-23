package vif.online.chungkhoan.helper;

import java.io.Serializable;
import java.math.BigDecimal;

public class CommissionObj implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Integer customerID;
	BigDecimal amount;
	String cType;

	public Integer getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Integer customerID) {
		this.customerID = customerID;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getcType() {
		return cType;
	}

	public void setcType(String cType) {
		this.cType = cType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
