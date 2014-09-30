package com.qait.mathplaynlearn.dto;

import java.io.Serializable;

public class GroupMemberDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long userKey;
	
	private long userID;
	
	private long groupID;

	public long getUserKey() {
		return userKey;
	}

	public void setUserKey(long userKey) {
		this.userKey = userKey;
	}

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
