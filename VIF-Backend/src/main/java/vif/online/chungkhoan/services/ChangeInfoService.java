package vif.online.chungkhoan.services;

import org.springframework.web.multipart.MultipartFile;

import vif.online.chungkhoan.entities.User;

public interface ChangeInfoService {

	User getUserById(int id);
	void updateUser(User user);
	String saveFileAvatar(MultipartFile file);
}
