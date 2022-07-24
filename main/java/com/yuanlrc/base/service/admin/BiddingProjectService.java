package com.yuanlrc.base.service.admin;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.ProjectStatus;
import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.dao.admin.BiddingProjectDao;
import com.yuanlrc.base.entity.admin.ApprovalRecord;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.common.LabelType;
import com.yuanlrc.base.entity.home.ProjectVo;
import com.yuanlrc.base.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 竞拍管理service
 *
 * @author Administrator
 */
@Service
public class BiddingProjectService {

    @Autowired
    private BiddingProjectDao biddingProjectDao;

    /**
     * 根据竞拍id查询
     *
     * @param id
     * @return
     */
    public BiddingProject find(Long id) {
        return biddingProjectDao.find(id);
    }

    /**
     * 竞拍添加/编辑操作
     *
     * @param biddingProject
     * @return
     */
    public BiddingProject save(BiddingProject biddingProject) {
        return biddingProjectDao.save(biddingProject);
    }


    /**
     * 竞拍分页list操作
     *
     * @param biddingProject
     * @param pageBean
     * @return
     */
    public PageBean<BiddingProject> findList(BiddingProject biddingProject, PageBean<BiddingProject> pageBean) {
        Specification<BiddingProject> specification = new Specification<BiddingProject>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("title"), "%" + (biddingProject.getTitle() == null ? "" : biddingProject.getTitle() + "%"));

