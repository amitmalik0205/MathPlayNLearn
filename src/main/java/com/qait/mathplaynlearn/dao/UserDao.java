package com.qait.mathplaynlearn.dao;

import com.qait.mathplaynlearn.domain.User;

public interface UserDao {

	public boolean saveUser(User user);
	
	public User getUserByEmail(String email);
	
	public User getUserByUserId(String email);
	
	public User authenticateUser(String userId, String password);
}
