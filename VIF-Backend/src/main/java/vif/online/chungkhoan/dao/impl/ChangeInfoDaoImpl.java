package vif.online.chungkhoan.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vif.online.chungkhoan.dao.ChangeInfoDao;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.helper.IContaints;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class ChangeInfoDaoImpl implements ChangeInfoDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(User.class, id);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		User mUser = entityManager.find(User.class, user.getId());
		user.setPassword(mUser.returnPassword());
		entityManager.merge(user);
		
	}

	@Override
	public String saveFileAvatar(MultipartFile file) {
		// TODO Auto-generated method stub
		String path = IContaints.FILE_UPLOAD.AVATAR_UPLOAD_DIRECTORY;
		String pathFile = "";
		try {
			String filename = file.getOriginalFilename();
			byte[] bytes = file.getBytes();
			File f = new File(path + "_time_" + System.currentTimeMillis() + "_time_" + filename);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(f));
			pathFile=f.getPath();
			stream.write(bytes);
			stream.flush();
			stream.close();

		} catch (IOException e) {
			return "Something wrong";
		}

		return pathFile;
	}

}
