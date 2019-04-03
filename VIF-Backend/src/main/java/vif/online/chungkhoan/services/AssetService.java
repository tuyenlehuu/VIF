package vif.online.chungkhoan.services;

import java.math.BigDecimal;
import java.util.List;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.helper.ApiResponse;

public interface AssetService {
	
	public ApiResponse buySercurities(Integer assetId, BigDecimal amount, BigDecimal price);
	
	public ApiResponse sellSercurities(Integer assetId, BigDecimal amount, BigDecimal price);
	
	public ApiResponse cashDividend(Integer assetId, BigDecimal amount, BigDecimal dividendRate);
	
	public ApiResponse sercuritiesDividend(Integer assetId, BigDecimal amount, BigDecimal dividendRate);
	
	void updateAsset(Asset asset);

	Asset getAssetByCode(String assetCode);

	public boolean addAsset(Asset asset);
	
	public List<Asset> getAlls();
	
	public List<Asset> getAllShares();

}
