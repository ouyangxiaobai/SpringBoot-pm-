package com.yuanlrc.base.entity.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 常见问答实体类
 */
@Entity
@Table(name="ylrc_common_problem")
@EntityListeners(AuditingEntityListener.class)
public class CommonProblem extends BaseEntity {

    @ValidateEntity(required=true,requiredLeng=true,minLength=2,maxLength=200,errorRequiredMsg="问题名称不能为空!",errorMinLengthMsg="问题名称长度需大于2!",errorMaxLengthMsg="问题名称长度不能大于200!")
    @Column(name="name",nullable=false,length=200,unique=true)
    private String name;//问题名称

    @Lob
    @ValidateEntity(required = true,errorRequiredMsg = "解答不能为空!")
    @Column(name ="answer",nullable = false)
    private String answer;//解答

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "CommonProblem{" +
                "name='" + name + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
