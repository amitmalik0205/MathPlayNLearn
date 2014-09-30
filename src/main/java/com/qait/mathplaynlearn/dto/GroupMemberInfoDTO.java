package com.qait.mathplaynlearn.dto;

import java.io.Serializable;

import com.qait.mathplaynlearn.enums.MemberStatus;

public class GroupMemberInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long userKey;
	
	private String userID;
	
	private MemberStatus status;

	public long getUserKey() {
		return userKey;
	}

	public void setUserKey(long userKey) {
		this.userKey = userKey;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public MemberStatus getStatus() {
		return status;
	}

	public void setStatus(MemberStatus status) {
		this.status = status;
	}
} 
