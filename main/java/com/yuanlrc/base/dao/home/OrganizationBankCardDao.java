package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.entity.home.OrganizationBankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 企业银行卡 dao
 * @author zhong
 */
@Repository
public interface OrganizationBankCardDao extends JpaRepository<OrganizationBankCard, Long>,
        JpaSpecificationExecutor<OrganizationBankCard> {

    @Query("select o from OrganizationBankCard o where o.id = :id")
    OrganizationBankCard find(@Param("id")Long id);


    OrganizationBankCard findByCardNumbers(String CardNumbers);

    List<OrganizationBankCard> findByOrganizationId(Long id);
}
