package vif.online.chungkhoan.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.HolderHistoryDao;
import vif.online.chungkhoan.entities.HolderHistory;
import vif.online.chungkhoan.services.HolderHistoryService;

@Service
public class HolderHistoryServiceImpl implements HolderHistoryService {
	@Autowired
	private HolderHistoryDao holderHistoryDao;
	
	@Override
	public List<HolderHistory> getAllsHolder() {
		// TODO Auto-generated method stub
		return holderHistoryDao.getAllsHolder();
	}

	@Override
	public boolean addHolder(HolderHistory holderHistory) {
		// TODO Auto-generated method stub
		return holderHistoryDao.addHolders(holderHistory);
	}

}
