package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.UserWithdrawalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户提现Dao
 */
@Repository
public interface UserWithdrawalRecordDao extends JpaRepository<UserWithdrawalRecord,Long>, JpaSpecificationExecutor<UserWithdrawalRecord> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Query("select u from UserWithdrawalRecord u where id = :id")
    UserWithdrawalRecord find(@Param("id")Long id);

}
