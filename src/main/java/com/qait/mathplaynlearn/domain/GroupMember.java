package com.qait.mathplaynlearn.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.qait.mathplaynlearn.enums.MemberStatus;

@Entity
@Table(name = "group_member")
public class GroupMember implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Embeddable
	public static class GroupMemberID implements Serializable {

		private static final long serialVersionUID = 1L;

		@Column(name = "member_id")
		private Long memberID;

		@Column(name = "group_id")
		private Long groupID;

		public GroupMemberID() { 
			
		}
		
		public GroupMemberID(Long memberID, Long groupID) {
			this.memberID = memberID;
			this.groupID = groupID;
		}

		@Override
		public boolean equals(Object o) {

			if (o != null && o instanceof GroupMemberID) {
				GroupMemberID that = (GroupMemberID) o;
				return this.memberID.equals(that.memberID)
						&& this.groupID.equals(that.groupID);
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return memberID.hashCode() + groupID.hashCode();
		}

		public Long getMemberID() {
			return memberID;
		}

		public void setMemberID(Long memberID) {
			this.memberID = memberID;
		}

		public Long getGroupID() {
			return groupID;
		}

		public void setGroupID(Long groupID) {
			this.groupID = groupID;
		}
	}

	@EmbeddedId
	private GroupMemberID groupMemberID = new GroupMemberID();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", insertable = false, updatable = false)
	private User member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", insertable = false, updatable = false)
	private Group group;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "member_status", nullable=false)
	private MemberStatus status;

	public GroupMember() {
		// TODO Auto-generated constructor stub
	}
	
	public GroupMember(User user, Group group, MemberStatus status) {
		this.member = user;
		this.group = group;
		this.status = status;
		this.groupMemberID.groupID = group.getGroupID();
		this.groupMemberID.memberID = user.getId();
	}
	
	public GroupMemberID getGroupMemberID() {
		return groupMemberID;
	}

	public void setGroupMemberID(GroupMemberID groupMemberID) {
		this.groupMemberID = groupMemberID;
	}

	public User getMember() {
		return member;
	}

	public void setMember(User member) {
		this.member = member;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public MemberStatus getStatus() {
		return status;
	}

	public void setStatus(MemberStatus status) {
		this.status = status;
	}
}
