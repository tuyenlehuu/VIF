package vif.online.chungkhoan.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.StockTrackingDao;
import vif.online.chungkhoan.entities.StockTracking;
import vif.online.chungkhoan.services.StockTradingService;

@Service(value = "stockTrackingService")
public class StockTrackingServiceImpl implements StockTradingService{

	@Autowired
	private StockTrackingDao stockTrackingDao;
	
	@Override
	public List<StockTracking> getAllStockTracking() {
		// TODO Auto-generated method stub
		return stockTrackingDao.getAllStockTracking();
	}

	@Override
	public StockTracking getStockTrackById(int id) {
		// TODO Auto-generated method stub
		return stockTrackingDao.getStockTrackById(id);
	}

	@Override
	public synchronized boolean addStockTracking(StockTracking stockTracking) {
		// TODO Auto-generated method stub
		if (stockTrackingDao.stockTrackingExists(stockTracking)) {
			return false;
		} else {
			stockTrackingDao.addStockTracking(stockTracking);
			return true;
		}
	}

	@Override
	public void updateStockTracking(StockTracking stockTracking) {
		// TODO Auto-generated method stub
		stockTrackingDao.updateStockTracking(stockTracking);
	}

	@Override
	public void deleteStockTrackingById(int id) {
		// TODO Auto-generated method stub
		stockTrackingDao.deleteStockTrackingById(id);
	}

	@Override
	public void deleteStockTrackingByCode(String stockCode) {
		// TODO Auto-generated method stub
		stockTrackingDao.deleteStockTrackingByCode(stockCode);
	}

	@Override
	public StockTracking getStockTrackingByCode(String stockCode) {
		// TODO Auto-generated method stub
		return stockTrackingDao.getStockTrackingByCode(stockCode);
	}

	@Override
	public int getRowCount(String stockCode) {
		// TODO Auto-generated method stub
		return stockTrackingDao.getRowCount(stockCode);
	}

	@Override
	public List<StockTracking> getStockTrackingByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String stockCode) {
		// TODO Auto-generated method stub
		return stockTrackingDao.getStockTrackingByCondition(page, pageSize, columnSortName, asc, stockCode);
	}
	
}
