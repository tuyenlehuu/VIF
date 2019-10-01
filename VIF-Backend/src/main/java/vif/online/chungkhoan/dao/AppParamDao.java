package vif.online.chungkhoan.dao;

import java.util.List;

import vif.online.chungkhoan.entities.AppParam;

public interface AppParamDao {
	List<AppParam> getAllAppParam();
	AppParam getAppParambyId(int id);
	boolean addAppParam(AppParam appParam);
	void deleteAppParamById(int id);
//	void updateAppParam(AppParam appParam, Integer id);
	void updateAppParam(AppParam appParam);
	boolean isExist(AppParam appParam);
	List<AppParam> SearchAppParamByCondition(int page, int pageSize, String columnSortName, Boolean asc, String propKey,Integer activeFlg,String propType,String propValueg, String description);
	int getRowCount(String propKey, Integer activeFlg, String propType, String propValue, String description);
	AppParam getAppParamByPropKey(String PropKey);
	AppParam getAppParamByKeyType(String propKey, String propType);
	List<AppParam> getConfigByType(String appType);
}
