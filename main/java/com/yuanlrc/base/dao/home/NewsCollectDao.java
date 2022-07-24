package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.NewsCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 新闻收藏 dao
 * @author zhong
 */
@Repository
public interface NewsCollectDao extends JpaRepository<NewsCollect, Long>, JpaSpecificationExecutor<NewsCollect> {

    @Query("select n from NewsCollect n where n.id = :id")
    NewsCollect find(@Param("id")Long id);

    NewsCollect findByNewsIdAndHomeUserId(Long newsId, Long userId);
}
