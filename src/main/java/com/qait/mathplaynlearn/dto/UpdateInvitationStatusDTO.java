package com.qait.mathplaynlearn.dto;

import java.io.Serializable;

import com.qait.mathplaynlearn.enums.MemberStatus;

public class UpdateInvitationStatusDTO implements Serializable {

	private static final long serialVersionUID = -7464088891808083108L;
	
	private long memberID;
	
	private long groupID;
	
	private MemberStatus status;

	public long getMemberID() {
		return memberID;
	}

	public void setMemberID(long memberID) {
		this.memberID = memberID;
	}

	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	public MemberStatus getStatus() {
		return status;
	}

	public void setStatus(MemberStatus status) {
		this.status = status;
	} 	
}
