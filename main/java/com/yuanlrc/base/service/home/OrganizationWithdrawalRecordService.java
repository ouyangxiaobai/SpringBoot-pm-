package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.home.OrganizationDao;
import com.yuanlrc.base.dao.home.OrganizationWithdrawalRecordDao;
import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.entity.home.OrganizationBankCard;
import com.yuanlrc.base.entity.home.OrganizationWithdrawalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * 企业提现记录
 * @author zhong
 */
@Service
public class OrganizationWithdrawalRecordService {

    @Autowired
    private OrganizationWithdrawalRecordDao organizationWithdrawalRecordDao;

    @Autowired
    private OrganizationDao organizationDao;

    public OrganizationWithdrawalRecord find(Long id)
    {
        return organizationWithdrawalRecordDao.find(id);
    }

    public OrganizationWithdrawalRecord save(OrganizationWithdrawalRecord entity)
    {
        return organizationWithdrawalRecordDao.save(entity);
    }

    public void delete(Long id)
    {
        organizationWithdrawalRecordDao.deleteById(id);
    }

    public List<OrganizationWithdrawalRecord> findAll()
    {
        return organizationWithdrawalRecordDao.findAll();
    }

    public PageBean<OrganizationWithdrawalRecord> findList(OrganizationWithdrawalRecord entitiy, PageBean<OrganizationWithdrawalRecord> pageBean)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withMatcher("organization.name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("status", "organization.earnestMoney", "organization.balance",
                        "organization.status", "organization.auditStatus", "organization.payPassword");

        Example<OrganizationWithdrawalRecord> example = Example.of(entitiy, exampleMatcher);

        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize());
        Page<OrganizationWithdrawalRecord> findAll = organizationWithdrawalRecordDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    public PageBean<OrganizationWithdrawalRecord> findListByOrgId(Long id, String bankCard, PageBean<OrganizationWithdrawalRecord> pageBean) {

        Specification<OrganizationWithdrawalRecord> specification = new Specification<OrganizationWithdrawalRecord>() {
            @Override
            public Predicate toPredicate(Root<OrganizationWithdrawalRecord> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("organization").get("id"), id);

                Predicate likeName = criteriaBuilder.like(root.get("bankCard"), bankCard == null ? "%%" : "%"+bankCard+"%");

                predicate = criteriaBuilder.and(predicate, likeName);

                return predicate;
            }
        };


        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<OrganizationWithdrawalRecord> findAll = organizationWithdrawalRecordDao.findAll(specification, pageable);

        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        return pageBean;
    }

    @Transactional
    public OrganizationWithdrawalRecord save(OrganizationWithdrawalRecord find, Organization organization) {

        organizationDao.save(organization);

        return save(find);
    }
}
