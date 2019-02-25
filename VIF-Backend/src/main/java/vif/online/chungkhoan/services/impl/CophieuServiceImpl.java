package vif.online.chungkhoan.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.CophieuDao;
import vif.online.chungkhoan.entities.Cophieu;
import vif.online.chungkhoan.services.CophieuService;

@Service
public class CophieuServiceImpl implements CophieuService {

	@Autowired
	CophieuDao cpDao;

	@Override
	public List<Cophieu> getAllCophieu() {
		// TODO Auto-generated method stub
		return cpDao.getAllCophieus();
	}

	@Override
	public Cophieu getCophieuById(int id) {
		// TODO Auto-generated method stub
		return cpDao.getCophieuById(id);
	}

	@Override
	public synchronized boolean addCophieu(Cophieu cophieu) {
		// TODO Auto-generated method stub
		if (cpDao.cophieuExists(cophieu)) {
			return false;
		} else {
			cpDao.addCophieu(cophieu);
			return true;
		}
	}

	@Override
	public void updateCophieu(Cophieu cophieu) {
		// TODO Auto-generated method stub
		cpDao.updateCophieu(cophieu);
	}

	@Override
	public void deleteCophieu(String cophieuCode) {
		// TODO Auto-generated method stub
		cpDao.deleteCophieu(cophieuCode);
	}

	@Override
	public Cophieu getCophieuByCode(String code) {
		// TODO Auto-generated method stub
		return cpDao.getCophieuByCode(code);
	}

}
