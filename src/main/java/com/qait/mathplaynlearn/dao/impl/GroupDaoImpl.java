package com.qait.mathplaynlearn.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.qait.mathplaynlearn.dao.GroupDao;
import com.qait.mathplaynlearn.domain.Group;
import com.qait.mathplaynlearn.util.MathPlayNLearnUtil;

@Repository("groupDao")
public class GroupDaoImpl extends GenericDaoImpl<Group, Long> implements GroupDao {
	
	private static final Logger logger = Logger.getLogger(GroupDaoImpl.class);

	@Override
	public boolean saveGroup(Group group) {
		boolean groupSaved = true;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.save(group);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			groupSaved = false;
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return groupSaved;
	}
	
	@Override
	public Group getGroupByGroupName(String groupName) {
		Session session = null;
		Group group = null;
		try {
			session = getSessionFactory().openSession();
			String queryString = "from Group where groupName = :gName";
			Query query = session.createQuery(queryString);
			query.setString("gName", groupName);
			group = (Group) query.uniqueResult();
		} catch (Exception e) {
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return group;
	}
	
	@Override
	public List<Group> getGroupListForOwner(String ownerID) {
		Session session = null;
		List<Group> list = new ArrayList<Group>();
		try {
			session = getSessionFactory().openSession();
			String queryString = "from Group g where g.groupOwner.userID = :owner";
			Query query = session.createQuery(queryString);
			query.setString("owner", ownerID);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return list;
	}
	
	@Override
	public Group getGroupByGroupId(long groupID) {
		Session session = null;
		Group group = null;
		try {
			session = getSessionFactory().openSession();
			group = (Group) session.load(Group.class, new Long(groupID));
		} catch (Exception e) {
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return group;
	}
}
