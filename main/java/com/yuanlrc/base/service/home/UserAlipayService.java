package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.home.UserAlipayDao;
import com.yuanlrc.base.entity.home.UserAlipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class UserAlipayService {

    @Autowired
    private UserAlipayDao userAlipayDao;

    /**
     * 保存
     * @param UserAlipay
     * @return
     */
    public UserAlipay save(UserAlipay UserAlipay){
        return userAlipayDao.save(UserAlipay);
    }

    /**
     * 根据订单号查询
     * @param outTradeNo
     * @return
     */
    public UserAlipay findByOutTradeNo(String outTradeNo){
        return userAlipayDao.findByOutTradeNo(outTradeNo);
    }

    /**
     * 根据前台用户id查询
     * @param homeUserId
     * @return
     */
    public PageBean<UserAlipay> findList(Long homeUserId,PageBean<UserAlipay>  pageBean){
        Specification<UserAlipay> specification = new Specification<UserAlipay>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<UserAlipay> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("homeUser"), homeUserId);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<UserAlipay> findAll = userAlipayDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

}
