package com.yuanlrc.base.dao.common;

import com.yuanlrc.base.entity.common.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 新闻类型 dao
 * @author zhong
 */
@Repository
public interface NewsTypeDao extends JpaRepository<NewsType, Long> {

    @Query("select n from NewsType n where n.id = :id")
    NewsType find(@Param("id")Long id);

    NewsType findByName(String name);
}
