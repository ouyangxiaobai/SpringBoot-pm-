package com.yuanlrc.base.service.common;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.common.NewsTypeDao;
import com.yuanlrc.base.entity.common.NewsType;
import com.yuanlrc.base.entity.home.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 新闻类型 service
 * @author zhong
 */
@Service
public class NewsTypeService {

    @Autowired
    private NewsTypeDao newsTypeDao;

    public NewsType find(Long id)
    {
        return newsTypeDao.find(id);
    }

    public NewsType save(NewsType entity)
    {
        return newsTypeDao.save(entity);
    }

    public void delete(Long id)
    {
        newsTypeDao.deleteById(id);
    }

    public List<NewsType> findAll()
    {
        return newsTypeDao.findAll();
    }

    public PageBean<NewsType> findList(NewsType entitiy, PageBean<NewsType> pageBean)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<NewsType> example = Example.of(entitiy, exampleMatcher);

        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize());
        Page<NewsType> findAll = newsTypeDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    public NewsType findByName(String name)
    {
        return newsTypeDao.findByName(name);
    }

    public boolean findByName(String name, Long id)
    {
        NewsType newsType = newsTypeDao.findByName(name);
        if(newsType!=null){
            if(!newsType.getId().equals(id)){
                return true;
            }
        }
        return false;
    }
}
