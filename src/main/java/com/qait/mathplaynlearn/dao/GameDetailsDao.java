package com.qait.mathplaynlearn.dao;

import com.qait.mathplaynlearn.domain.GameDetails;

public interface GameDetailsDao extends GenericDao<GameDetails, Long> {

	public boolean saveGameDetails(GameDetails details);
	
	public GameDetails getGameDetailsByUserAndGame(Long userID, Long gameId);
}
