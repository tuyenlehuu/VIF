package vif.online.chungkhoan.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.AssetDao;
import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.services.AssetService;

@Service(value = "assetService")
public class AssetServiceImpl implements AssetService{

	@Autowired
	private AssetDao assetDao;
	
	@Override
	public void updateAsset(Asset asset) {
		// TODO Auto-generated method stub
		assetDao.updateAsset(asset);
	}

	@Override
	public Asset getAssetByCode(String assetCode) {
		// TODO Auto-generated method stub
		return assetDao.getAssetByCode(assetCode);
	}

	@Override
	public boolean addAsset(Asset asset) {
		// TODO Auto-generated method stub
		return assetDao.addAsset(asset);
	}

	@Override
	public List<Asset> getAllAsset() {
		// TODO Auto-generated method stub
		return assetDao.getAllAssets();
	}

}
