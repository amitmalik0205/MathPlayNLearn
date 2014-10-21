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
import com.qait.mathplaynlearn.domain.User;
import com.qait.mathplaynlearn.dto.GroupDTO;
import com.qait.mathplaynlearn.enums.MemberStatus;
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
			session.flush();
			session.close();
		}
		return groupSaved;
	}
	
/*	@Override
	public boolean saveGroup(Group group) {
		boolean groupSaved = true;
		Session session = null;
		session = getSessionFactory().openSession();
		session.save(group);
		int i = 1/0;
		//session.flush();
		//session.close();
		return groupSaved;
	}*/
	
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
			group = (Group) session.get(Group.class, new Long(groupID));
		} catch (Exception e) {
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return group;
	}
	
	@Override
	public boolean delete(Group group) {
		boolean groupDeleted = true;
		Session session = null;
		Transaction transaction = null;
		try {
			session = getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.delete(group);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			groupDeleted = false;
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.flush();
			session.close();
		}
		return groupDeleted;
	}
	
	@Override
	public User getGroupOwner(Long groupID) {
		Session session = null;
		User user = null;
		try {
			session = getSessionFactory().openSession();
			String queryString = "from User u join fetch u.groups g where g.groupID = :gid";
			Query query = session.createQuery(queryString);
			query.setParameter("gid", groupID);
			user = (User)query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return user;
	}
	
	@Override
	public List<GroupDTO> getGroupListForMember(String memberID) {
		Session session = null;
		List<GroupDTO> list = new ArrayList<GroupDTO>();
		try {
			session = getSessionFactory().openSession();
			String queryString = "Select new com.qait.mathplaynlearn.dto.GroupDTO(g.groupID, g.groupName) from GroupMember gm "
					+ " join gm.group g join gm.member m join g.groupOwner u where u.userID <> :memberID "
					+ " and m.userID = :memberID and gm.status <> :status";
			Query query = session.createQuery(queryString);
			query.setString("memberID", memberID);
			query.setParameter("status", MemberStatus.WAITING);
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
