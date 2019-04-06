package vif.online.chungkhoan.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="group_asset")
public class GroupAsset implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "CODE", nullable = false)
	private String groupCode;
	
	@Column(name = "NAME", nullable = false)
	private String groupName;
	
	@Column(name = "TYPE_OF_ASSET", nullable = false)
	private String typeOfAsset;
	
	@Column(name = "ACTIVE_FLG")
    private Integer activeFlg = 1;
	
	@OneToMany(mappedBy = "groupAsset", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Asset> assets = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getTypeOfAsset() {
		return typeOfAsset;
	}

	public void setTypeOfAsset(String typeOfAsset) {
		this.typeOfAsset = typeOfAsset;
	}

	public Integer getActiveFlg() {
		return activeFlg;
	}

	public void setActiveFlg(Integer activeFlg) {
		this.activeFlg = activeFlg;
	}

	@JsonIgnore
	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}
	
}
