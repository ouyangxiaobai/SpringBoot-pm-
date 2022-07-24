package com.yuanlrc.base.dao.admin;

import com.yuanlrc.base.entity.admin.ApprovalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 审批记录数据库处理层
 * @author Administrator
 *
 */
@Repository
public interface ApprovalRecordDao extends JpaRepository<ApprovalRecord, Long>,JpaSpecificationExecutor<ApprovalRecord> {


	/**
	 * 根据记录id查询
	 * @param id
	 * @return
	 */
	@Query("select ar from ApprovalRecord ar where id = :id")
	public ApprovalRecord find(@Param("id") Long id);


	ApprovalRecord findByBiddingProjectIdAndProjectStatus(Long projectId, Integer status);

}
