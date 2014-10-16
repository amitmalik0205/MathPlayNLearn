package com.qait.mathplaynlearn.dto;

import java.io.Serializable;

public class GetInvitationsDTO implements Serializable {

	private static final long serialVersionUID = -2608060206184340410L;

	private String groupName;

	private long groupID;

	private String groupOwner;

	private long memberID;

	public GetInvitationsDTO(String groupName, long groupID, String groupOwner,
			long memberID) {
		this.groupName = groupName;
		this.groupID = groupID;
		this.groupOwner = groupOwner;
		this.memberID = memberID;
	}
	
	public GetInvitationsDTO(String groupName, long groupID) {
		this.groupName = groupName;
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	public String getGroupOwner() {
		return groupOwner;
	}

	public void setGroupOwner(String groupOwner) {
		this.groupOwner = groupOwner;
	}

	public long getMemberID() {
		return memberID;
	}

	public void setMemberID(long memberID) {
		this.memberID = memberID;
	}
}
