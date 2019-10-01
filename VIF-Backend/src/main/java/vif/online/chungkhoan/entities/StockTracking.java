package vif.online.chungkhoan.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "stock_tracking")
public class StockTracking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CODE", nullable = false)
	private String code;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ORGINAL_PRICE")
	private BigDecimal orginalPrice;
	
	@Column(name = "CURRENT_PRICE")
	private BigDecimal currentPrice;

	@Column(name = "TARGET_PRICE_BUY")
	private BigDecimal targetPriceBuy;
	
	@Column(name = "TARGET_PRICE_SELL")
	private BigDecimal targetPriceSell;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CREATE_DATE", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "LAST_UPDATE", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@Column(name = "STATUS")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public BigDecimal getTargetPriceBuy() {
		return targetPriceBuy;
	}

	public void setTargetPriceBuy(BigDecimal targetPriceBuy) {
		this.targetPriceBuy = targetPriceBuy;
	}

	public BigDecimal getTargetPriceSell() {
		return targetPriceSell;
	}

	public void setTargetPriceSell(BigDecimal targetPriceSell) {
		this.targetPriceSell = targetPriceSell;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
