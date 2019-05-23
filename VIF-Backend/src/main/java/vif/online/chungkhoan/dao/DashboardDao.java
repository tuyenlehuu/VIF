package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.Asset;
import vif.online.chungkhoan.entities.DashBoard;
import vif.online.chungkhoan.helper.KeyNameValueDTO;
import vif.online.chungkhoan.helper.NAVDTO;

public interface DashboardDao {
	DashBoard getData();
	List<KeyNameValueDTO> getDataTotalAsset();
	List<NAVDTO> getNAVReport(Integer customerId, String fromDate, String toDate);
	
	List<KeyNameValueDTO> getNavChartData(boolean isByMonth);
	List<Asset> getDebtDataByUsername(String username);
}
