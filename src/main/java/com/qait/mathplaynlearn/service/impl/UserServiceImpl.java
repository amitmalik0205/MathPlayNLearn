package com.qait.mathplaynlearn.service.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qait.mathplaynlearn.dao.UserDao;
import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.rest.service.MathPlayNLearnServiceResponse;
import com.qait.mathplaynlearn.service.UserService;
import com.qait.mathplaynlearn.util.MathPlayPropertiesFileReaderUtil;

@Service("userService")

public class UserServiceImpl implements UserService {
	
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	
	public Response saveUser(User user) {
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();
		response.setCode("rsgisterUser001");
		String status = MathPlayPropertiesFileReaderUtil
				.getPropertyValue("rsgisterUser001");
		if (userDao.getUserByUserId(user.getUserID()) != null) {
			response.setCode("rsgisterUser003");
			status = MathPlayPropertiesFileReaderUtil
					.getPropertyValue("rsgisterUser003");
		} else if (!userDao.saveUser(user)) {
			response.setCode("rsgisterUser002");
			status = MathPlayPropertiesFileReaderUtil
					.getPropertyValue("rsgisterUser002");
		}

		response.setMessage(status);
		return Response.status(200).entity(response).build();
	}
	
	@Override
	public User authenticateUser(String userId, String password) {
		return userDao.authenticateUser(userId, password);
	}
	
	@Override
	public List<Object[]> getMatchingUserID(String str) {
		return userDao.getMatchingUserID(str);
	}
	
	@Override
	public User getUserByUserId(String userId) {
		return userDao.getUserByUserId(userId);
	}
	
	@Override
	public User getUserWithSecurityQuestion(String userId) {
		return userDao.getUserWithSecurityQuestion(userId);
	}
	
	@Override
	public User getUser(long id) {
		return userDao.getUser(id);
	}
	
	@Override
	public List<Object[]> getMatchingUserIDForGroup(String str, long groupID) {
		return userDao.getMatchingUserIDForGroup(str, groupID);
	}
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
