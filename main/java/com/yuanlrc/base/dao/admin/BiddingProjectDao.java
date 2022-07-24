package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.bean.ProjectStatus;
import com.yuanlrc.base.entity.admin.ApprovalRecord;
import com.yuanlrc.base.entity.common.BiddingProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 竞拍数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface BiddingProjectDao extends JpaRepository<BiddingProject, Long>,JpaSpecificationExecutor<BiddingProject> {


	/**
	 * 根据竞拍id查询
	 * @param id
	 * @return
	 */
	@Query("select bp from BiddingProject bp where id = :id")
	public BiddingProject find(@Param("id") Long id);

	/**
	 * 更新项目状态
	 * @param id
	 * @param status
	 * @return
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update ylrc_bidding_project set project_status=:status where id=:id",nativeQuery = true)
	int  updateStatus(@Param("id")Long id,@Param("status") int status);

	/**
	 * 项目审核
	 * @param approvalRecord
	 * @return
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update ylrc_bidding_project set project_status=:#{#approvalRecord.projectStatus.code},reason=:#{#approvalRecord.reason} where id=:#{#approvalRecord.biddingProject.id}",nativeQuery = true)
	int updateApproval(@Param("approvalRecord") ApprovalRecord approvalRecord);

	/**
	 * 更新浏览量
	 * @param viewNumber
	 * @param id
	 * @return
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update ylrc_bidding_project set views_number=:viewNumber where id=:id",nativeQuery = true)
	int updateViewsNumber(@Param("viewNumber")int viewNumber,@Param("id")Long id);

	/**
	 * 根据项目id更新当前价
	 * @param currentPrice
	 * @param id
	 * @return
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update ylrc_bidding_project set current_price=:currentPrice where id=:id",nativeQuery = true)
	int updateCurrentPrice(@Param("currentPrice")int currentPrice,@Param("id")Long id);


	/**
	 * 根据状态公示中和竞拍中查找项目
	 * @param inpublic
	 * @param bidding
	 * @return
	 */
	List<BiddingProject> findByProjectStatusOrProjectStatus(ProjectStatus inpublic,ProjectStatus bidding);

	/**
	 * 查询这5种状态的前8个
	 * @param inPublic
	 * @param bidding
	 * @param successfulBidding
	 * @param endBidding
	 * @param closed
	 * @return
	 */
	List<BiddingProject> findTop8ByProjectStatusOrProjectStatusOrProjectStatusOrProjectStatusOrProjectStatus(ProjectStatus inPublic,ProjectStatus bidding,ProjectStatus successfulBidding,ProjectStatus endBidding,ProjectStatus closed);


	/**
	 * 根据项目id更新竞拍次数
	 * @param auctionTimes
	 * @param id
	 * @return
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update ylrc_bidding_project set auction_times=:auctionTimes where id=:id",nativeQuery = true)
	int updateAuctionTimes(@Param("auctionTimes")int auctionTimes,@Param("id")Long id);

	/**
	 * 根据项目Id更新报名人数
	 * @param applicantsNumber
	 * @param id
	 * @return
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update ylrc_bidding_project set applicants_number=:applicantsNumber where id=:id",nativeQuery = true)
	int updateApplicantsNumber(@Param("applicantsNumber")int applicantsNumber,@Param("id")Long id);

	/**
	 * 根据标类型查询
	 * @param labelTypeId
	 * @return
	 */
    List<BiddingProject> findByLabelTypeId(Long labelTypeId);

	/**
	 * 根据项目Id更新竞价结束时间 延时周期
	 * @param biddingEndTime
	 * @param id
	 * @return
	 */
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "update ylrc_bidding_project set bidding_end_time=:biddingEndTime where id=:id",nativeQuery = true)
	int updateBiddingEndTime(@Param("biddingEndTime")Date biddingEndTime, @Param("id")Long id);
}
