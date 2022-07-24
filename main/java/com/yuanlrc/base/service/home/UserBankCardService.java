package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.home.UserBankCardDao;
import com.yuanlrc.base.entity.home.UserBankCard;
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
import java.util.List;

/**
 * 用户银行卡Service
 */
@Service
public class UserBankCardService {

    @Autowired
    private UserBankCardDao userBankCardDao;

    /**
     * 保存
     * @param userBankCard
     * @return
     */
    public UserBankCard save(UserBankCard userBankCard){
        return userBankCardDao.save(userBankCard);
    }

    /**
     * 根据id删除
     * @param id
     */
    public void delete(Long id){
        userBankCardDao.deleteById(id);
    }

    /**
     * 根据卡号查询
     * @param cardNumbers
     * @return
     */
    public UserBankCard findByCardNumbers(String cardNumbers){
        return userBankCardDao.findByCardNumbers(cardNumbers);
    }

    /**
     * 分页查询银行卡
     * @param homeUserId
     * @param pageBean
     * @return
     */
    public PageBean<UserBankCard> findList(Long homeUserId,PageBean<UserBankCard> pageBean){
        Specification<UserBankCard> specification = new Specification<UserBankCard>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<UserBankCard> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("homeUser"), homeUserId);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<UserBankCard> findAll = userBankCardDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;

    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public UserBankCard find(Long id){
        return userBankCardDao.find(id);
    }

    /**
     * 根据用户id查询
     * @param homeUserId
     * @return
     */
    public List<UserBankCard> finByHomeUserId(Long homeUserId) {
        return userBankCardDao.findByHomeUserId(homeUserId);
    }
}
