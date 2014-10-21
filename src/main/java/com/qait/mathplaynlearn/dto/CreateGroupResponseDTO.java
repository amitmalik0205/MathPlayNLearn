package com.qait.mathplaynlearn.dto;

import java.io.Serializable;

public class CreateGroupResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String code;
	private String message;
	private long groupID;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}
}
