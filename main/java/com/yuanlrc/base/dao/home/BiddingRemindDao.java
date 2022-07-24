package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.BiddingRemind;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 提醒Dao
 */
@Repository
public interface BiddingRemindDao extends JpaRepository<BiddingRemind,Long>, JpaSpecificationExecutor<BiddingRemind>{


    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Query("select b from BiddingRemind b where id = :id")
    BiddingRemind find(@Param("id") Long id);

    /**
     * 根据项目id用户id查询
     * @param biddingProjectId
     * @param homeUserId
     * @return
     */
    BiddingRemind findByBiddingProjectIdAndHomeUserId(Long biddingProjectId, Long homeUserId);

    /**
     * 根据项目id查询
     * @param biddingProjectId
     * @return
     */
    List<BiddingRemind> findByBiddingProjectId(Long biddingProjectId);

    /**
     * 根据项目id和状态查询
     * @param bidderProjectId
     * @param status
     * @return
     */
    List<BiddingRemind> findByBiddingProjectIdAndStatus(Long bidderProjectId,int status);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update ylrc_bidding_remind set status = :status where id = :id",nativeQuery = true)
    int updateStatus(@Param("id")Long id,@Param("status")int status);
}
