package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 通知公告
 * @author zhong
 * @date 2021-01-26
 */
@Entity
@Table(name="ylrc_inform")
@EntityListeners(AuditingEntityListener.class)
public class Inform extends BaseEntity{

    @ValidateEntity(required=true,requiredLeng=true,minLength=2,maxLength=30,errorRequiredMsg="标题不能为空!",errorMinLengthMsg="标题长度需大于2!",errorMaxLengthMsg="标题长度不能大于30!")
    @Column(name="caption",nullable=false,length=30,unique=true)
    private String caption;//标题

    @Lob
    @ValidateEntity(required = true,errorRequiredMsg="内容不能为空!")
    @Column(name = "content")
    private String content;//内容

    @Column(name = "source", nullable = false)
    @ValidateEntity(required = true, requiredLeng = true,
            minLength = 2, maxLength = 20,
            errorRequiredMsg = "来源不能为空", errorMinLengthMsg = "来源长度最小为2字", errorMaxLengthMsg = "来源最大不能大于20字")
    private String source; //来源

    @Column(name = "province", nullable = false)
    @ValidateEntity(required = true, errorRequiredMsg = "请选择省份")
    private String province; //省

    @Column(name = "city")
    @ValidateEntity(required = true, errorRequiredMsg = "请选择城市")
    private String city; //市

    @Column(name = "county")
    @ValidateEntity(required = true, errorRequiredMsg = "请选择区")
    private String county; //区

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public String toString() {
        return "Inform{" +
                "caption='" + caption + '\'' +
                ", content='" + content + '\'' +
                ", source='" + source + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                '}';
    }
}
