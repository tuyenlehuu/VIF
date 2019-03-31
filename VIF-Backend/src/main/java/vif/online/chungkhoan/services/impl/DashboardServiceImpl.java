package vif.online.chungkhoan.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.DashboardDao;
import vif.online.chungkhoan.entities.DashBoard;
import vif.online.chungkhoan.services.DashboardService;

@Service(value = "dashboardService")
public class DashboardServiceImpl implements DashboardService{

	@Autowired
	private DashboardDao dashboardDao;
	
	@Override
	public DashBoard getData() {
		// TODO Auto-generated method stub
		return dashboardDao.getData();
	}

}
