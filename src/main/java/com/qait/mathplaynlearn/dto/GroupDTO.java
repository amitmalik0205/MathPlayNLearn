package com.qait.mathplaynlearn.dto;

import java.io.Serializable;


public class GroupDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long groupID;

	private String groupName;

	public Long getGroupID() {
		return groupID;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
