package com.yuanlrc.base.dao.home;

import com.yuanlrc.base.entity.home.UserAlipay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * 用户充值订单Dao
 */
@Repository
public interface UserAlipayDao extends JpaRepository<UserAlipay,Long>, JpaSpecificationExecutor<UserAlipay> {

    /**
     * 根据订单号查询
     * @param outTradeNo
     * @return
     */
    UserAlipay findByOutTradeNo(String outTradeNo);

}
