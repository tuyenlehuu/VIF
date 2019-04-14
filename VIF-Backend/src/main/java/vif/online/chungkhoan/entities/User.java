package vif.online.chungkhoan.entities;

import java.io.Serializable;
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
@Table(name="user", uniqueConstraints = { @UniqueConstraint(columnNames = { "USER_NAME" }) })
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "USER_NAME", nullable=false)
    private String username;
    
    @Column(name = "PASSWORD", nullable=false)
    private String password;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "ACTIVE_FLG")
    private Integer activeFlg;
    
    @Column(name = "ROLE")
    private String role;
    
    @Column(name = "IS_ONLINE")
    private Integer isOnline = 0;
    
    @Column(name = "IS_DELETED")
    private Integer isDeleted = 0;
    
    @Column(name = "TOKEN_RESET")
    private String tokenReset;
    
    @Column(name = "TOKEN_RESET_EXPRIED", columnDefinition = "DATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenResetExpried;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;
	
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getActiveFlg() {
		return activeFlg;
	}

	public void setActiveFlg(Integer activeFlg) {
		this.activeFlg = activeFlg;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String returnPassword() {
		return password;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getPassword() {
		return password;
	}

	public String getTokenReset() {
		return tokenReset;
	}

	public void setTokenReset(String tokenReset) {
		this.tokenReset = tokenReset;
	}

	public Date getTokenResetExpried() {
		return tokenResetExpried;
	}

	public void setTokenResetExpried(Date tokenResetExpried) {
		this.tokenResetExpried = tokenResetExpried;
	}

	
}
