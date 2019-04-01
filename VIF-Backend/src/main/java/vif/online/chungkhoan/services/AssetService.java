package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.Asset;

public interface AssetService {
	void updateAsset(Asset asset);

	Asset getAssetByCode(String assetCode);

	boolean addAsset(Asset asset);
	
	List<Asset> getAllAsset();

}
