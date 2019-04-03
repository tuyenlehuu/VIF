package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.DashBoard;
import vif.online.chungkhoan.helper.KeyNameValueDTO;

public interface DashboardService {
	DashBoard getData();
	List<KeyNameValueDTO> getDataTotalAsset();
}
