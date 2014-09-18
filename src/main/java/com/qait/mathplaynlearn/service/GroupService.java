package com.qait.mathplaynlearn.service;

import com.qait.mathplaynlearn.domain.Group;

public interface GroupService {

	public boolean saveGroup(Group group);
	
	public Group getGroupByGroupName(String groupName);
}
