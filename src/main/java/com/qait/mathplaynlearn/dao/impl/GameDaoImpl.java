package com.qait.mathplaynlearn.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.qait.mathplaynlearn.dao.GameDao;
import com.qait.mathplaynlearn.domain.Game;
import com.qait.mathplaynlearn.util.MathPlayNLearnUtil;

@Repository("gameDao")
public class GameDaoImpl extends GenericDaoImpl<Game, Long> implements GameDao {
	
	private static final Logger logger = Logger.getLogger(GameDaoImpl.class);

	@Override
	public boolean saveGame(Game game) {
		boolean isSaved = true;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(game);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			isSaved = false;
			e.printStackTrace();
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.flush();
			session.close();
		}
		return isSaved;
	}

	@Override
	public Game getGameByNameAndClass(String gameName, String gameClass) {
		Session session = null;
		Game game = null;
		try {
			session = getSessionFactory().openSession();
			String queryStr = "from Game where gameName=:gname and gameClass=:gclass";
			Query query = session.createQuery(queryStr);
			query.setParameter("gname", gameName);
			query.setParameter("gclass", gameClass);
			game = (Game)query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return game;
	}

}
