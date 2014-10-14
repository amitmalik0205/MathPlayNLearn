package com.qait.mathplaynlearn.service;

import java.util.List;

import javax.ws.rs.core.Response;

import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.dto.GroupMemberInfoDTO;

public interface UserService {

	public Response saveUser(User user);
	
	public User authenticateUser(String userId, String password);
	
	public List<GroupMemberInfoDTO> getMatchingUserID(String str);
	
	public User getUserByUserId(String userId);
	
	public User getUserWithSecurityQuestion(String userId);
	
	public User getUser(long id);
	
	public List<GroupMemberInfoDTO> getMatchingUserIDForGroup(String str, long groupID);
}
