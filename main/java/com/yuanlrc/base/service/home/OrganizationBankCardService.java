package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.home.OrganizationBankCardDao;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.OrganizationAlipay;
import com.yuanlrc.base.entity.home.OrganizationBankCard;
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
 * 企业银行卡 service
 * @author zhong
 */
@Service
public class OrganizationBankCardService {

    @Autowired
    private OrganizationBankCardDao organizationBankCardDao;

    public OrganizationBankCard find(Long id)
    {
        return organizationBankCardDao.find(id);
    }

    public OrganizationBankCard save(OrganizationBankCard entity)
    {
        return organizationBankCardDao.save(entity);
    }

    public void delete(Long id)
    {
        organizationBankCardDao.deleteById(id);
    }

    public List<OrganizationBankCard> findAll()
    {
        return organizationBankCardDao.findAll();
    }

    public PageBean<OrganizationBankCard> findList(OrganizationBankCard entitiy, PageBean<OrganizationBankCard> pageBean)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<OrganizationBankCard> example = Example.of(entitiy, exampleMatcher);

        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize());
        Page<OrganizationBankCard> findAll = organizationBankCardDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }


    public PageBean<OrganizationBankCard> findListByOrgId(Long id, String name, PageBean<OrganizationBankCard> pageBean)
    {
        Specification<OrganizationBankCard> specification = new Specification<OrganizationBankCard>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<OrganizationBankCard> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("organization").get("id"), id);
                Predicate like = criteriaBuilder.like(root.get("name"),  name == null ? "%%" : "%"+name+"%");

                predicate = criteriaBuilder.and(predicate, like);

                return predicate;
            }
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(),sort);
        Page<OrganizationBankCard> find = organizationBankCardDao.findAll(specification, pageable);
        pageBean.setContent(find.getContent());
        pageBean.setTotal(find.getTotalElements());
        pageBean.setTotalPage(find.getTotalPages());
        return pageBean;
    }

    public OrganizationBankCard findByCardNumbers(String number)
    {
        return organizationBankCardDao.findByCardNumbers(number);
    }

    public List<OrganizationBankCard> findByOrganizationId(Long id)
    {
        return organizationBankCardDao.findByOrganizationId(id);
    }
}
