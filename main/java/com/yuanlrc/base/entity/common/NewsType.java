package com.yuanlrc.base.entity.common;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.entity.admin.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

/**
 * 新闻类型实体类
 */
@Entity
@Table(name="ylrc_news_type")
@EntityListeners(AuditingEntityListener.class)
public class NewsType extends BaseEntity {

    @ValidateEntity(required=true,requiredLeng=true,minLength=2,maxLength=18,errorRequiredMsg="名称不能为空!",errorMinLengthMsg="名称长度需大于2!",errorMaxLengthMsg="名称长度不能大于18!")
    @Column(name="name",nullable=false,length=18,unique=true)
    private String name;//名称


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NewsType{" +
                "name='" + name + '\'' +
                '}';
    }
}
