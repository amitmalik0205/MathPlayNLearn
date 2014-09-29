package com.qait.mathplaynlearn.service;

import com.qait.mathplaynlearn.domain.GameDetails;

public interface GameDetailsService {

	public boolean saveGameDetails(GameDetails details);
	
	public GameDetails getGameDetailsByUserAndGame(Long userID, Long gameId);
}
