package com.qait.mathplaynlearn.dao;

import com.qait.mathplaynlearn.domain.Game;

public interface GameDao extends GenericDao<Game, Long> {

	public boolean saveGame(Game game);
	
	public Game getGameByNameAndClass(String gameName, String gameClass);
}
