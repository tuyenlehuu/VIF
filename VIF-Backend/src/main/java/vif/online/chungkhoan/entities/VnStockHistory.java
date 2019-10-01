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
@Table(name="VNSTOCK_HISTORY")
public class VnStockHistory implements Serializable{
private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
	
	@Column(name = "TIME_UPDATE", nullable=false)
    private String timeUpdate;
	
	@Column(name = "LAST_PRICE")
	private BigDecimal lastPrice;
	
	@Column(name = "OPEN_PRICE")
	private BigDecimal openPrice;
	
	@Column(name = "RATE_CHANGE")
	private BigDecimal rateChange;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTimeUpdate() {
		return timeUpdate;
	}

	public void setTimeUpdate(String timeUpdate) {
		this.timeUpdate = timeUpdate;
	}

	public BigDecimal getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(BigDecimal lastPrice) {
		this.lastPrice = lastPrice;
	}

	public BigDecimal getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(BigDecimal openPrice) {
		this.openPrice = openPrice;
	}

	public BigDecimal getRateChange() {
		return rateChange;
	}

	public void setRateChange(BigDecimal rateChange) {
		this.rateChange = rateChange;
	}
}