                if (biddingProject.getOrganization() != null) {
                    Predicate equal = criteriaBuilder.equal(root.get("organization"), biddingProject.getOrganization().getId());
                    predicate = criteriaBuilder.and(predicate, equal);
                }
                if (biddingProject.getProjectStatus() == ProjectStatus.REVIEWED) {
                    Predicate equal2 = criteriaBuilder.equal(root.get("projectStatus"), biddingProject.getProjectStatus().code);
                    predicate = criteriaBuilder.and(predicate, equal2);
                }
                if (biddingProject.getProjectStatus() == ProjectStatus.INPUBLIC) {
                    Predicate equal3 = criteriaBuilder.equal(root.get("projectStatus"), biddingProject.getProjectStatus().code);
                    predicate = criteriaBuilder.and(predicate, equal3);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<BiddingProject> findAll = biddingProjectDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 根据机构ID查询出这个下的可用项目
     *
     * @param orgId    机构ID
     * @param title    项目标题
     * @param pageBean
     * @return
     */
    public PageBean<BiddingProject> findList(Long orgId, String title, PageBean<BiddingProject> pageBean) {
        Specification<BiddingProject> specification = new Specification<BiddingProject>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate predicate = criteriaBuilder.like(root.get("title"), "%" + (title == null ? "" : title + "%"));
                Predicate idEq = criteriaBuilder.equal(root.get("organization"), orgId);

                Predicate statusEq = criteriaBuilder.equal(root.get("labelType").get("status"), UserStatus.ACTIVE.getCode());

                predicate = criteriaBuilder.and(predicate, idEq, statusEq);


                predicate = activeStatus(criteriaBuilder, root, predicate);

                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<BiddingProject> findAll = biddingProjectDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 按照竞拍id删除
     *
     * @param id
     */
    public void delete(Long id) {
        biddingProjectDao.deleteById(id);
    }

    /**
     * 返回竞拍总数
     *
     * @return
     */
    public long total() {
        return biddingProjectDao.count();
    }

    /**
     * 发布项目
     *
     * @param id
     * @param status
     * @return
     */
    public int updateStatus(Long id, int status) {
        return biddingProjectDao.updateStatus(id, status);
    }

    /**
     * 项目审核
     *
     * @param approvalRecord
     * @return
     */
    public int updateApproval(ApprovalRecord approvalRecord) {
        return biddingProjectDao.updateApproval(approvalRecord);
    }


    //获取项目可用状态
    public Predicate activeStatus(CriteriaBuilder criteriaBuilder, Root root, Predicate predicate) {
        //公示中
        Predicate status = criteriaBuilder.equal(root.get("projectStatus"), ProjectStatus.INPUBLIC.code);

        //竞价中
        Predicate status2 = criteriaBuilder.equal(root.get("projectStatus"), ProjectStatus.BIDDING.code);

        //竞价成功
        Predicate status3 = criteriaBuilder.equal(root.get("projectStatus"), ProjectStatus.SUCCESSFULBIDDING.code);

        //竞价结束
        Predicate status4 = criteriaBuilder.equal(root.get("projectStatus"), ProjectStatus.ENDBIDDING.code);

        //已成交
        Predicate status5 = criteriaBuilder.equal(root.get("projectStatus"), ProjectStatus.CLOSED.code);

        status = criteriaBuilder.or(status, status2, status3, status4, status5);
        predicate = criteriaBuilder.and(predicate, status);

        return predicate;
    }

    /**
     * 更新浏览量
     *
     * @param viewsNumber
     * @param id
     * @return
     */
    public int updateViewsNumber(int viewsNumber, Long id) {
        return biddingProjectDao.updateViewsNumber(viewsNumber, id);
    }

    /**
     * 产权交易首页
     *
     * @param pageBean
     * @param biddingProject
     * @return
     */
    public PageBean<BiddingProject> findHomeList(BiddingProject biddingProject, PageBean<BiddingProject> pageBean) {
        Specification<BiddingProject> specification = new Specification<BiddingProject>() {
            @Override
            public Predicate toPredicate(Root<BiddingProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("title"), "%" + (biddingProject.getTitle() == null ? "" : biddingProject.getTitle() + "%"));
                if (biddingProject.getProjectStatus() == ProjectStatus.NOTSUBMIT) {
                    //说明没有条件
                    predicate = activeStatus(criteriaBuilder, root, predicate);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<BiddingProject> findAll = biddingProjectDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 根据状态公示中和竞拍中查找项目
     *
     * @param inpublic
     * @param bidding
     * @return
     */
    public List<BiddingProject> findByProjectStatusOrProjectStatus(ProjectStatus inpublic, ProjectStatus bidding) {
        return biddingProjectDao.findByProjectStatusOrProjectStatus(inpublic, bidding);
    }

    /**
     * 前台交易大厅多条件查询
     *
     * @param pageBean
     * @param projectVo
     * @return
     */
    public PageBean<BiddingProject> findListByAgency(PageBean<BiddingProject> pageBean, ProjectVo projectVo) {
        Specification<BiddingProject> specification = new Specification<BiddingProject>() {
            @Override
            public Predicate toPredicate(Root<BiddingProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("projectNumber"), "%%");
                //状态
                if (projectVo.getProjectStatus() != null) {
                    Predicate statusEq = criteriaBuilder.equal(root.get("projectStatus"), projectVo.getProjectStatus());
                    predicate = criteriaBuilder.and(predicate, statusEq);
                } else {
                    Predicate statusEq = criteriaBuilder.equal(root.get("projectStatus"), ProjectStatus.BIDDING.code);
                    Predicate status2Eq = criteriaBuilder.equal(root.get("projectStatus"), ProjectStatus.CLOSED.code);
                    statusEq = criteriaBuilder.or(status2Eq, statusEq);
                    predicate = criteriaBuilder.and(predicate, statusEq);
                }
                predicate = homeQuery(criteriaBuilder, root, predicate, projectVo);
                return predicate;
            }
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<BiddingProject> findAll = biddingProjectDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    //根据省市区和标签进行过滤
    public Predicate homeQuery(CriteriaBuilder criteriaBuilder, Root root, Predicate predicate, ProjectVo projectVo) {
        //省份
        if (projectVo.getProvince() != null) {
            Predicate provinceEq = criteriaBuilder.like(root.get("province"), "%" + projectVo.getProvince() + "%");
            predicate = criteriaBuilder.and(predicate, provinceEq);
        }
        //城市
        if (projectVo.getCity() != null) {
            Predicate cityEq = criteriaBuilder.like(root.get("city"), "%" + projectVo.getCity() + "%");
            predicate = criteriaBuilder.and(predicate, cityEq);
        }
        Predicate labelTypeAllEq = criteriaBuilder.equal(root.get("labelType").get("status"), UserStatus.ACTIVE.getCode());
        predicate = criteriaBuilder.and(predicate, labelTypeAllEq);

        //标签
        if (projectVo.getLabelType() != null) {
            Predicate labelTypeEq = criteriaBuilder.equal(root.get("labelType").get("id"), projectVo.getLabelType());
            predicate = criteriaBuilder.and(predicate, labelTypeEq);
        }

        //地区
        if (projectVo.getArea() != null) {
            Predicate areaEq = criteriaBuilder.like(root.get("area"), "%" + projectVo.getArea() + "%");
            predicate = criteriaBuilder.and(predicate, areaEq);
        }
        return predicate;
    }

    /**
     * 查询多条件
     *
     * @param pageBean
     * @param projectVo
     * @return
     */
    public PageBean<BiddingProject> findListBidding(PageBean<BiddingProject> pageBean, ProjectVo projectVo) {
        Specification<BiddingProject> specification = new Specification<BiddingProject>() {
            @Override
            public Predicate toPredicate(Root<BiddingProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Predicate predicate = criteriaBuilder.like(root.get("title"), projectVo.getTitle() == null ?
                        "%%" : "%" + projectVo.getTitle() + "%");

                //状态
                if (projectVo.getProjectStatus() != null) {
                    Predicate statusEq = criteriaBuilder.equal(root.get("projectStatus"), projectVo.getProjectStatus());
                    predicate = criteriaBuilder.and(predicate, statusEq);
                } else {
                    //添加状态
                    predicate = activeStatus(criteriaBuilder, root, predicate);
                }

                //添加省市区多条件查询
                predicate = homeQuery(criteriaBuilder, root, predicate, projectVo);

                //判断时间多条件查询
                if (!StringUtils.isEmpty(projectVo.getStartTime())) {
                    Predicate start = greater(root, projectVo.getStartTime(), "endTime", criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate, start);
                }

                if (!StringUtils.isEmpty(projectVo.getEndTime())) {
                    Predicate end = lessThan(root, projectVo.getEndTime(), "startTime", criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate, end);
                }

                if (!StringUtils.isEmpty(projectVo.getBiddingStartTime())) {
                    Predicate biddingStart = greater(root, projectVo.getBiddingStartTime(), "biddingEndTime", criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate, biddingStart);
                }

                if (!StringUtils.isEmpty(projectVo.getBiddingEndTime())) {
                    Predicate biddingEnd = lessThan(root, projectVo.getBiddingEndTime(), "biddingStartTime", criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate, biddingEnd);
                }

                return predicate;
            }
        };

        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<BiddingProject> findAll = biddingProjectDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 根据5种状态查询前8个
     *
     * @return
     */
    public List<BiddingProject> findTop(ProjectStatus inPublic, ProjectStatus bidding, ProjectStatus successfulBidding, ProjectStatus endBidding, ProjectStatus closed) {
        return biddingProjectDao.findTop8ByProjectStatusOrProjectStatusOrProjectStatusOrProjectStatusOrProjectStatus(inPublic, bidding, successfulBidding, endBidding, closed);
    }

    public BiddingProject status(BiddingProject biddingProject, Date date) {
        if (biddingProject.getProjectStatus() != ProjectStatus.ENDBIDDING && biddingProject.getProjectStatus() != ProjectStatus.SUCCESSFULBIDDING && biddingProject.getProjectStatus() != ProjectStatus.CLOSED) {
            //计算距离开始时间
            if (biddingProject.getProjectStatus() != ProjectStatus.BIDDING) {
                //报名开始
                Date startTime = biddingProject.getStartTime();
                Date startDate = biddingProject.getBiddingStartTime();
                if (startTime.getTime() <= date.getTime() && date.getTime() <= biddingProject.getEndTime().getTime()) {
                    biddingProject.setProjectStatus(ProjectStatus.REGISTERING);
                } else if (startDate.getTime() > date.getTime() && date.getTime() > biddingProject.getEndTime().getTime()) {
                    biddingProject.setProjectStatus(ProjectStatus.AUCTIONSOON);
                }
            }
        }
        return biddingProject;
    }

    public List<BiddingProject> findTop8() {
        Specification<BiddingProject> specification = new Specification<BiddingProject>() {
            @Override
            public Predicate toPredicate(Root<BiddingProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("labelType").get("status"), UserStatus.ACTIVE.getCode());
                predicate = activeStatus(criteriaBuilder, root, predicate);

                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        List<BiddingProject> topList = new ArrayList<>();
        List<BiddingProject> biddingProjects = biddingProjectDao.findAll(specification, sort);
        if (biddingProjects.size() <= 8) {
            topList = biddingProjects;
        } else {
            topList = biddingProjects.subList(0, 8);
        }
        return topList;
    }


    public Predicate greater(Root root, String date, String attribute, CriteriaBuilder criteriaBuilder) {
        Date time = DateUtil.toDate(date);
        Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get(attribute), time);
        return predicate;
    }

    public Predicate lessThan(Root root, String date, String attribute, CriteriaBuilder criteriaBuilder) {
        Date time = DateUtil.toDate(date);
        time = DateUtil.tommorrow(time, 1);
        Predicate predicate = criteriaBuilder.lessThan(root.get(attribute), time);
        return predicate;
    }

    /**
     * 根据项目id更新竞拍次数
     *
     * @param auctionTimes
     * @param id
     * @return
     */
    public int updateAuctionTimes(int auctionTimes, Long id) {
        return biddingProjectDao.updateAuctionTimes(auctionTimes, id);
    }

    /**
     * 根据项目Id更新报名人数
     *
     * @param applicantsNumber
     * @param id
     * @return
     */
    public int updateApplicantsNumber(int applicantsNumber, Long id) {
        return biddingProjectDao.updateApplicantsNumber(applicantsNumber, id);
    }

    public List<BiddingProject> findByLableType(Long labelTypeId) {
        return biddingProjectDao.findByLabelTypeId(labelTypeId);
    }

    /**
     * 根据项目Id更新竞价结束时间 延时周期
     * @param biddingEndTime
     * @param id
     * @return
     */
    public int updateBiddingEndTime(Date biddingEndTime,Long id){
        return biddingProjectDao.updateBiddingEndTime(biddingEndTime,id);
    }

    /**
     * 根据项目id更新当前价
     * @param currentPrice
     * @param id
     * @return
     */
    public int updateCurrentPrice(int currentPrice,Long id){
        return biddingProjectDao.updateCurrentPrice(currentPrice,id);
    }
}
