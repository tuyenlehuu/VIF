package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.GroupAsset;

public interface GroupAssetService {
	public List<GroupAsset> getAlls();

	GroupAsset getByGroupById(int groupId);
	
	GroupAsset getGroupByCode(String code);
	
	public List<GroupAsset> getAllGroupsAssets();
}
