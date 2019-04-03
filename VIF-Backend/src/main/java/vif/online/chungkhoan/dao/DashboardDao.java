package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.DashBoard;
import vif.online.chungkhoan.helper.KeyNameValueDTO;

public interface DashboardDao {
	DashBoard getData();
	List<KeyNameValueDTO> getDataTotalAsset();
}
