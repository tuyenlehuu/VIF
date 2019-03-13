package vif.online.chungkhoan.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="asset")
public class Asset implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

	@Column(name = "BRANCH_CODE")
	private String branchCode;
	
	@Column(name = "CODE", nullable = false)
	private String assetCode;
	
	@Column(name = "NAME", nullable = false)
	private String assetName;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@Column(name = "ORGINAL_PRICE")
	private BigDecimal orginalPrice;
	
	@Column(name = "CURRENT_PRICE", nullable = false)
	private BigDecimal currentPrice;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "ACTIVE_FLG")
    private Integer activeFlg = 1;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private GroupAsset groupAsset;
	
	@OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AssetHistory> assets = new ArrayList<>();
	
	@OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TransactionHistory> assetsTransaction = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getActiveFlg() {
		return activeFlg;
	}

	public void setActiveFlg(Integer activeFlg) {
		this.activeFlg = activeFlg;
	}

	@JsonIgnore
	public GroupAsset getGroupAsset() {
		return groupAsset;
	}

	public void setGroupAsset(GroupAsset groupAsset) {
		this.groupAsset = groupAsset;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	@JsonIgnore
	public List<AssetHistory> getAssets() {
		return assets;
	}

	public void setAssets(List<AssetHistory> assets) {
		this.assets = assets;
	}

	@JsonIgnore
	public List<TransactionHistory> getAssetsTransaction() {
		return assetsTransaction;
	}

	public void setAssetsTransaction(List<TransactionHistory> assetsTransaction) {
		this.assetsTransaction = assetsTransaction;
	}
}
