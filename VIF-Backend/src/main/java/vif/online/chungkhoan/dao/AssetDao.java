package vif.online.chungkhoan.dao;

import vif.online.chungkhoan.entities.Asset;

public interface AssetDao {
	public boolean updateAsset(Asset asset);

	public Asset getAssetByCode(String assetCode);

	public boolean addAsset(Asset asset);
}
