package com.yuanlrc.base.entity.common;


import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.CycleType;
import com.yuanlrc.base.bean.ProjectStatus;
import com.yuanlrc.base.bean.YearType;
import com.yuanlrc.base.entity.admin.BaseEntity;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.Organization;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "ylrc_bidding_project")
@EntityListeners(AuditingEntityListener.class)
public class BiddingProject extends BaseEntity {

    @Column(name = "project_number", nullable = false)
    private String projectNumber;//项目编号

    @ManyToOne
    @JoinColumn(name = "label_type_id")
    private LabelType labelType;//标的类型 √

    @ValidateEntity(required = true, errorRequiredMsg = "省不能为空")
    @Column(name = "province", nullable = false)
    private String province;//省 √

    @ValidateEntity(required = true, errorRequiredMsg = "市不能为空")
    @Column(name = "city", nullable = false)
    private String city;//市 √

    @ValidateEntity(required = true, errorRequiredMsg = "区不能为空")
    @Column(name = "area", nullable = false)
    private String area;//区 √

    @Enumerated
    @Column(name = "project_status", nullable = false, length = 2)
    private ProjectStatus projectStatus = ProjectStatus.NOTSUBMIT; //项目状态

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @ValidateEntity(required = true, errorRequiredMsg = "请选择报名开始时间")
    @Column(name = "start_time", nullable = false)
    private Date startTime;//报名开始时间 √

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @ValidateEntity(required = true, errorRequiredMsg = "请选择报名结束时间")
    @Column(name = "end_time")
    private Date endTime;//报名结束时间 √

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @ValidateEntity(required = true, errorRequiredMsg = "请选择竞拍开始时间")
    @Column(name = "biddind_start_time")
    private Date biddingStartTime;//竞拍开始时间√

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @ValidateEntity(required = true, errorRequiredMsg = "请选择竞拍结束时间")
    @Column(name = "bidding_end_time")
    private Date biddingEndTime;//竞拍结束时间√

    @ManyToOne
    @JoinColumn(name = "organization")
    private Organization organization;//发布企业

    @Column(name = "years_type", nullable = false)
    private int yearsType = YearType.NOTYEARS.getCode();//是否有年限 √

    @Column(name = "years", nullable = false)
    private int years=0;//年限数 √

    @ValidateEntity(required = true, errorRequiredMsg = "请填写项目标题", requiredLeng = true, minLength = 4, maxLength = 20, errorMinLengthMsg = "项目标题至少为4个字", errorMaxLengthMsg = "项目标题最多为20个字")
    @Column(name = "title", nullable = false)
    private String title;//标题 √

    @ValidateEntity(required = true, errorRequiredMsg = "请填写转出方",minLength = 2,maxLength = 10,requiredLeng = true,errorMinLengthMsg = "转出方至少为2个字",errorMaxLengthMsg = "转出方最多为10个字")
    @Column(name = "transferor", nullable = false)
    private String transferor;//转出方 √

    @Column(name = "start_price", nullable = false, length = 11)
    private int startPrice;//起拍价 √

    @Column(name = "rate_increase", nullable = false, length = 11)
    private int rateIncrease;//加价幅度 √

    @Column(name = "bond", nullable = false, length = 11)
    private int bond;//保证金 √

    @Column(name = "rate", nullable = false, length = 4)
    private BigDecimal rate;//佣金比例 √

    @ValidateEntity(required = true, errorRequiredMsg = "请上传图片")
    @Column(name = "picture", nullable = false, length = 512)
    private String picture;//图片 √

    @Lob
    @ValidateEntity(required = true, errorRequiredMsg = "详情描述不能为空")
    @Column(name = "description")
    private String description;//详情描述√

    @Lob
    @ValidateEntity(required = true, errorRequiredMsg = "请填写竞买公告")
    @Column(name = "notice")
    private String notice;//竞买公告√

    @Lob
    @ValidateEntity(required = true, errorRequiredMsg = "请填写竞买须知")
    @Column(name = "bidding_information")
    private String biddingInformation;//竞买须知√

    @ValidateEntity(required = true,errorRequiredMsg = "请填写尾款线上支付截止",requiredLeng = true,minLength = 4,maxLength = 20,errorMinLengthMsg = "尾款截止至少为4个字",errorMaxLengthMsg = "尾款截止最多为20个字")
    @Column(name = "payment_date")
    private String paymentDate;//尾款线上支付截止日期√

    @Column(name = "certificate")
    private String certificate;//授权书 √

    @Column(name = "circulation_end_time")
    private Date circulationEndTime;//流转结束日期

    @Column(name = "transaction_price")
    private int transactionPrice;//成交价

    @Column(name = "transaction_time")
    private Date transactionTime;//交易时间

    @ManyToOne
    @JoinColumn(name = "home_user_id")
    private HomeUser homeUser;//成交方

    @Column(name = "current_price")
    private int currentPrice;//当前价

