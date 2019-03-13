package vif.online.chungkhoan.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "USER_NAME", nullable = false)
	private String username;

	@Column(name = "FULL_NAME", nullable = false)
	private String fullName;

	@Column(name = "AVATAR")
	private String avatar;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ORGINAL_CCQ_PRICE")
	private BigDecimal orginalCCQPrice;
	
	@Column(name = "TOTAL_CCQ")
	private BigDecimal totalCcq;
	
	@Column(name = "IDENTITY_NUMBER", nullable = false)
	private String identityNumber;
	
	@Column(name = "DATE_OF_BIRTH", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfBirth;
	
	@Column(name = "SIGN_CONTRACT_DATE", columnDefinition = "DATETIME", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date signContractDate;
	
	@Column(name = "IDENTITY_DOCUMENT")
	private String identityDoc;
	
	@Column(name = "ACTIVE_FLG", nullable = false)
    private Integer activeFlg = 1;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<User> users = new ArrayList<>();
	
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InvestorHistory> investorHistorylst = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getTotalCcq() {
		return totalCcq;
	}

	public void setTotalCcq(BigDecimal totalCcq) {
		this.totalCcq = totalCcq;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<InvestorHistory> getInvestorHistorylst() {
		return investorHistorylst;
	}

	public void setInvestorHistorylst(List<InvestorHistory> investorHistorylst) {
		this.investorHistorylst = investorHistorylst;
	}
}
