package com.qait.mathplaynlearn.service.impl;

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

/*	@Override
	public UserStatus saveUser(User user) {
		UserStatus status = UserStatus.SUCCESSFUL;
		if(userDao.getUserByUserId(user.getUserID()) == null) {
			status = UserStatus.USER_ID_EXISTS;
		} else if(userDao.getUserByEmail(user.getEmail()) == null) {
			status = UserStatus.EMAIL_EXISTS;
		} else if(!userDao.saveUser(user)) {
			status = UserStatus.UNSUCCESSFUL;
		}
		return status;
	}*/
	
	public Response saveUser(User user) {
		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();
		response.setCode("rsgisterUser001");
		String status = MathPlayPropertiesFileReaderUtil
				.getPropertyValue("rsgisterUser001");
		if (userDao.getUserByUserId(user.getUserID()) == null) {
			response.setCode("rsgisterUser003");
			status = MathPlayPropertiesFileReaderUtil
					.getPropertyValue("rsgisterUser003");
		} else if (userDao.getUserByEmail(user.getEmail()) == null) {
			response.setCode("rsgisterUser004");
			status = MathPlayPropertiesFileReaderUtil
					.getPropertyValue("rsgisterUser004");
		} else if (!userDao.saveUser(user)) {
			response.setCode("rsgisterUser002");
			status = MathPlayPropertiesFileReaderUtil
					.getPropertyValue("rsgisterUser002");
		}

		response.setMessage(status);
		return Response.status(200).entity(response).build();
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
