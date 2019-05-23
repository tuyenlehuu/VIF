package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.AppParam;

public interface AppParamService {
	List<AppParam> getAllAppParam();
	AppParam getAppParamById(Integer id);
	boolean addAppParam(AppParam appParam);
	void updateAppParam(AppParam appParam);
	void deleteAppParamById(Integer id);
	List<AppParam> SearchAppParamByCondition(int page, int pageSize, String columnSortName, Boolean asc, String propKey,
			Integer activeFlg, String propType, String propValue, String description);
	int getRowCount(String propKey, Integer activeFlg, String propType, String propValue, String description);
	public AppParam getAppParamByKeyType(String propKey, String propType);
}
