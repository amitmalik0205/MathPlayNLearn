package com.qait.mathplaynlearn.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.qait.mathplaynlearn.enums.MemberStatus;

@Entity
@Table(name = "group_member")
public class GroupMember implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "group_member_id")
	private Long groupMemberID;
	
	@ManyToOne
	@JoinColumn(name = "member_id")
	private User member;
	
	@ManyToOne
	@JoinColumn(name = "group_id")
	private Group group;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "member_status", nullable=false)
	private MemberStatus status;

	public Long getGroupMemberID() {
		return groupMemberID;
	}

	public void setGroupMemberID(Long groupMemberID) {
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
