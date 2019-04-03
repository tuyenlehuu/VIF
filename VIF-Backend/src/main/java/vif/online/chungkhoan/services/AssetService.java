package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.Asset;

public interface AssetService {
	public void updateAsset(Asset asset);

	public Asset getAssetByCode(String assetCode);
	
	public boolean addAsset(Asset asset);

	public List<Asset> getAlls();

	public List<Asset> getAllShares();
}
