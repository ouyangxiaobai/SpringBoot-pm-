package com.yuanlrc.base.entity.home;

import com.yuanlrc.base.entity.admin.BaseEntity;
import com.yuanlrc.base.entity.common.BiddingProject;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * 新闻资讯收藏
 */
@Entity
@Table(name = "ylrc_news_collect")
@EntityListeners(AuditingEntityListener.class)
public class NewsCollect extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="home_user_id")
    private HomeUser homeUser;//用户

    @ManyToOne
    @JoinColumn(name="news_id")
    private News news;//新闻

    public HomeUser getHomeUser() {
        return homeUser;
    }

    public void setHomeUser(HomeUser homeUser) {
        this.homeUser = homeUser;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    @Override
    public String toString() {
        return "NewsCollect{" +
                "homeUser=" + homeUser +
                ", news=" + news +
                '}';
    }
}
