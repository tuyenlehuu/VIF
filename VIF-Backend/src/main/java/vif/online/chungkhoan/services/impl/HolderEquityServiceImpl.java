package vif.online.chungkhoan.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.HolderEquityDao;
import vif.online.chungkhoan.entities.HolderEquity;
import vif.online.chungkhoan.services.HolderEquityService;

@Service
public class HolderEquityServiceImpl implements HolderEquityService {
	@Autowired
	private HolderEquityDao holderEquityDao;

	@Override
	public List<HolderEquity> getAllsHolder() {
		// TODO Auto-generated method stub
		return holderEquityDao.getAllsHolder();
	}

	@Override
	public HolderEquity getHolderbyId(Integer id) {
		// TODO Auto-generated method stub
		return holderEquityDao.getHolderbyId(id);
	}

	@Override
	public boolean addHolder(HolderEquity holderEquity) {
		// TODO Auto-generated method stub
		return holderEquityDao.addHolder(holderEquity);
	}

	@Override
	public void updateHolder(HolderEquity holderEquity) {
		// TODO Auto-generated method stub
		holderEquityDao.updateHolder(holderEquity);

	}

	@Override
	public void deleteHolderById(Integer id) {
		// TODO Auto-generated method stub
		holderEquityDao.deleteHolderById(id);
	}

	@Override
	public List<HolderEquity> SearchHolderByCondition(int page, int pageSize, String columnSortName, Boolean asc,
			String fromDate, String toDate, String fullName) {
		// TODO Auto-generated method stub
		return holderEquityDao.SearchHolderByCondition(page, pageSize, columnSortName, asc, fromDate, toDate, fullName);
	}

	@Override
	public int getRowCount(String fromDate, String toDate, String fullName) {
		// TODO Auto-generated method stub
		return holderEquityDao.getRowCount(fromDate, toDate, fullName);
	}

}
