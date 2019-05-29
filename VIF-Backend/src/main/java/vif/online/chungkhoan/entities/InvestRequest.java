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

@Entity
@Table(name = "invest_request"  ,uniqueConstraints = { @UniqueConstraint(columnNames = { "ID" }) })
public class InvestRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "TYPE_OF_REQUEST")
    private Integer typeOfRequest; // 1-gui tien, 2-rut tien
	
	@Column(name = "TYPE_OF_INVEST")
    private Integer typeOfInvest; // 1-CCQ, 2-CCQ dam bao

	@Column(name = "CREATE_DATE", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@Column(name="STATUS")
	private Integer status =1; // 1-pending , 2-approval, 3-cancel 
	
	@Column(name="PRICE")
	private BigDecimal price;
	
	@Column(name="MONEY")
	private BigDecimal money;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;
	
	
	public Integer getTypeOfRequest() {
		return typeOfRequest;
	}

	public void setTypeOfRequest(Integer typeOfRequest) {
		this.typeOfRequest = typeOfRequest;
	}
	
	public Integer getTypeOfInvest() {
		return typeOfInvest;
	}

	public void setTypeOfInvest(Integer typeOfInvest) {
		this.typeOfInvest = typeOfInvest;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	
	
	
	
	
	

	
}
