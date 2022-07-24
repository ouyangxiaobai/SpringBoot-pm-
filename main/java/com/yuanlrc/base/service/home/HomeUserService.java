package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.home.HomeUserDao;
import com.yuanlrc.base.entity.home.HomeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 前台用户Service
 */
@Service
public class HomeUserService {

    @Autowired
    private HomeUserDao homeUserDao;

    /**
     * 后台查询用户列表
     * @param homeUser
     * @param pageBean
     * @return
     */
    public PageBean<HomeUser> findList(HomeUser homeUser,PageBean<HomeUser> pageBean){
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains());
        exampleMatcher = exampleMatcher.withIgnorePaths("sex","status","balance");
        Example<HomeUser> example = Example.of(homeUser,exampleMatcher);
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize());
        Page<HomeUser> findAll = homeUserDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 添加保存
     * @param homeUser
     * @return
     */
    public HomeUser save(HomeUser homeUser){
        return homeUserDao.save(homeUser);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public HomeUser find(Long id){
        return homeUserDao.find(id);
    }

    /**
     * 根据手机号查询
     * @param mobile
     * @return
     */
    public HomeUser findByMobile(String mobile){
        return homeUserDao.findByMobile(mobile);
    }

    /**
     * 根据身份证号查询
     * @param idNumber
     * @return
     */
    public HomeUser findByIdNumber(String idNumber){
        return homeUserDao.findByIdNumber(idNumber);
    }

    /**
     * 根据邮箱查询
     * @param email
     * @return
     */
    public HomeUser findByEmail(String email){
        return homeUserDao.findByEmail(email);
    }

    /**
     * 更新用户状态
     * @param homeUser
     * @return
     */
    public int updateStatus(HomeUser homeUser){
        return homeUserDao.updateStatus(homeUser);
    }

    /**
     * 更新用户余额
     * @param id
     * @param balance
     * @return
     */
    public int updateBalance(Long id, BigDecimal balance){
        return homeUserDao.updateBalance(id,balance);
    }
}
