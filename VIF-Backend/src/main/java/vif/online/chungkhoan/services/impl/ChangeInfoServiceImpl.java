package vif.online.chungkhoan.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vif.online.chungkhoan.dao.ChangeInfoDao;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.services.ChangeInfoService;

@Service(value = "changeInfoService")
public class ChangeInfoServiceImpl implements ChangeInfoService{

	@Autowired
	private ChangeInfoDao changeInfoDao;
	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return changeInfoDao.getUserById(id);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		changeInfoDao.updateUser(user);
	}

	@Override
	public String saveFileAvatar(MultipartFile file) {
		// TODO Auto-generated method stub
		return changeInfoDao.saveFileAvatar(file);
	}

}
