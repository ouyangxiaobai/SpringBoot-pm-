
package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.UserBiddingStatus;
import com.yuanlrc.base.dao.home.BiddingApplyDao;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.BiddingApply;
import com.yuanlrc.base.entity.home.ProjectTimeVO;
import com.yuanlrc.base.entity.home.UserAlipay;
import com.yuanlrc.base.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 竞拍报名Service
 */
@Service
public class BiddingApplyService {

    @Autowired
    private BiddingApplyDao biddingApplyDao;

    /**
     * 根据前台用户id查询
     * @param homeUserId
     * @return
     */
    public PageBean<BiddingApply> findList(Long homeUserId, ProjectTimeVO projectTimeVO, PageBean<BiddingApply>  pageBean){

        Specification<BiddingApply> specification = new Specification<BiddingApply>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<BiddingApply> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("homeUser"), homeUserId);

                //报名开始时间
                if(!StringUtils.isEmpty(projectTimeVO.getStartTime())){
                    Predicate start = greater(root, projectTimeVO.getStartTime(), "endTime", criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate,start);
                }

                //报名结束时间
                if(!StringUtils.isEmpty(projectTimeVO.getEndTime())){
                    Predicate end = lessThan(root, projectTimeVO.getEndTime(), "startTime", criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate,end);
                }

                //竞拍开始时间
                if(!StringUtils.isEmpty(projectTimeVO.getBiddingStartTime())){
                    Predicate biddingStart = greater(root, projectTimeVO.getBiddingStartTime(), "biddingEndTime", criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate,biddingStart);
                }

                //竞拍结束时间
                if(!StringUtils.isEmpty(projectTimeVO.getBiddingEndTime())){
                    Predicate biddingEnd = lessThan(root, projectTimeVO.getBiddingEndTime(), "biddingStartTime", criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate,biddingEnd);
                }

                if(projectTimeVO.getStatus() != -1){
                    Predicate equal = criteriaBuilder.equal(root.get("biddingProject").get("projectStatus"), projectTimeVO.getStatus());
                    predicate = criteriaBuilder.and(predicate,equal);
                }
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<BiddingApply> findAll = biddingApplyDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }


    /**
     * 添加保存
     *
     * @param biddingApply
     * @return
     */
    public BiddingApply save(BiddingApply biddingApply) {
        return biddingApplyDao.save(biddingApply);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public BiddingApply find(Long id) {
        return biddingApplyDao.find(id);
    }


    /**
     * 报名列表分页查询
     * @param biddingApply
     * @param pageBean
     * @return
     */
    public PageBean<BiddingApply>findList(BiddingApply biddingApply,PageBean<BiddingApply> pageBean){
        Specification<BiddingApply> specification = new Specification<BiddingApply>() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.like(root.get("biddingProject").get("title"), biddingApply.getBiddingProject() == null ? "" : biddingApply.getBiddingProject().getTitle());
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Pageable pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(),sort);
        Page<BiddingApply> findAll = biddingApplyDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }
    /**
     * 根据项目id查询报名列表
     * @param projectId
     * @return
     */
    public List<BiddingApply> findByBiddingProjectId(Long projectId){
        return biddingApplyDao.findByBiddingProjectId(projectId);
    }

    /**
     * 根据项目id和用户id查询
     * @param biddingProjectId
     * @param homeUserId
     * @return
     */
    public BiddingApply findByBiddingProjectIdAndHomeUserId(Long biddingProjectId,Long homeUserId){
        return biddingApplyDao.findByBiddingProjectIdAndHomeUserId(biddingProjectId,homeUserId);
    }

    public List<BiddingApply> findByBiddingProjectAndHomeUserNot(Long biddingProjectId,Long homeUserId,int status){
        return biddingApplyDao.findByBiddingProjectIdAndHomeUserIdNotAndStatus(biddingProjectId,homeUserId,status);
    }

    public List<BiddingApply> findByBiddingProjectAndStatus(Long biddingProjectId,int status){
        return biddingApplyDao.findByBiddingProjectIdAndStatus(biddingProjectId,status);
    }

    /**
     * 大于等于
     * @param root
     * @param date
     * @param attribute
     * @param criteriaBuilder
     * @return
     */
    public Predicate greater(Root root,String date,String attribute,CriteriaBuilder criteriaBuilder){
        Date time = DateUtil.toDate(date);
        Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("biddingProject").get(attribute), time);
        return predicate;
    }

    /**
     * 小于
     * @param root
     * @param date
     * @param attribute
     * @param criteriaBuilder
     * @return
     */
    public Predicate lessThan(Root root,String date,String attribute,CriteriaBuilder criteriaBuilder){
        Date time = DateUtil.toDate(date);
        time = DateUtil.tommorrow(time, 1);
        Predicate predicate = criteriaBuilder.lessThan(root.get("biddingProject").get(attribute), time);
        return predicate;
    }
}

