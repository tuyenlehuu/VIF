package vif.online.chungkhoan.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vif.online.chungkhoan.dao.UserDao;
import vif.online.chungkhoan.entities.User;
import vif.online.chungkhoan.services.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userDao.getAllUsers();
	}

	@Override
	public User getUserById(int id) {
		// TODO Auto-generated method stub
		return userDao.getUserById(id);
	}

	@Override
	public User getUserByUserName(String user_name) {
		// TODO Auto-generated method stub
		return userDao.getUserByUserName(user_name);
	}

	@Override
	public synchronized boolean addUser(User user) {
		// TODO Auto-generated method stub
		if (userDao.userExists(user)) {
			return false;
		} else {
			userDao.addUser(user);
			return true;
		}
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userDao.updateUser(user);
	}

	@Override
	public void deleteUser(String user_name) {
		// TODO Auto-generated method stub
		userDao.deleteUser(user_name);
	}

	@Override
	public boolean authenUser(String username, String password) {
		// TODO Auto-generated method stub
		return userDao.authenUser(username, password);
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userDao.getUserByUserName(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}
	
	@SuppressWarnings("rawtypes")
	private List getAuthority(User user) {
		return Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
	}

}
