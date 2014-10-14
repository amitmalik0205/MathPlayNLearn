package com.qait.mathplaynlearn.enums;

public enum MemberStatus {

	WAITING("Waiting for approval"), 
	ACCEPTED("Request Accepted"), 
	DECLINED("Request Declined"),
	ADD("Add to group");

	String value;

	private MemberStatus(String val) {
		this.value = val;
	}
}
