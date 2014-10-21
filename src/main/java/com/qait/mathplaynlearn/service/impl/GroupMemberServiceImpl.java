package com.qait.mathplaynlearn.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qait.mathplaynlearn.dao.GroupMemberDao;
import com.qait.mathplaynlearn.domain.GroupMember;
import com.qait.mathplaynlearn.dto.GetInvitationsDTO;
import com.qait.mathplaynlearn.dto.GroupMemberInfoDTO;
import com.qait.mathplaynlearn.enums.MemberStatus;
import com.qait.mathplaynlearn.service.GroupMemberService;

@Service("groupMemberService")
public class GroupMemberServiceImpl implements GroupMemberService {

	@Autowired
	private GroupMemberDao groupMemberDao;
	
	@Override
	public boolean saveMember(GroupMember member) { 
		return groupMemberDao.saveMember(member);
	}
	
	@Override
	public List<GroupMemberInfoDTO> getMembersInfoByGroup(long groupID) {
		 List<GroupMemberInfoDTO> membersInfoList = new ArrayList<GroupMemberInfoDTO>();
		 List<Object []> list = groupMemberDao.getMembersInfoByGroup(groupID);
		 
		 for(Object[] objArr: list) {
			 GroupMemberInfoDTO dto = new GroupMemberInfoDTO();
			 dto.setUserKey((Long)objArr[0]);
			 dto.setUserID((String)objArr[1]);
			 dto.setStatus((MemberStatus)objArr[2]);
			 membersInfoList.add(dto);
		 }
		 return membersInfoList;
	}

	@Override
	public GroupMember getGroupMemberByID(long groupID, long memberID) {
		return groupMemberDao.getGroupMemberByID(groupID, memberID);
	}
	
	@Override
	public boolean deleteGroupMember(long groupID, long memberID) {
		return groupMemberDao.deleteGroupMember(groupID, memberID);
	}
	
	@Override
	public List<GetInvitationsDTO> getGroupInvitationsForUser(String userID) {
		return groupMemberDao.getGroupInvitationsForUser(userID);
	}
	
	@Override
	public Long getInvitationCount(String userID) {
		return groupMemberDao.getInvitationCount(userID);
	}
	
	public GroupMemberDao getGroupMemberDao() {
		return groupMemberDao;
	}

	public void setGroupMemberDao(GroupMemberDao groupMemberDao) {
		this.groupMemberDao = groupMemberDao;
	}
}
