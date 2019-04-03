package vif.online.chungkhoan.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.DashboardDao;
import vif.online.chungkhoan.entities.DashBoard;
import vif.online.chungkhoan.helper.KeyNameValueDTO;
import vif.online.chungkhoan.helper.NAVDTO;
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

	@Override
	public List<KeyNameValueDTO> getDataTotalAsset() {
		// TODO Auto-generated method stub
		return dashboardDao.getDataTotalAsset();
	}

	@Override
	public List<NAVDTO> getNAVReport(Integer customerId, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return dashboardDao.getNAVReport(customerId, fromDate, toDate);
	}

}
