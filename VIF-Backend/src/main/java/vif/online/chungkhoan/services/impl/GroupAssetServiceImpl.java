package vif.online.chungkhoan.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.GroupAssetDao;
import vif.online.chungkhoan.entities.GroupAsset;
import vif.online.chungkhoan.services.GroupAssetService;

@Service(value = "groupAssetService")
public class GroupAssetServiceImpl implements GroupAssetService{

	@Autowired
	private GroupAssetDao groupAssetDao;
	
	@Override
	public List<GroupAsset> getAlls() {
		// TODO Auto-generated method stub
		return groupAssetDao.getAlls();
	}

	@Override
	public GroupAsset getByGroupById(int groupId) {
		// TODO Auto-generated method stub
		return groupAssetDao.getByGroupById(groupId);
	}

	@Override
	public GroupAsset getGroupByCode(String code) {
		// TODO Auto-generated method stub
		return groupAssetDao.getGroupByCode(code);
	}

	@Override
	public List<GroupAsset> getAllGroupsAssets() {
		// TODO Auto-generated method stub
		return groupAssetDao.getAllGroupsAssets();
	}

}
