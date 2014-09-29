package com.qait.mathplaynlearn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qait.mathplaynlearn.dao.GameDao;
import com.qait.mathplaynlearn.domain.Game;
import com.qait.mathplaynlearn.service.GameService;

@Service("gameService")
public class GameServiceImpl implements GameService {
	
	@Autowired
	private GameDao gameDao;

	@Override
	public boolean saveGame(Game game) {
		return gameDao.saveGame(game);
	}

	@Override
	public Game getGameByNameAndClass(String gameName, String gameClass) {
		return gameDao.getGameByNameAndClass(gameName, gameClass);
	}

	public GameDao getGameDao() {
		return gameDao;
	}

	public void setGameDao(GameDao gameDao) {
		this.gameDao = gameDao;
	}
}
