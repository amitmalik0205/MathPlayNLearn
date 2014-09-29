package com.qait.mathplaynlearn.enums;

public enum MemberStatus {

	WAITING("Waiting for approval"), 
	ACCEPTED("Request accepted"), 
	DECLINED("Request declined");

	String value;

	private MemberStatus(String val) {
		this.value = val;
	}


}
