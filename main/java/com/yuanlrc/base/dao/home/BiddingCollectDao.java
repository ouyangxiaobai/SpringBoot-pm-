package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.BiddingCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 项目收藏Dao
 */
@Repository
public interface BiddingCollectDao extends JpaRepository<BiddingCollect,Long>, JpaSpecificationExecutor<BiddingCollect> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Query("select b from BiddingCollect b where id = :id")
    BiddingCollect find(@Param("id") Long id);

    /**
     * 根据项目id用户id查询
     * @param biddingProjectId
     * @param homeUserId
     * @return
     */
    BiddingCollect findByBiddingProjectIdAndHomeUserId(Long biddingProjectId,Long homeUserId);
}
