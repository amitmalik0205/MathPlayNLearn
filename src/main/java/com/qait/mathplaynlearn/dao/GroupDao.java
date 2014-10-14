package com.qait.mathplaynlearn.dao;

import java.util.List;

import com.qait.mathplaynlearn.domain.Group;
import com.qait.mathplaynlearn.domain.User;

public interface GroupDao extends GenericDao<Group, Long> {

	public boolean saveGroup(Group group);
	
	public Group getGroupByGroupName(String groupName);
	
	public List<Group> getGroupListForOwner(String ownerID);
	
	public Group getGroupByGroupId(long groupID);
	
	public boolean delete(Group group);
	
	public User getGroupOwner(Long groupID);
}
