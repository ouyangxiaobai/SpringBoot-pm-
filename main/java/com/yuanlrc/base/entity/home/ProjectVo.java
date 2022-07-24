package com.yuanlrc.base.entity.home;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;

@EntityListeners(AuditingEntityListener.class)
public class ProjectVo
{
    private String projectNumber;

    private String title;

    private Integer projectStatus; //物品状态

    private String province = ""; //省份

    private String city = ""; //市

    private String area = ""; //区

    private Long labelType; //标签

    private String startTime;//报名开始时间

    private String endTime;//报名开始时间

    private String biddingStartTime;//竞拍开始时间

    private String biddingEndTime;//竞拍结束时间

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getLabelType() {
        return labelType;
    }

    public void setLabelType(Long labelType) {
        this.labelType = labelType;
    }

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

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
