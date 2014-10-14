package com.qait.mathplaynlearn.dto;

import java.io.Serializable;

import com.qait.mathplaynlearn.enums.MemberStatus;

public class GroupMemberInfoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long userKey;
	
	private String userID;
	
	private MemberStatus status;

	public GroupMemberInfoDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public GroupMemberInfoDTO(long userKey, String userID) {
		this.userKey = userKey;
		this.userID = userID;
		this.status = MemberStatus.ADD;
	}
	
	public GroupMemberInfoDTO(long userKey, String userID, MemberStatus status) {
		this.userKey = userKey;
		this.userID = userID;
		this.status = status;
	}
	
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
