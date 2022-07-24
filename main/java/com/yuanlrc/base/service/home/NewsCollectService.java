package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.home.NewsCollectDao;
import com.yuanlrc.base.entity.home.NewsCollect;
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

/**
 * 新闻收藏 service
 * @author zhong
 */
@Service
public class NewsCollectService {


    @Autowired
    private NewsCollectDao newsCollectDao;


    public NewsCollect find(Long id)
    {
        return newsCollectDao.find(id);
    }

    public NewsCollect save(NewsCollect entitiy)
    {
        return newsCollectDao.save(entitiy);
    }

    public void delete(Long id)
    {
        newsCollectDao.deleteById(id);
    }

    public NewsCollect findByNewsIdAndHomeUserId(Long newsId, Long userId)
    {
        return newsCollectDao.findByNewsIdAndHomeUserId(newsId, userId);
    }

    /**
     * 分页查询收藏新闻
     * @param homeUserId
     * @param pageBean
     * @return
     */
    public PageBean<NewsCollect> findList(Long homeUserId, PageBean<NewsCollect> pageBean){
        Specification<NewsCollect> specification = new Specification<NewsCollect>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<NewsCollect> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("homeUser"), homeUserId);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<NewsCollect> findAll = newsCollectDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;

    }
}
