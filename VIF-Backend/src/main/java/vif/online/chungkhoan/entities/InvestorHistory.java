package vif.online.chungkhoan.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="investor_history", uniqueConstraints = { @UniqueConstraint(columnNames = { "ID" }) })
public class InvestorHistory implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
	
	@Column(name = "CODE", nullable=false)
    private String code;
	
	@Column(name = "AMOUNT_CCQ", nullable=false)
	private BigDecimal amountCCQ;
	
	@Column(name = "PRICE_OF_CCQ", nullable=false)
	private BigDecimal priceOfCCQ;
	
	@Column(name = "AMOUNT_CCQ_BEFORE", nullable=false)
	private BigDecimal amountCCQBefore;
	
	@Column(name = "PRICE_OF_CCQ_BEFORE", nullable=false)
	private BigDecimal priceOfCCQBefore;
	
	@Column(name = "TYPE_OF_TRANSACTION")
    private String typeOfTransaction;
	
	@Column(name = "CREATE_DATE", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name = "LAST_UPDATE", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;
	
	@Column(name = "TYPE_OF_INVEST")
    private Integer typeOfInvest;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getAmountCCQ() {
		return amountCCQ;
	}

	public void setAmountCCQ(BigDecimal amountCCQ) {
		this.amountCCQ = amountCCQ;
	}

	public BigDecimal getPriceOfCCQ() {
		return priceOfCCQ;
	}

	public void setPriceOfCCQ(BigDecimal priceOfCCQ) {
		this.priceOfCCQ = priceOfCCQ;
	}

	public String getTypeOfTransaction() {
		return typeOfTransaction;
	}

	public void setTypeOfTransaction(String typeOfTransaction) {
		this.typeOfTransaction = typeOfTransaction;
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


	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public BigDecimal getAmountCCQBefore() {
		return amountCCQBefore;
	}

	public void setAmountCCQBefore(BigDecimal amountCCQBefore) {
		this.amountCCQBefore = amountCCQBefore;
	}

	public BigDecimal getPriceOfCCQBefore() {
		return priceOfCCQBefore;
	}

	public void setPriceOfCCQBefore(BigDecimal priceOfCCQBefore) {
		this.priceOfCCQBefore = priceOfCCQBefore;
	}

	public Integer getTypeOfInvest() {
		return typeOfInvest;
	}

	public void setTypeOfInvest(Integer typeOfInvest) {
		this.typeOfInvest = typeOfInvest;
	}
}
