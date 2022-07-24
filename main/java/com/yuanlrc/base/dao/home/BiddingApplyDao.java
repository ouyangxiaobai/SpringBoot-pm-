package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.BiddingApply;
import com.yuanlrc.base.entity.home.HomeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞拍报名Dao
 */
@Repository
public interface BiddingApplyDao extends JpaRepository<BiddingApply, Long>,JpaSpecificationExecutor<BiddingApply> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Query("select ba from BiddingApply ba where id = :id")
    public BiddingApply find(@Param("id") Long id);

    /**
     * 根据项目id查询报名列表
     * @param id
     * @return
     */
    public List<BiddingApply> findByBiddingProjectId(@Param("projectId")Long id);

    /**
     * 根据项目id和用户id查询
     * @param biddingProjectId
     * @param homeUserId
     * @return
     */
    BiddingApply findByBiddingProjectIdAndHomeUserId(Long biddingProjectId,Long homeUserId);

    /**
     * 根据项目id查询除了领先的记录
     * @param biddingProjectId
     * @param homeUserId
     * @return
     */
    List<BiddingApply> findByBiddingProjectIdAndHomeUserIdNotAndStatus(Long biddingProjectId,Long homeUserId,Integer status);


    List<BiddingApply> findByBiddingProjectIdAndStatus(Long biddingProjectId,int status);

}
