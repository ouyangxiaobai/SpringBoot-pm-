package com.yuanlrc.base.entity.home;

import com.yuanlrc.base.entity.admin.BaseEntity;
import com.yuanlrc.base.entity.common.BiddingProject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 竞价实体类
 */
@Entity
@Table(name = "ylrc_bidding_record")
@EntityListeners(AuditingEntityListener.class)
public class BiddingRecord extends BaseEntity {

    public static final int STATUS_YES = 1;//状态为是
    public static final int STATUS_NO = 0;//状态为否

    @ManyToOne
    @JoinColumn(name="home_user_id")
    private HomeUser homeUser;//用户

    @ManyToOne
    @JoinColumn(name="bidding_project_id")
    private BiddingProject biddingProject;//竞拍项目

    @Column(name = "bid")
    private int bid;//出价

    @Column(name = "bidding_status")
    private Integer biddingStatus;//竞拍状态

    @Column(name = "pay_status")
    private Integer payStatus = STATUS_NO;//支付状态 默认为否 未支付

    @Column(name = "overdue_status")
    private Integer overdueStatus = STATUS_NO;//逾期状态 默认为否 未逾期


    public HomeUser getHomeUser() {
        return homeUser;
    }

    public void setHomeUser(HomeUser homeUser) {
        this.homeUser = homeUser;
    }


    public Integer getBiddingStatus() {
        return biddingStatus;
    }

    public void setBiddingStatus(Integer biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Integer getOverdueStatus() {
        return overdueStatus;
    }

    public void setOverdueStatus(Integer overdueStatus) {
        this.overdueStatus = overdueStatus;
    }

    public BiddingProject getBiddingProject() {
        return biddingProject;
    }

    public void setBiddingProject(BiddingProject biddingProject) {
        this.biddingProject = biddingProject;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "BiddingRecord{" +
                "homeUser=" + homeUser +
                ", biddingProject=" + biddingProject +
                ", bid=" + bid +
                ", biddingStatus=" + biddingStatus +
                ", payStatus=" + payStatus +
                ", overdueStatus=" + overdueStatus +
                '}';
    }
}
