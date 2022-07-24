package com.yuanlrc.base.entity.home;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.entity.admin.BaseEntity;
import com.yuanlrc.base.entity.common.NewsType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 新闻实体类
 */
@Entity
@Table(name="ylrc_news")
@EntityListeners(AuditingEntityListener.class)
public class News extends BaseEntity {

    @ValidateEntity(required=true,requiredLeng=true,minLength=2,maxLength=30,errorRequiredMsg="标题不能为空!",errorMinLengthMsg="标题长度需大于2!",errorMaxLengthMsg="标题长度不能大于30!")
    @Column(name="caption",nullable=false,length=30)
    private String caption;//标题

    @ValidateEntity(required=true,requiredLeng = true,minLength = 10,maxLength = 100,errorRequiredMsg = "请上传封面图片!",errorMinLengthMsg = "请上传封面图片",errorMaxLengthMsg = "只能上传一张封面图片")
    @Column(name="picture",length=128)
    private String picture;//封面图片

    @ManyToOne
    @JoinColumn(name = "news_type_id")
    private NewsType newsType;//所属类型

    @Lob
    @ValidateEntity(required = true, errorRequiredMsg="内容不能为空!")
    @Column(name = "content")
    private String content;//内容

    @Column(name = "source", nullable = false)
    @ValidateEntity(required = true,requiredLeng = true,
            minLength = 2, maxLength = 20,
            errorRequiredMsg = "来源不能为空", errorMinLengthMsg = "来源长度最小为2字", errorMaxLengthMsg = "来源最大不能大于20字")
    private String source;

    @Column(name = "province", nullable = false)
    @ValidateEntity(required = true, errorRequiredMsg = "省份不能为空")
    private String province; //省

    @Column(name = "city")
    @ValidateEntity(required = true, errorRequiredMsg = "市不能为空")
    private String city; //市

    @Column(name = "county")
    @ValidateEntity(required = true, errorRequiredMsg = "区不能为空")
    private String county; //区

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public NewsType getNewsType() {
        return newsType;
    }

    public void setNewsType(NewsType newsType) {
        this.newsType = newsType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "News{" +
                "caption='" + caption + '\'' +
                ", picture='" + picture + '\'' +
                ", newsType=" + newsType +
                ", content='" + content + '\'' +
                ", source='" + source + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                '}';
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }
}
