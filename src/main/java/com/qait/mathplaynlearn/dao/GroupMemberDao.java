package com.qait.mathplaynlearn.dao;

import com.qait.mathplaynlearn.domain.GroupMember;

public interface GroupMemberDao extends GenericDao<GroupMember, Long> {

	public boolean saveMember(GroupMember member);
}
