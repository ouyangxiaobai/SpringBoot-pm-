package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.home.BiddingCollectDao;
import com.yuanlrc.base.entity.home.BiddingCollect;
import com.yuanlrc.base.entity.home.NewsCollect;
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

/**
 * 项目收藏Service
 */
@Service
public class BiddingCollectService {

    @Autowired
    private BiddingCollectDao biddingCollectDao;

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public BiddingCollect find(Long id){
        return biddingCollectDao.find(id);
    }

    /**
     * 保存
     * @param biddingCollect
     * @return
     */
    public BiddingCollect save(BiddingCollect biddingCollect){
        return biddingCollectDao.save(biddingCollect);
    }

    /**
     * 根据id删除
     * @param id
     */
    public void delete(Long id){
        biddingCollectDao.deleteById(id);
    }

    /**
     * 根据项目id和用户id查询
     * @param biddingProjectId
     * @param homeUserId
     * @return
     */
    public BiddingCollect findByBiddingProjectIdAndHomeUserId(Long biddingProjectId,Long homeUserId){
        return biddingCollectDao.findByBiddingProjectIdAndHomeUserId(biddingProjectId,homeUserId);
    }

    /**
     * 分页查询收藏项目
     * @param homeUserId
     * @param pageBean
     * @return
     */
    public PageBean<BiddingCollect> findList(Long homeUserId, PageBean<BiddingCollect> pageBean){
        Specification<BiddingCollect> specification = new Specification<BiddingCollect>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<BiddingCollect> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("homeUser"), homeUserId);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<BiddingCollect> findAll = biddingCollectDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;

    }

}
