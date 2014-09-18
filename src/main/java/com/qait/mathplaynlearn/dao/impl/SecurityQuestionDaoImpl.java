package com.qait.mathplaynlearn.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.qait.mathplaynlearn.dao.SecurityQuestionDao;
import com.qait.mathplaynlearn.domain.SecurityQuestion;
import com.qait.mathplaynlearn.util.MathPlayNLearnUtil;

@Repository("securityQuestionDao")
public class SecurityQuestionDaoImpl extends GenericDaoImpl<SecurityQuestion, Long> implements SecurityQuestionDao {
	
	private static final Logger logger = Logger.getLogger(SecurityQuestionDaoImpl.class);

	@Override
	public List<SecurityQuestion> getAllQuestions() {
		List<SecurityQuestion> list = new ArrayList<SecurityQuestion>();
		Session session =null;
		try {
			session = getSessionFactory().openSession();
			String queryString = "from SecurityQuestion";
			Query query = session.createQuery(queryString);
			list = query.list();
		} catch (Exception e) {
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		
		return list;
	}
}
