package com.qait.mathplaynlearn.rest.dto;

public class GameDetailsDTO {

	private String level;
	
	private long userScore;
	
	private String userID;
	
	private String gameName;
	
	private String gameClass;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public long getUserScore() {
		return userScore;
	}

	public void setUserScore(long userScore) {
		this.userScore = userScore;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
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
