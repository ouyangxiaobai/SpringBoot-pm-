package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.home.OrganizationAlipayDao;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.common.NewsType;
import com.yuanlrc.base.entity.home.Organization;
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
 * 企业支付订单管理 service
 * @author zhong
 */
@Service
public class OrganizationAlipayService {

    @Autowired
    private OrganizationAlipayDao organizationAlipayDao;

    public OrganizationAlipay find(Long id)
    {
        return organizationAlipayDao.find(id);
    }

    public OrganizationAlipay save(OrganizationAlipay entity)
    {
        return organizationAlipayDao.save(entity);
    }

    public void delete(Long id)
    {
        organizationAlipayDao.deleteById(id);
    }

    public List<OrganizationAlipay> findAll()
    {
        return organizationAlipayDao.findAll();
    }

    public PageBean<OrganizationAlipay> findList(OrganizationAlipay entitiy, PageBean<OrganizationAlipay> pageBean)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withMatcher("outTradeNo", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("status");

        Example<OrganizationAlipay> example = Example.of(entitiy, exampleMatcher);

        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize());
        Page<OrganizationAlipay> findAll = organizationAlipayDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    public PageBean<OrganizationAlipay> findListByOrgId(Long id, String outTradeNo, PageBean<OrganizationAlipay> pageBean)
    {
        Specification<OrganizationAlipay> specification = new Specification<OrganizationAlipay>() {

            private static final long serialVersionUID = 1l;

            @Override
            public Predicate toPredicate(Root<OrganizationAlipay> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Predicate predicate = criteriaBuilder.equal(root.get("organization").get("id"), id);

                Predicate like = criteriaBuilder.like(root.get("outTradeNo"), outTradeNo == null ? "%%" : "%"+outTradeNo+"%");

                predicate = criteriaBuilder.and(predicate, like);
                return predicate;
            }
        };

        Sort sort =Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(),sort);
        Page<OrganizationAlipay> findAll = organizationAlipayDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    public OrganizationAlipay findByOutTradeNo(String outTradeNo)
    {
        return organizationAlipayDao.findByOutTradeNo(outTradeNo);
    }
}
