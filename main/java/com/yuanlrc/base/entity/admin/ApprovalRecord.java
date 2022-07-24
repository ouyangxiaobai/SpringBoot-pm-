package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.bean.ProjectStatus;
import com.yuanlrc.base.entity.common.BiddingProject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 审批记录实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="ylrc_approval_record")
@EntityListeners(AuditingEntityListener.class)
public class ApprovalRecord extends BaseEntity{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="bidding_project_id")
	private BiddingProject biddingProject;//项目

	@Column(name = "reason")
	private String reason; //审批理由

	@Enumerated
	@Column(name = "project_status",nullable = false, length = 2)
	private ProjectStatus projectStatus;//审批状态

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;//审批人

	public BiddingProject getBiddingProject() {
		return biddingProject;
	}

	public void setBiddingProject(BiddingProject biddingProject) {
		this.biddingProject = biddingProject;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public ProjectStatus getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(ProjectStatus projectStatus) {
		this.projectStatus = projectStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "ApprovalRecord{" +
				"biddingProject=" + biddingProject +
				", reason='" + reason + '\'' +
				", projectStatus=" + projectStatus +
				'}';
	}
}
