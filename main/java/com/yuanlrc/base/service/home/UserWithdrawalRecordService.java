package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.home.HomeUserDao;
import com.yuanlrc.base.dao.home.UserWithdrawalRecordDao;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.UserWithdrawalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 用户提现Service
 */
@Service
public class UserWithdrawalRecordService {

    @Autowired
    private UserWithdrawalRecordDao userWithdrawalRecordDao;

    @Autowired
    private HomeUserDao homeUserDao;

    /**
     * 根据用户查询
     * @param homeUserId
     * @return
     */
    public PageBean<UserWithdrawalRecord> findList(Long homeUserId, PageBean< UserWithdrawalRecord>  pageBean){
        Specification< UserWithdrawalRecord> specification = new Specification< UserWithdrawalRecord>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root< UserWithdrawalRecord> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("homeUser"), homeUserId);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<UserWithdrawalRecord> findAll = userWithdrawalRecordDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 查询全部
     * @param userWithdrawalRecord
     * @param pageBean
     * @return
     */
    public PageBean<UserWithdrawalRecord> findAll(UserWithdrawalRecord userWithdrawalRecord,PageBean<UserWithdrawalRecord> pageBean){
        ExampleMatcher withMather = ExampleMatcher.matching().withMatcher("bankCard", ExampleMatcher.GenericPropertyMatchers.contains());
        withMather = withMather.withIgnorePaths("status");
        Example<UserWithdrawalRecord> example = Example.of(userWithdrawalRecord, withMather);
        Sort sort = Sort.by(Sort.Direction.DESC,"status");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<UserWithdrawalRecord> findAll = userWithdrawalRecordDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 审批同意
     * @param  userWithdrawalRecord
     * @param homeUser
     */
    @Transactional
    public  UserWithdrawalRecord approval(UserWithdrawalRecord  userWithdrawalRecord, HomeUser homeUser){
        homeUser.setBalance(homeUser.getBalance().subtract(userWithdrawalRecord.getMoney()));
        homeUserDao.save(homeUser);
        return userWithdrawalRecordDao.save(userWithdrawalRecord);
    }

    /**
     * 保存
     * @param  UserWithdrawalRecord
     * @return
     */
    public  UserWithdrawalRecord save( UserWithdrawalRecord  UserWithdrawalRecord){
        return userWithdrawalRecordDao.save( UserWithdrawalRecord);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public  UserWithdrawalRecord findById(Long id){
        return userWithdrawalRecordDao.find(id);
    }

}
