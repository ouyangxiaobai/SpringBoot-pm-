package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.home.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 新闻 dao
 * @author zhong
 */
@Repository
public interface NewsDao extends JpaRepository<News, Long>, JpaSpecificationExecutor<News> {

    @Query("select n from News n where n.id = :id")
    News find(@Param("id")Long id);

    /**
     * 查询4个
     * @return
     */
    List<News> findTop4ByOrderByCreateTimeDesc();
}