    @Column(name = "auction_times")
    private int auctionTimes;//竞拍次数

    @Column(name = "applicants_number")
    private int applicantsNumber;//报名人数

    @ValidateEntity(required = true,errorRequiredMsg = "请填写尾款支付方式",requiredLeng = true,minLength = 1,maxLength =10,errorMinLengthMsg = "尾款支付方式至少为1个字",errorMaxLengthMsg = "尾款支付方式最多为10个字")
    @Column(name = "payment_method")
    private String paymentMethod;//尾款支付方式√

    @Column(name = "delay_period")
    private int delayPeriod=CycleType.FIVE.getdays();//延时周期√

    @Column(name = "views_number")
    private int viewsNumber;//浏览次数

    @ValidateEntity(required = true,errorRequiredMsg = "请填写联系人")
    @Column(name = "contacts",nullable = false,length = 11)
    private String contacts;//联系人√

    @ValidateEntity(required = true,errorRequiredMsg = "请填写手机号",requiredLeng = true,minLength = 5,maxLength = 18,errorMinLengthMsg = "联系电话至少为5位",errorMaxLengthMsg = "联系电话最多为18位")
    @Column(name = "phone",nullable = false,length = 16)
    private String phone;//手机号 √

    @Column(name = "reason")
    private String reason; //审批理由

    public String getProjectNumber() {
        return projectNumber;
    }

    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public LabelType getLabelType() {
        return labelType;
    }

    public void setLabelType(LabelType labelType) {
        this.labelType = labelType;
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


    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getBiddingStartTime() {
        return biddingStartTime;
    }

    public void setBiddingStartTime(Date biddingStartTime) {
        this.biddingStartTime = biddingStartTime;
    }

    public Date getBiddingEndTime() {
        return biddingEndTime;
    }

    public void setBiddingEndTime(Date biddingEndTime) {
        this.biddingEndTime = biddingEndTime;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public int getYearsType() {
        return yearsType;
    }

    public void setYearsType(int yearsType) {
        this.yearsType = yearsType;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTransferor() {
        return transferor;
    }

    public void setTransferor(String transferor) {
        this.transferor = transferor;
    }

    public int getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(int startPrice) {
        this.startPrice = startPrice;
    }

    public int getRateIncrease() {
        return rateIncrease;
    }

    public void setRateIncrease(int rateIncrease) {
        this.rateIncrease = rateIncrease;
    }

    public int getBond() {
        return bond;
    }

    public void setBond(int bond) {
        this.bond = bond;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getBiddingInformation() {
        return biddingInformation;
    }

    public void setBiddingInformation(String biddingInformation) {
        this.biddingInformation = biddingInformation;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public Date getCirculationEndTime() {
        return circulationEndTime;
    }

    public void setCirculationEndTime(Date circulationEndTime) {
        this.circulationEndTime = circulationEndTime;
    }

    public int getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(int transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public HomeUser getHomeUser() {
        return homeUser;
    }

    public void setHomeUser(HomeUser homeUser) {
        this.homeUser = homeUser;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getAuctionTimes() {
        return auctionTimes;
    }

    public void setAuctionTimes(int auctionTimes) {
        this.auctionTimes = auctionTimes;
    }

    public int getApplicantsNumber() {
        return applicantsNumber;
    }

    public void setApplicantsNumber(int applicantsNumber) {
        this.applicantsNumber = applicantsNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getDelayPeriod() {
        return delayPeriod;
    }

    public void setDelayPeriod(int delayPeriod) {
        this.delayPeriod = delayPeriod;
    }

    public int getViewsNumber() {
        return viewsNumber;
    }

    public void setViewsNumber(int viewsNumber) {
        this.viewsNumber = viewsNumber;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "BiddingProject{" +
                "projectNumber='" + projectNumber + '\'' +
                ", labelType=" + labelType +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", projectStatus=" + projectStatus +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", biddingStartTime=" + biddingStartTime +
                ", biddingEndTime=" + biddingEndTime +
                ", yearsType=" + yearsType +
                ", years=" + years +
                ", title='" + title + '\'' +
                ", transferor='" + transferor + '\'' +
                ", startPrice=" + startPrice +
                ", rateIncrease=" + rateIncrease +
                ", bond=" + bond +
                ", rate=" + rate +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", notice='" + notice + '\'' +
                ", biddingInformation='" + biddingInformation + '\'' +
                ", paymentDate=" + paymentDate +
                ", certificate='" + certificate + '\'' +
                ", circulationEndTime=" + circulationEndTime +
                ", transactionPrice=" + transactionPrice +
                ", transactionTime=" + transactionTime +
                ", homeUser=" + homeUser +
                ", currentPrice=" + currentPrice +
                ", auctionTimes=" + auctionTimes +
                ", applicantsNumber=" + applicantsNumber +
                ", paymentMethod=" + paymentMethod +
                ", delayPeriod=" + delayPeriod +
                ", viewsNumber=" + viewsNumber +
                '}';
    }

}
