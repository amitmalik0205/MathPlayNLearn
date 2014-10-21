package com.qait.mathplaynlearn.service;

import java.util.List;

import com.qait.mathplaynlearn.domain.Group;
import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.dto.GroupDTO;

public interface GroupService {

	public boolean saveGroup(Group group);
	
	public Group getGroupByGroupName(String groupName);
	
	public List<Group> getGroupListForOwner(String ownerID);
	
	public Group getGroupByGroupId(long groupID);
	
	public boolean delete(Group group);
	
	public User getGroupOwner(Long groupID);
	
	public List<GroupDTO> getGroupListForMember(String memberID);
}
