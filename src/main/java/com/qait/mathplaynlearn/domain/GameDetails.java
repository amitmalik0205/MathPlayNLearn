package com.qait.mathplaynlearn.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "game_details")
public class GameDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "game_Detail_id")
	private Long gameDetailId;

	@Column(name = "level", nullable = false)
	private String level;
	
	@Column(name = "user_score", nullable = false, columnDefinition = "int default 0")
	private long userScore;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "game_id")
	private Game game;

	public Long getGameDetailId() {
		return gameDetailId;
	}

	public void setGameDetailId(Long gameDetailId) {
		this.gameDetailId = gameDetailId;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
