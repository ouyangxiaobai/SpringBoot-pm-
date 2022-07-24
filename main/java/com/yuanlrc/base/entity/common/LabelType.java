package com.yuanlrc.base.entity.common;


import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.entity.admin.BaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Entity
@Table(name="ylrc_label_type")
@EntityListeners(AuditingEntityListener.class)
public class LabelType extends BaseEntity {

    @ValidateEntity(required = true,errorRequiredMsg = "标类型名称不能为空",requiredLeng = true,minLength = 1,maxLength = 10,errorMinLengthMsg = "标类型名称长度最少为1",errorMaxLengthMsg = "标类型名称长度最多为10")
    @Column(name = "name",nullable = false,length = 10)
    private String name; //标类型名称

    @Column(name = "status",length = 2)
    private int status=UserStatus.ACTIVE.getCode();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LabelType{" +
                "name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
