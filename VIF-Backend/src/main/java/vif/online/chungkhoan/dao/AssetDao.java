package vif.online.chungkhoan.dao;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.User;

import java.util.List;

public interface AssetDao {
	List<Asset> getAllAssets();
	
	boolean updateAsset(Asset asset);

	Asset getAssetByCode(String assetCode);

	boolean addAsset(Asset asset);
	
	boolean deleteAssetByCode(String code);
	
	boolean deleteAssetById(int id);
	
	List<Asset> SearchAssetByCondition(int page, int pageSize, String columnSortName, Boolean asc, String code,
			Integer activeFlg, int group_id, String branch_code);
}
