package com.qait.mathplaynlearn.service;

import java.util.List;

import com.qait.mathplaynlearn.domain.GroupMember;
import com.qait.mathplaynlearn.dto.GetInvitationsDTO;
import com.qait.mathplaynlearn.dto.GroupMemberInfoDTO;

public interface GroupMemberService {

	public boolean saveMember(GroupMember member);
	
	public List<GroupMemberInfoDTO> getMembersInfoByGroup(long groupID);
	
	public GroupMember getGroupMemberByID(long groupID, long memberID);
	
	public boolean deleteGroupMember(long groupID, long memberID);
	
	public List<GetInvitationsDTO> getGroupInvitationsForUser(String userID);
	
	public Long getInvitationCount(String userID);
}
