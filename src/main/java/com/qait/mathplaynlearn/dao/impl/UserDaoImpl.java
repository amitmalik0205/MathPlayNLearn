package com.qait.mathplaynlearn.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.qait.mathplaynlearn.dao.UserDao;
import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.util.MathPlayNLearnUtil;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public boolean saveUser(User user) {
		boolean userSaved = true;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(user);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			userSaved = false;
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.flush();
			session.close();
		}
		return userSaved;
	}
	
	@Override
	public User getUserByEmail(String email) {
		Session session = null;
		User user = null;
		try {
			session = getSessionFactory().openSession();
			String queryString = "from User where email = :email";
			Query query = session.createQuery(queryString);
			query.setString("email", email);
			user = (User) query.uniqueResult();
		} catch (Exception e) {
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return user;
	}
	
	@Override
	public User getUserByUserId(String id) {
		Session session = null;
		User user = null;
		try {
			session = getSessionFactory().openSession();
			String queryString = "from User where userID = :id";
			Query query = session.createQuery(queryString);
			query.setString("id", id);
			user = (User) query.uniqueResult();
		} catch (Exception e) {
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return user;
	}
}
