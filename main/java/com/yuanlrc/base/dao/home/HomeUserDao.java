package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.HomeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 前台用户Dao
 */
@Repository
public interface HomeUserDao extends JpaRepository<HomeUser,Long> {

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @Query("select h from HomeUser h where id = :id")
    public HomeUser find(@Param("id") Long id);

    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    public HomeUser findByMobile(String mobile);

    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    public HomeUser findByEmail(String email);

    /**
     * 根据身份证号查询
     * @param idNumber
     * @return
     */
    public HomeUser findByIdNumber(String idNumber);

    @Transactional
    @Modifying
    @Query(value = "update ylrc_home_user set  status=:#{#homeUser.status} where id=:#{#homeUser.id}",nativeQuery = true)
    int updateStatus(@Param("homeUser")HomeUser homeUser);

    /**
     * 更新用户余额
     * @param id
     * @param balance
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update ylrc_home_user set balance = :balance where id = :id",nativeQuery = true)
    int updateBalance(@Param("id")Long id, @Param("balance")BigDecimal balance);
}
