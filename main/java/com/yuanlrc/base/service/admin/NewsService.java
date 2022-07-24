package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.InformDao;
import com.yuanlrc.base.dao.admin.NewsDao;
import com.yuanlrc.base.entity.admin.Inform;
import com.yuanlrc.base.entity.home.News;
import com.yuanlrc.base.entity.home.OrganizationAlipay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 新闻 service
 * @author zhong
 */
@Service
public class NewsService {

    @Autowired
    private NewsDao newsDao;

    public News find(Long id)
    {
        return newsDao.find(id);
    }

    public List<News> findTop(){
        return newsDao.findTop4ByOrderByCreateTimeDesc();
    }

    public News save(News entity)
    {
        return newsDao.save(entity);
    }

    public void delete(Long id)
    {
        newsDao.deleteById(id);
    }

    public List<News> findAll()
    {
        return newsDao.findAll();
    }

    public PageBean<News> findList(News news, PageBean<News> pageBean)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withMatcher("caption", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<News> example = Example.of(news, exampleMatcher);

        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize());
        Page<News> findAll = newsDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    public PageBean<News> findHomeList(String province, String city, String county, PageBean<News> pageBean) {


        Specification<News> specification = new Specification<News>() {

            private static final long serialVersionUID = 1l;

            @Override
            public Predicate toPredicate(Root<News> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Predicate predicate = criteriaBuilder.like(root.get("province"), province == null ? "%%" :  "%"+province+"%");

                Predicate like = criteriaBuilder.like(root.get("city"), city == null ? "%%" : "%"+city+"%");

                Predicate like2 = criteriaBuilder.like(root.get("county"), county == null ? "%%" : "%" + county + "%");

                predicate = criteriaBuilder.and(predicate, like, like2);

                return predicate;
            }
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(),sort);
        Page<News> findAll = newsDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }
}
