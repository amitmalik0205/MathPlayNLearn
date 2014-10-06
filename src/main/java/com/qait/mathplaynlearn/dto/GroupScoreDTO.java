package com.qait.mathplaynlearn.dto;

import java.io.Serializable;

public class GroupScoreDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long groupID;
	
	private String gameName;
	
	private String gameClass;

	public Long getGroupID() {
		return groupID;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameClass() {
		return gameClass;
	}

	public void setGameClass(String gameClass) {
		this.gameClass = gameClass;
	}
	
	
}
