package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.DashBoard;
import vif.online.chungkhoan.helper.KeyNameValueDTO;
import vif.online.chungkhoan.helper.NAVDTO;

public interface DashboardService {
	DashBoard getData();
	List<KeyNameValueDTO> getDataTotalAsset();
	List<NAVDTO> getNAVReport(Integer customerId, String fromDate, String toDate);
	List<KeyNameValueDTO> getNavChartData(boolean isByMonth);
}
