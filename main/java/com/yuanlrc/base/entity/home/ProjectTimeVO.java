package com.yuanlrc.base.entity.home;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
public class ProjectTimeVO {

    private String startTime;//报名开始时间
    private String endTime;//报名开始时间
    private String biddingStartTime;//竞拍开始时间
    private String biddingEndTime;//竞拍结束时间
    private Integer status = -1;//竞拍物品状态

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBiddingStartTime() {
        return biddingStartTime;
    }

    public void setBiddingStartTime(String biddingStartTime) {
        this.biddingStartTime = biddingStartTime;
    }

    public String getBiddingEndTime() {
        return biddingEndTime;
    }

    public void setBiddingEndTime(String biddingEndTime) {
        this.biddingEndTime = biddingEndTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
