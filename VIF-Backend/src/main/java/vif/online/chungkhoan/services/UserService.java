package vif.online.chungkhoan.services;

import java.util.List;

import vif.online.chungkhoan.entities.User;

public interface UserService {
	List<User> getAllUsers();
	
	User getUserById(int id);

	User getUserByUserName(String user_name);

	boolean addUser(User user);

	void updateUser(User user);

	void deleteUserByUsername(String user_name);

	boolean authenUser(String username, String password);

	List<User> SearchUserByCondition(int page, int pageSize, String columnSortName, Boolean asc, String username,
			Integer activeFlg, String email, String role);
	
	int getRowCount(String username, Integer activeFlg, String email, String role);

	void deleteUserById(Integer id);

	boolean resetPassword(String username, String token, String newPass);

	boolean prepareResetPassword(String username);
}
