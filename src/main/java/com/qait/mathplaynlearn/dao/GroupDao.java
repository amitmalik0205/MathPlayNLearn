package com.qait.mathplaynlearn.dao;

import com.qait.mathplaynlearn.domain.Group;

public interface GroupDao extends GenericDao<Group, Long> {

	public boolean saveGroup(Group group);
	
	public Group getGroupByGroupName(String groupName);
}
