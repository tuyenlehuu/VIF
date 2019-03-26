package vif.online.chungkhoan.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.AppParamDao;
import vif.online.chungkhoan.entities.AppParam;
import vif.online.chungkhoan.services.AppParamService;

@Service(value = "appParamService")
public class AppParamServiceImpl implements AppParamService{

	@Autowired
	private AppParamDao appParamDao;
	
	@Override
	public void updateAppParam(AppParam appParam) {
		// TODO Auto-generated method stub
		appParamDao.updateAppParam(appParam);
	}

	@Override
	public void deleteAppParamById(Integer id) {
		// TODO Auto-generated method stub
		appParamDao.deleteAppParamById(id);
	}

	@Override
	public AppParam getAppParamById(Integer id) {
		// TODO Auto-generated method stub
		return appParamDao.getAppParambyId(id);
	}

	@Override
	public boolean addAppParam(AppParam appParam) {
		// TODO Auto-generated method stub
		return appParamDao.addAppParam(appParam);
	}

	@Override
	public List<AppParam> getAllAppParam() {
		// TODO Auto-generated method stub
		return appParamDao.getAllAppParam();
	}
}
