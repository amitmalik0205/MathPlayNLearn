package com.qait.mathplaynlearn.dao;

import java.util.List;

import com.qait.mathplaynlearn.domain.GroupMember;

public interface GroupMemberDao extends GenericDao<GroupMember, Long> {

	public boolean saveMember(GroupMember member);
	
	public List<Object[]> getMembersInfoByGroup(long groupID);
	
	public GroupMember getGroupMemberByID(long groupID, long memberID);
	
	public boolean deleteGroupMember(long groupID, long memberID);
}
