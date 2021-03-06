package com.qait.mathplaynlearn.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.qait.mathplaynlearn.dao.GroupMemberDao;
import com.qait.mathplaynlearn.domain.GroupMember;
import com.qait.mathplaynlearn.dto.GetInvitationsDTO;
import com.qait.mathplaynlearn.enums.MemberStatus;
import com.qait.mathplaynlearn.util.MathPlayNLearnUtil;

@Repository("groupMemberDao")
public class GroupMemberDaoImpl extends GenericDaoImpl<GroupMember, Long>
		implements GroupMemberDao {

	private static final Logger logger = Logger
			.getLogger(GroupMemberDaoImpl.class);

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
			e.printStackTrace();
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.flush();
			session.close();
		}
		return isSaved;
	}

	/*
	 * @Override public boolean saveMember(GroupMember member) { boolean isSaved
	 * = true; Session session = null; session =
	 * getSessionFactory().openSession(); session.saveOrUpdate(member); int i =
	 * 1/0; //session.flush(); //session.close(); return isSaved; }
	 */

	@Override
	public List<Object[]> getMembersInfoByGroup(long groupID) {
		List<Object[]> list = new ArrayList<Object[]>();
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			String queryStr = "Select u.id, u.userID, gm.status from GroupMember gm join gm.group g join gm.member u where g.groupID = :gid";
			Query query = session.createQuery(queryStr);
			query.setParameter("gid", groupID);
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
	public GroupMember getGroupMemberByID(long groupID, long memberID) {
		Session session = null;
		GroupMember groupMember = null;
		try {
			session = getSessionFactory().openSession();
			String queryStr = "from GroupMember gm where gm.groupMemberID.memberID=:mid and gm.groupMemberID.groupID=:gid";
			Query query = session.createQuery(queryStr);
			query.setParameter("mid", memberID);
			query.setParameter("gid", groupID);
			groupMember = (GroupMember) query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return groupMember;
	}

	@Override
	public boolean deleteGroupMember(long groupID, long memberID) {
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = true;
		try {
			session = getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String queryStr = "Delete from GroupMember gm where gm.groupMemberID.memberID=:mid and gm.groupMemberID.groupID=:gid";
			Query query = session.createQuery(queryStr);
			query.setParameter("mid", memberID);
			query.setParameter("gid", groupID);
			query.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			isDeleted = false;
			e.printStackTrace();
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return isDeleted;
	}

	@Override
	public List<GetInvitationsDTO> getGroupInvitationsForUser(String userID) {
		List<GetInvitationsDTO> list = null;
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			String queryStr = "Select new com.qait.mathplaynlearn.dto.GetInvitationsDTO(g.groupName, g.groupID, u.userID, m.id) "
					+ " from GroupMember gm join gm.group g join g.groupOwner u join gm.member m where m.userID = :uid and gm.status = :status";
			Query query = session.createQuery(queryStr);
			query.setString("uid", userID);
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

	@Override
	public Long getInvitationCount(String userID) {
		Long count = null;
		Session session = null;
		try {
			session = getSessionFactory().openSession();
			String queryStr = "Select count(*) from GroupMember gm join gm.group g join g.groupOwner u join gm.member m "
					+ " where m.userID = :uid and gm.status = :status";
			Query query = session.createQuery(queryStr);
			query.setString("uid", userID);
			query.setParameter("status", MemberStatus.WAITING);
			count = (Long)query.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			logger.fatal(MathPlayNLearnUtil.getExceptionDescriptionString(e));
		} finally {
			session.close();
		}
		return count;
	}
}
