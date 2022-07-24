package com.yuanlrc.base.entity.home;

import com.yuanlrc.base.bean.RemindType;
import com.yuanlrc.base.entity.admin.BaseEntity;
import com.yuanlrc.base.entity.common.BiddingProject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 竞拍提醒
 */
@Entity
@Table(name = "ylrc_bidding_remind")
@EntityListeners(AuditingEntityListener.class)
public class BiddingRemind extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="home_user_id")
    private HomeUser homeUser;//用户

    @ManyToOne
    @JoinColumn(name="bidding_project_id")
    private BiddingProject biddingProject;//竞拍项目

    @Column(name = "status")
    private Integer status = RemindType.NOREMIND.getCode();//状态 默认未提醒

    public HomeUser getHomeUser() {
        return homeUser;
    }

    public void setHomeUser(HomeUser homeUser) {
        this.homeUser = homeUser;
    }

    public BiddingProject getBiddingProject() {
        return biddingProject;
    }

    public void setBiddingProject(BiddingProject biddingProject) {
        this.biddingProject = biddingProject;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BiddingRemind{" +
                "homeUser=" + homeUser +
                ", biddingProject=" + biddingProject +
                ", status=" + status +
                '}';
    }
}
