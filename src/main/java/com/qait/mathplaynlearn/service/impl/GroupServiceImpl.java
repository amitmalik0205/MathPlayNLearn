package com.qait.mathplaynlearn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qait.mathplaynlearn.dao.GroupDao;
import com.qait.mathplaynlearn.domain.Group;
import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.dto.GroupDTO;
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

	@Override
	public List<Group> getGroupListForOwner(String ownerID) {
		return groupDao.getGroupListForOwner(ownerID);
	}

	@Override
	public Group getGroupByGroupId(long groupID) {
		return groupDao.getGroupByGroupId(groupID);
	}
	
	@Override
	public boolean delete(Group group) {
		return groupDao.delete(group);
	}
	
	@Override
	public User getGroupOwner(Long groupID) {
		return groupDao.getGroupOwner(groupID);
	}

	@Override
	public List<GroupDTO> getGroupListForMember(String memberID) {
		return groupDao.getGroupListForMember(memberID);
	}
}
