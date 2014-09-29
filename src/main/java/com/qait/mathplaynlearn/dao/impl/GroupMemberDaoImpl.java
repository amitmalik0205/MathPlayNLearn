package com.qait.mathplaynlearn.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.qait.mathplaynlearn.dao.GroupMemberDao;
import com.qait.mathplaynlearn.domain.GroupMember;
import com.qait.mathplaynlearn.util.MathPlayNLearnUtil;

@Repository("groupMemberDao")
public class GroupMemberDaoImpl extends GenericDaoImpl<GroupMember, Long> implements GroupMemberDao {

	private static final Logger logger = Logger.getLogger(GroupMemberDaoImpl.class);

	@Override
	public boolean saveMember(GroupMember member) {
		boolean isSaved = true;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(member);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			isSaved = false;
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.flush();
			session.close();
		}
		return isSaved;
	}
}
