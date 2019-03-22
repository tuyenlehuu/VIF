package vif.online.chungkhoan.services;

import vif.online.chungkhoan.entities.Asset;

public interface AssetService {
	public void updateAsset(Asset asset);

	public Asset getAssetByCode(String assetCode);
	
	public boolean addAsset(Asset asset);
}
