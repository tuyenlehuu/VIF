package vif.online.chungkhoan.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.ShareMasterDao;
import vif.online.chungkhoan.entities.ShareMaster;
import vif.online.chungkhoan.services.ShareMasterService;

@Service
public class ShareMasterServiceImpl implements ShareMasterService {

	@Autowired
	ShareMasterDao cpDao;

	@Override
	public List<ShareMaster> getAllCophieu() {
		// TODO Auto-generated method stub
		return cpDao.getAllCophieus();
	}

	@Override
	public ShareMaster getCophieuById(int id) {
		// TODO Auto-generated method stub
		return cpDao.getCophieuById(id);
	}

	@Override
	public synchronized boolean addCophieu(ShareMaster cophieu) {
		// TODO Auto-generated method stub
		if (cpDao.cophieuExists(cophieu)) {
			return false;
		} else {
			cpDao.addCophieu(cophieu);
			return true;
		}
	}

	@Override
	public void updateCophieu(ShareMaster cophieu) {
		// TODO Auto-generated method stub
		cpDao.updateCophieu(cophieu);
	}

	@Override
	public void deleteCophieu(String cophieuCode) {
		// TODO Auto-generated method stub
		cpDao.deleteCophieu(cophieuCode);
	}

	@Override
	public ShareMaster getCophieuByCode(String code) {
		// TODO Auto-generated method stub
		return cpDao.getCophieuByCode(code);
	}

}
