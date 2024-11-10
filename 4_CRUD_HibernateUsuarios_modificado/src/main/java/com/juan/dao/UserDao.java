package com.juan.dao;

import java.util.List;

import com.juan.model.User;
import org.hibernate.Session;

public interface UserDao {

	void  saveUser(User user, Session session);

	User getUserById(int id,Session session);

	List<User> getAllUsers(Session session);

	void updateUser(User user,Session session);

	void deleteUserById(int id,Session session);

}
