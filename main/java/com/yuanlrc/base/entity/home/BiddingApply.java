package com.yuanlrc.base.entity.home;

import com.yuanlrc.base.bean.EarnestMoneyStatus;
import com.yuanlrc.base.entity.admin.BaseEntity;
import com.yuanlrc.base.entity.common.BiddingProject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 竞拍报名表
 */
@Entity
@Table(name = "ylrc_bidding_apply")
@EntityListeners(AuditingEntityListener.class)
public class BiddingApply extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="home_user_id")
    private HomeUser homeUser;//用户

    @ManyToOne
    @JoinColumn(name="bidding_project_id")
    private BiddingProject biddingProject;//竞拍项目

    @Column(name = "earnest_money")
    private int earnestMoney;//保证金

    @Column(name = "status")
    private Integer status = EarnestMoneyStatus.NOT_RETURN.getCode();//保险金状态 默认未返还

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

    public int getEarnestMoney() {
        return earnestMoney;
    }

    public void setEarnestMoney(int earnestMoney) {
        this.earnestMoney = earnestMoney;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BiddingApply{" +
                "homeUser=" + homeUser +
                ", biddingProject=" + biddingProject +
                ", earnestMoney=" + earnestMoney +
                ", status=" + status +
                '}';
    }
}
