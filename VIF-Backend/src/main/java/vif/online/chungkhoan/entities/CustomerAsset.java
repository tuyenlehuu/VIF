package vif.online.chungkhoan.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_asset")
public class CustomerAsset implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "CUSTOMER_ID", nullable = false)
	private Integer customerId;
	
	@Column(name = "ASSET_ID", nullable = false)
	private Integer assetId;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "ORGINAL_PRICE")
	private BigDecimal orginalPrice;
	
	@Column(name = "CURRENT_PRICE", nullable = false)
	private BigDecimal currentPrice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

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

	public BigDecimal getOrginalPrice() {
		return orginalPrice;
	}

	public void setOrginalPrice(BigDecimal orginalPrice) {
		this.orginalPrice = orginalPrice;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}	
}
