package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.OrganizationWithdrawalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 企业提现记录管理
 * @author zhong
 */
@Repository
public interface OrganizationWithdrawalRecordDao extends JpaRepository<OrganizationWithdrawalRecord, Long>,
        JpaSpecificationExecutor<OrganizationWithdrawalRecord> {

    @Query("select o from OrganizationWithdrawalRecord o where o.id = :id")
    OrganizationWithdrawalRecord find(@Param("id")Long id);
}
