package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.InformDao;
import com.yuanlrc.base.entity.admin.CommonProblem;
import com.yuanlrc.base.entity.admin.Inform;
import com.yuanlrc.base.entity.home.News;
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
 * 通知 service
 * @author zhong
 */
@Service
public class InformService {

    @Autowired
    private InformDao informDao;

    public Inform find(Long id)
    {
        return informDao.find(id);
    }

    public Inform save(Inform entity)
    {
        return informDao.save(entity);
    }

    public void delete(Long id)
    {
        informDao.deleteById(id);
    }

    public List<Inform> findAll()
    {
        return informDao.findAll();
    }

    public PageBean<Inform> findList(Inform inform, PageBean<Inform> pageBean)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withMatcher("caption", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Inform> example = Example.of(inform, exampleMatcher);

        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize());
        Page<Inform> findAll = informDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    public PageBean<Inform> findHomeList(String province, String city, String county, PageBean<Inform> pageBean)
    {
        Specification<Inform> specification = new Specification<Inform>() {

            private static final long serialVersionUID = 1l;

            @Override
            public Predicate toPredicate(Root<Inform> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Predicate like = criteriaBuilder.like(root.get("city"), city == null ? "%%" : "%"+city+"%");

                Predicate like2 = criteriaBuilder.like(root.get("county"), county == null ? "%%" : "%" + county + "%");

                Predicate predicate = criteriaBuilder.like(root.get("province"), province == null ? "%%" :  "%"+province+"%");

                predicate = criteriaBuilder.and(predicate, like, like2);

                return predicate;
            }
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(),sort);
        Page<Inform> findAll = informDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    public Inform findByCaption(String caption)
    {
        return informDao.findByCaption(caption);
    }

}
