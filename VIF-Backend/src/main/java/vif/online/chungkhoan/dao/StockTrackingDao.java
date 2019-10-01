package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.StockTracking;

public interface StockTrackingDao {
	List<StockTracking> getAllStockTracking();
	
	StockTracking getStockTrackById(int id);

	boolean addStockTracking(StockTracking stockTracking);

	void updateStockTracking(StockTracking stockTracking);

	void deleteStockTrackingById(int id);
	
	void deleteStockTrackingByCode(String stockCode);
	
	StockTracking getStockTrackingByCode(String stockCode);

	boolean stockTrackingExists(StockTracking stockTracking);

	int getRowCount(String stockCode);

	List<StockTracking> getStockTrackingByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String stockCode);
}
