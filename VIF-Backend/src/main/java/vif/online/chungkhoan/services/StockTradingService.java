package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.StockTracking;

public interface StockTradingService {
	List<StockTracking> getAllStockTracking();
	
	StockTracking getStockTrackById(int id);

//	User getUserByUserName(String user_name);

	boolean addStockTracking(StockTracking stockTracking);

	void updateStockTracking(StockTracking stockTracking);

	void deleteStockTrackingById(int id);
	
	void deleteStockTrackingByCode(String stockCode);
	
	StockTracking getStockTrackingByCode(String stockCode);

	int getRowCount(String stockCode);

	List<StockTracking> getStockTrackingByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String stockCode);
}
