package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.AuditStatus;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.dao.admin.RoleDao;
import com.yuanlrc.base.dao.home.OrganizationDao;
import com.yuanlrc.base.entity.admin.Role;
import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.entity.home.OrganizationBankCard;
import com.yuanlrc.base.entity.home.OrganizationWithdrawalRecord;
import com.yuanlrc.base.service.admin.RoleService;
import org.aspectj.weaver.ast.Or;
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
 * 机构 service
 * @author zhong
 * @date 2021-01-26
 */
@Service
public class OrganizationService {

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private RoleDao roleDao;


    public Organization find(Long id)
    {
        return organizationDao.find(id);
    }

    public Organization findByEmail(String email)
    {
        return organizationDao.findByEmail(email);
    }

    public Organization findByPhone(String phone)
    {
        return organizationDao.findByPhone(phone);
    }

    public Organization save(Organization organization)
    {
        return organizationDao.save(organization);
    }

    public List<Organization> findAll()
    {
        return organizationDao.findAll();
    }

    public void delete(Long id)
    {
        organizationDao.deleteById(id);
    }

    public PageBean<Organization> findList(Organization entitiy, PageBean<Organization> pageBean)
    {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("earnestMoney", "balance", "auditStatus", "payPassword");

        Sort sort = Sort.by(Sort.Direction.DESC, "auditStatus");
        Example<Organization> example = Example.of(entitiy, exampleMatcher);

        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<Organization> findAll = organizationDao.findAll(example, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());

        for (Organization organization : pageBean.getContent()) {
            organization.cardImgToImages();
        }
        return pageBean;
    }

    public Role findOrganizationRole()
    {
        return roleDao.find(5L); //企业默认ID
    }

    public String defaultPayPassword()
    {
        return "123456";
    }

    //是否可用编辑
    public boolean isOrganizationEdit(Organization login)
    {
        if(login.getAuditStatus().intValue() == AuditStatus.UNCOMMITTED.getCode().intValue() ||
                login.getAuditStatus().intValue() == AuditStatus.NOT_PASS.getCode().intValue() ||
                login.getAuditStatus().intValue() == AuditStatus.FREEZE.getCode().intValue())
            return true;

        return false;
    }

    //判断是否可用进行其他操作
    public boolean isOrganizationOperation(Organization login)
    {
        if(login.getAuditStatus().intValue() == AuditStatus.PASS.getCode().intValue())
            return true;

        return false;
    }

    //根据状态进行查询

    /**
     *
     * @param name 机构名称
     * @param status 是否可用
     * @param auditStatus 是否通过
     * @param pageBean
     * @return
     */
    public PageBean<Organization> findListByStatus(String name, Integer status,Integer auditStatus, PageBean<Organization> pageBean)
    {
        Specification<Organization> specification = new Specification<Organization>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Predicate predicate = criteriaBuilder.like(root.get("name"), name == null ? "%%" : "%"+name+"%");
                Predicate and = criteriaBuilder.equal(root.get("auditStatus"),  auditStatus);
                predicate = criteriaBuilder.and(predicate, and);
                return predicate;
            }
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(),sort);
        Page<Organization> find = organizationDao.findAll(specification, pageable);
        pageBean.setContent(find.getContent());
        pageBean.setTotal(find.getTotalElements());
        pageBean.setTotalPage(find.getTotalPages());
        return pageBean;
    }

    public Organization findByName(String name)
    {
        return organizationDao.findByName(name);
    }

}
