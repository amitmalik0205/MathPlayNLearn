package com.qait.mathplaynlearn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qait.mathplaynlearn.dao.GroupMemberDao;
import com.qait.mathplaynlearn.domain.GroupMember;
import com.qait.mathplaynlearn.rest.service.GroupMemberService;

@Service("groupMemberService")
public class GroupMemberServiceImpl implements GroupMemberService {

	@Autowired
	private GroupMemberDao groupMemberDao;
	
	@Override
	public boolean saveMember(GroupMember member) { 
		return groupMemberDao.saveMember(member);
	}

	public GroupMemberDao getGroupMemberDao() {
		return groupMemberDao;
	}

	public void setGroupMemberDao(GroupMemberDao groupMemberDao) {
		this.groupMemberDao = groupMemberDao;
	}
}
