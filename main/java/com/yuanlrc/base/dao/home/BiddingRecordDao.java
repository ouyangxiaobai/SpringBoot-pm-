package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.BiddingApply;
import com.yuanlrc.base.entity.home.BiddingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 竞价Dao
 */
@Repository
public interface BiddingRecordDao extends JpaRepository<BiddingRecord,Long>, JpaSpecificationExecutor<BiddingRecord> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Query("select b from BiddingRecord b where id = :id")
    BiddingRecord find(@Param("id")Long id);

    /**
     * 根据项目id查找竞价列表
     * @param projectId
     * @return
     */
    List<BiddingRecord>findByBiddingProjectId(@Param("projectId")Long projectId);

    /**
     * 根据项目id 和状态查询
     * @param projectId
     * @param status
     * @return
     */
    BiddingRecord findByBiddingProjectIdAndBiddingStatus(Long projectId, Integer status);

    /**
     * 根据id更新竞拍状态
     * @param id
     * @param status
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update ylrc_bidding_record set bidding_status=:status where id=:id",nativeQuery = true)
    int updateBiddingStatus(@Param("id")Long id,@Param("status") Integer status);
}
