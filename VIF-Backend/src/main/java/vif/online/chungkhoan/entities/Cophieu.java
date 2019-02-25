package vif.online.chungkhoan.entities;

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
@Table(name = "sharemaster")
public class Cophieu {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(unique = true, name = "code")
	private String cpCode;
	
	@Column(name = "name")
	private String cpName;
	
	@Column(name = "current_price")
	private String cpPrice;
	
	@Column(name = "last_update", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	/*
	 * @OneToMany(mappedBy = "from", cascade = CascadeType.ALL) private Set<Post>
	 * posts;
	 * 
	 * @OneToMany(mappedBy = "from", cascade = CascadeType.ALL) private Set<Comment>
	 * comments;
	 */

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpCode() {
		return cpCode;
	}

	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getCpPrice() {
		return cpPrice;
	}

	public void setCpPrice(String cpPrice) {
		this.cpPrice = cpPrice;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
