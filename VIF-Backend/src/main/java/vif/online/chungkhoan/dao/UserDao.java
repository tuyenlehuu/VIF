package vif.online.chungkhoan.dao;

import vif.online.chungkhoan.entities.User;

public interface UserDao {
//	List<User> getAllUsers();
	User getUserById(int id);
	User getUserByUserName(String user_name);
    boolean addUser(User user);
    void updateUser(User user);
    void deleteUser(String user_name);
    boolean userExists(User user);
	boolean authenUser(String username, String password);
}
