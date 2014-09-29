package com.qait.mathplaynlearn.dto;

import java.io.Serializable;

public class GroupMemberDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long userID;
	
	private long groupID;

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}
}
