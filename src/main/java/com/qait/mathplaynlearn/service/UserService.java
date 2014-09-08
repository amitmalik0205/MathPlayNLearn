package com.qait.mathplaynlearn.service;

import javax.ws.rs.core.Response;

import com.qait.mathplaynlearn.domain.User;

public interface UserService {

	public Response saveUser(User user);
	
	public Response authenticateUser(String userId, String password);
}
