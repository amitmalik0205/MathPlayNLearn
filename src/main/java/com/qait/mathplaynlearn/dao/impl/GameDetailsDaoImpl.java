package com.qait.mathplaynlearn.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.qait.mathplaynlearn.dao.GameDetailsDao;
import com.qait.mathplaynlearn.domain.GameDetails;
import com.qait.mathplaynlearn.util.MathPlayNLearnUtil;

@Repository("gameDetailsDao")
public class GameDetailsDaoImpl extends GenericDaoImpl<GameDetails, Long> implements GameDetailsDao {

	private static final Logger logger = Logger.getLogger(GenericDaoImpl.class);
	
	@Override
	public boolean saveGameDetails(GameDetails details) {
		boolean isSaved = true;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(details);
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
	public GameDetails getGameDetailsByUserAndGame(Long userID, Long gameId) {
		Session session = null;
		GameDetails details = null;
		try {
			session = getSessionFactory().openSession();
			String queryString = "from GameDetails g where g.user.id=:uid and g.game.gameId=:gameid";
			Query query = session.createQuery(queryString);
			query.setParameter("uid", userID);
			query.setParameter("gameid", gameId);
			details = (GameDetails)query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return details;
	}
}
