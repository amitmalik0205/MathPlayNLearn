package com.qait.mathplaynlearn.dao;

import java.util.List;

import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.dto.GroupMemberInfoDTO;

public interface UserDao extends GenericDao<User, Long> {

	public boolean saveUser(User user);
	
	public User getUserByEmail(String email);
	
	public User getUserByUserId(String userId);
	
	public User authenticateUser(String userId, String password);
	
	public List<GroupMemberInfoDTO> getMatchingUserID(String str);
	
	public User getUserWithSecurityQuestion(String userId);
	
	public User getUser(long id);
	
	public List<GroupMemberInfoDTO> getMatchingUserIDForGroup(String str, long groupID);
}
