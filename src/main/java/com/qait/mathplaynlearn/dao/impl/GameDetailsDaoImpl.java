package com.qait.mathplaynlearn.dao.impl;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public List<Object[]> getScoreForGroup(long groupID, long gameID) {
		List<Object[]> list = new ArrayList<Object[]>();
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			/*String queryStr = "Select u.userId, gd.user_score, gm.member_status from group_member gm "
					+ "join game_details gd on gd.user_id = gm.member_id join user u on u.id = gd.user_id "
					+ "where gd.game_id = :gameid and gm.group_id = :gid";
			Query query = session.createSQLQuery(queryStr);*/
			String queryStr = "Select u.userID, gd.userScore, gm.status from GroupMember gm join gm.group g "+
							"join gm.member u join u.gameDetails gd where g.groupID = :gid and gd.game.gameId = :gameid";
			Query query = session.createQuery(queryStr);
			query.setParameter("gid", groupID);
			query.setParameter("gameid", gameID);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return list;
	}
}
