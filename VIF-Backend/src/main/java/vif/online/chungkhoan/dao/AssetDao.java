package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.Asset;

public interface AssetDao {
	
	boolean updateAsset(Asset asset);

	Asset getAssetByCode(String assetCode);

	
	boolean deleteAssetByCode(String code);
	
	boolean deleteAssetById(int id);
	
	List<Asset> SearchAssetByCondition(int page, int pageSize, String columnSortName, Boolean asc, String code,
			Integer activeFlg, int group_id, String branch_code);

	public boolean addAsset(Asset asset);

	public List<Asset> getAlls();

	Asset getByAssetId(int assetId);

	public List<Asset> getAllShares();
	
	public List<Asset> getAllSharesForBuy();

	List<Asset> getOtherAssetNotShares();

	boolean isExists(Asset asset);

	Asset getAssetById(Integer id);

	List<Asset> searchAssetsByCondition(int page, int pageSize, String columnSortName, Boolean asc, String assetCode,
			Integer groupAssetId, String assetName);

	int getRowCount(String assetCode, Integer groupAssetId, String assetName);

	List<Asset> getAssetByGroupId(Integer groupId);

}
