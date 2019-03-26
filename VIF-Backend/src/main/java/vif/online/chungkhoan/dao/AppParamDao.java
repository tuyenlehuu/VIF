package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.AppParam;

public interface AppParamDao {
	List<AppParam> getAllAppParam();
	AppParam getAppParambyId(int id);
	boolean addAppParam(AppParam appParam);
	void deleteAppParamById(int id);
//	void updateAppParam(AppParam appParam, Integer id);
	boolean updateAppParam(AppParam appParam);
	boolean isExist(AppParam appParam);
}
