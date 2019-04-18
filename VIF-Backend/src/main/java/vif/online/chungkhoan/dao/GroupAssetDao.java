package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.GroupAsset;

public interface GroupAssetDao {
	
	public List<GroupAsset> getAlls();

	GroupAsset getByGroupById(int groupId);
	
	GroupAsset getGroupByCode(String code);
	
	public List<GroupAsset> getAllGroupsAssets();

}
