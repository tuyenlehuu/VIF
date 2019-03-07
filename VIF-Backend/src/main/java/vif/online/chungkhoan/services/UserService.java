package vif.online.chungkhoan.services;

import vif.online.chungkhoan.entities.User;

public interface UserService {
//	List<User> getAllUsers();
	User getUserById(int id);
	User getUserByUserName(String user_name);
    boolean addUser(User user);
    void updateUser(User user);
    void deleteUser(String user_name);
    boolean authenUser(String username, String password);
}
