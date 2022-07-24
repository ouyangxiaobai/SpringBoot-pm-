package com.yuanlrc.base.entity.home;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.entity.admin.BaseEntity;
import com.yuanlrc.base.entity.common.BiddingProject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 留言回复实体
 */
@Entity
@Table(name = "ylrc_message_and_reply")
@EntityListeners(AuditingEntityListener.class)
public class MessageAndReply extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="home_user_id")
    private HomeUser homeUser;//用户

    @ManyToOne
    @JoinColumn(name="bidding_project_id")
    private BiddingProject biddingProject;//竞拍项目

    @ValidateEntity(required = true,requiredLeng = true,errorRequiredMsg = "留言不能为空",minLength = 1,maxLength = 100,errorMinLengthMsg = "留言长度最少为1",errorMaxLengthMsg = "留言长度最多为100")
    @Column(name = "message")
    private String message;//留言

    @Column(name = "reply")
    private String reply;//回复

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        return "MessageAndReply{" +
                "homeUser=" + homeUser +
                ", biddingProject=" + biddingProject +
                ", message='" + message + '\'' +
                ", reply='" + reply + '\'' +
                '}';
    }
}
