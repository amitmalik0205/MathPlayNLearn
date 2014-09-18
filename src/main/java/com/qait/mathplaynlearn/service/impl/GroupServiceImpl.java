package com.qait.mathplaynlearn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qait.mathplaynlearn.dao.GroupDao;
import com.qait.mathplaynlearn.domain.Group;
import com.qait.mathplaynlearn.service.GroupService;

@Service("groupService")
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;
	
	@Override
	public boolean saveGroup(Group group) {
		return groupDao.saveGroup(group);
	}
	
	@Override
	public Group getGroupByGroupName(String groupName) {
		return groupDao.getGroupByGroupName(groupName);
	}
}
