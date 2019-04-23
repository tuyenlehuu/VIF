package vif.online.chungkhoan.services;

import java.math.BigDecimal;
import java.util.List;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.ApiResponse;

public interface AssetService {
	
	public ApiResponse buySercurities(Integer assetId, BigDecimal amount, BigDecimal price);
	
	public ApiResponse sellSercurities(Integer assetId, BigDecimal amount, BigDecimal price);
	
	public ApiResponse dividendTrans(Integer assetId, BigDecimal amount, int dType, BigDecimal dRate);
	
	void updateAsset(Asset asset);

	Asset getAssetByCode(String assetCode);

	public boolean addAsset(Asset asset);
	
	public List<Asset> getAlls();
	
	public List<Asset> getAllShares();
	
	public List<Asset> getAllSharesForBuy();

	public List<Asset> getOtherAssetNotShares();

	public void deleteAssetByCode(String assetCode);

	public void deleteAssetById(Integer id);

	public Asset getAssetById(Integer id);

	public List<Asset> SearchAssetsByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String assetCode, Integer groupAssetId, String assetName);

	public int getRowCount(String assetCode, Integer groupAssetId, String assetName);

}
