package com.qait.mathplaynlearn.service;

import com.qait.mathplaynlearn.domain.Game;
import com.qait.mathplaynlearn.domain.GameDetails;

public interface GameService {

    public boolean saveGame(Game game);
	
	public Game getGameByNameAndClass(String gameName, String gameClass);
}
