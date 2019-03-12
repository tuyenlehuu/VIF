package vif.online.chungkhoan.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vif.online.chungkhoan.dao.UserDao;
import vif.online.chungkhoan.entities.User;

@Transactional
@Repository
public class UserDaoImpl implements UserDao{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<User> getAllUsers() { // TODO Auto-generated method
	 * stub String hql = "FROM User as u ORDER BY u.username asc"; return
	 * (List<User>) entityManager.createQuery(hql).getResultList(); }
	 */

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(User.class, id);
	}

	@Override
	public User getUserByUserName(String user_name) {
		// TODO Auto-generated method stub
		String hql = "FROM User as u WHERE u.username = :username";
		@SuppressWarnings("unchecked")
		List<User> lstResult = entityManager.createQuery(hql).setParameter("username", user_name).getResultList();
		if (lstResult != null && lstResult.size() > 0) {
			return lstResult.get(0);
		}
		return null;
	}

	@Override
	public boolean addUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword(passwordEncoder.encode(user.returnPassword()));
		entityManager.persist(user);
		return true;
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		entityManager.flush();
//		entityManager.getTransaction().commit();
	}

	@Override
	public void deleteUser(String user_name) {
		// TODO Auto-generated method stub
		entityManager.remove(getUserByUserName(user_name));
	}

	@Override
	public boolean userExists(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean authenUser(String username, String password) {
		// TODO Auto-generated method stub
		String hql = "FROM User as u WHERE u.username = :username AND u.password = :password AND u.activeFlg = 1 AND u.isDeleted = 0";
		List<User> lstUsers = (List<User>) entityManager.createQuery(hql).setParameter("username", username).setParameter("password", password).getResultList();
		if(lstUsers != null && lstUsers.size()>0) {
			return true;
		}
		return false;
	}
}
