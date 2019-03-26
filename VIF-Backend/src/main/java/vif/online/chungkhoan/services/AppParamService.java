package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.AppParam;

public interface AppParamService {
	List<AppParam> getAllAppParam();
	AppParam getAppParamById(Integer id);
	boolean addAppParam(AppParam appParam);
	void updateAppParam(AppParam appParam);
	void deleteAppParamById(Integer id);
}
