package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.UserBankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 前台用户银行卡DAO
 */
@Repository
public interface UserBankCardDao extends JpaRepository<UserBankCard,Long> , JpaSpecificationExecutor<UserBankCard> {

    /**
     * 根据用户id查询
     * @param homeUserId
     * @return
     */
    List<UserBankCard> findByHomeUserId(Long homeUserId);

    /**
     * 根据卡号查询
     * @param cardNumbers
     * @return
     */
    UserBankCard findByCardNumbers(String cardNumbers);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Query("select u from UserBankCard u where id = :id")
    UserBankCard find(@Param("id") Long id);
}
