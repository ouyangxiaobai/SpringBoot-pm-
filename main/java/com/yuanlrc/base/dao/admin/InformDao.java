package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.Inform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 通知 dao层
 * @author zhong
 */
@Repository
public interface InformDao extends JpaRepository<Inform, Long>, JpaSpecificationExecutor<Inform> {

    @Query("select i from Inform i where i.id = :id")
    Inform find(@Param("id")Long id);

    Inform findByCaption(String caption);

}
