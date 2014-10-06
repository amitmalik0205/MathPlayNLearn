package com.qait.mathplaynlearn.rest.service;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qait.mathplaynlearn.domain.Group;
import com.qait.mathplaynlearn.domain.GroupMember;
import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.enums.MemberStatus;
import com.qait.mathplaynlearn.service.GroupMemberService;
import com.qait.mathplaynlearn.service.GroupService;
import com.qait.mathplaynlearn.service.UserService;
import com.qait.mathplaynlearn.util.MathPlayPropertiesFileReaderUtil;

@Component("mathPlayNLearnServiceAdapter")
public class MathPlayNLearnServiceAdapter {

	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupMemberService memberService;

	@Transactional
	public Response createGroup(String userID, String groupName) {

		MathPlayNLearnServiceResponse response = new MathPlayNLearnServiceResponse();

		User user = userService.getUserByUserId(userID);
		if (user == null) {

			response.setCode("createGroup004");
			response.setMessage(MathPlayPropertiesFileReaderUtil
					.getPropertyValue("createGroup004"));

		} else {

			Group savedGroup = groupService.getGroupByGroupName(groupName);
			if (savedGroup != null) {

				response.setCode("createGroup003");
				response.setMessage(MathPlayPropertiesFileReaderUtil
						.getPropertyValue("createGroup003"));

			} else {

				Group newGroup = new Group();
				newGroup.setGroupName(groupName);
				newGroup.setGroupOwner(user);
				boolean isGroupSaved = groupService.saveGroup(newGroup);

				if (isGroupSaved) {
					
					//Add owner as group member
					Group createdGroup = groupService.getGroupByGroupName(groupName);
					GroupMember groupMember = new GroupMember(user, createdGroup,MemberStatus.ACCEPTED);
					memberService.saveMember(groupMember);

					response.setCode("createGroup001");
					response.setMessage(MathPlayPropertiesFileReaderUtil
							.getPropertyValue("createGroup001"));

				} else {

					response.setCode("createGroup002");
					response.setMessage(MathPlayPropertiesFileReaderUtil
							.getPropertyValue("createGroup002"));
				}
			}
		}

		return Response.status(200).entity(response).build();
	} 
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public GroupMemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(GroupMemberService memberService) {
		this.memberService = memberService;
	}
}
