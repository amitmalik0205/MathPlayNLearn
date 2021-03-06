package com.qait.mathplaynlearn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qait.mathplaynlearn.dao.GameDetailsDao;
import com.qait.mathplaynlearn.domain.GameDetails;
import com.qait.mathplaynlearn.service.GameDetailsService;

@Service("gameDetailsService")
public class GameDetailsServiceImpl implements GameDetailsService {

	@Autowired
	private GameDetailsDao gameDetailsDao;
	
	@Override
	public boolean saveGameDetails(GameDetails details) {
		return gameDetailsDao.saveGameDetails(details);
	}
	
	@Override
	public GameDetails getGameDetailsByUserAndGame(Long userID, Long gameId) {
		return gameDetailsDao.getGameDetailsByUserAndGame(userID, gameId);
	}
	
	@Override
	public List<Object[]> getScoreForGroup(long groupID, long gameID) {
		return gameDetailsDao.getScoreForGroup(groupID, gameID);
	}
	
	@Override
	public List<Object[]> getTotalScoreForUser(long groupID) {
		return gameDetailsDao.getTotalScoreForUser(groupID);
	}

	public GameDetailsDao getGameDetailsDao() {
		return gameDetailsDao;
	}

	public void setGameDetailsDao(GameDetailsDao gameDetailsDao) {
		this.gameDetailsDao = gameDetailsDao;
	}

}
