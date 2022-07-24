package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.entity.home.OrganizationAlipay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 企业/ 机构 支付 dao
 * @author zhong
 */
public interface OrganizationAlipayDao extends JpaRepository<OrganizationAlipay, Long>,
        JpaSpecificationExecutor<OrganizationAlipay> {

    @Query("select o from OrganizationAlipay o where o.id = :id")
    OrganizationAlipay find(@Param("id")Long id);

    OrganizationAlipay findByOutTradeNo(String outTradeNo);
}
