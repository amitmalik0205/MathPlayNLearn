package com.qait.mathplaynlearn.dao;

import java.util.List;

import com.qait.mathplaynlearn.domain.GameDetails;

public interface GameDetailsDao extends GenericDao<GameDetails, Long> {

	public boolean saveGameDetails(GameDetails details);
	
	public GameDetails getGameDetailsByUserAndGame(Long userID, Long gameId);
	
	public List<Object[]> getScoreForGroup(long groupID, long gameID);
	
	public List<Object[]> getTotalScoreForUser(long groupID);
}
