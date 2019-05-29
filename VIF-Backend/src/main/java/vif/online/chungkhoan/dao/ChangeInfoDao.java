package vif.online.chungkhoan.dao;

import org.springframework.web.multipart.MultipartFile;

import vif.online.chungkhoan.entities.User;

public interface ChangeInfoDao {

	User getUserById(int id);
	void updateUser(User user);
	String saveFileAvatar(MultipartFile file);

}
