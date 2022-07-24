package com.yuanlrc.base.service.home;

import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.dao.admin.BiddingProjectDao;
import com.yuanlrc.base.dao.home.BiddingApplyDao;
import com.yuanlrc.base.dao.home.BiddingRecordDao;
import com.yuanlrc.base.dao.home.HomeUserDao;
import com.yuanlrc.base.dao.home.OrganizationDao;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.*;
import com.yuanlrc.base.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.GregorianCalendar;


@Service
public class BiddingRecordService {

    @Autowired
    private BiddingRecordDao biddingRecordDao;

    @Autowired
    private BiddingApplyDao biddingApplyDao;

    @Autowired
    private BiddingProjectDao biddingProjectDao;

    @Autowired
    private HomeUserDao homeUserDao;


    @Autowired
    private OrganizationDao organizationDao;



    /**
     * 根据id查询
     * @param id
     * @return
     */
    public BiddingRecord find(Long id){
        return biddingRecordDao.find(id);
    }

    /**
     * 分页查询用户竞拍
     * @param homeUserId
     * @param projectTimeVO
     * @param pageBean
     * @return
     */
    public PageBean<BiddingRecord> findList(Long homeUserId, ProjectTimeVO projectTimeVO,PageBean<BiddingRecord> pageBean,Integer biddingStatus){
        Specification<BiddingRecord> specification = new Specification<BiddingRecord>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<BiddingRecord> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("homeUser"), homeUserId);
                Predicate status = criteriaBuilder.equal(root.get("biddingStatus"), biddingStatus);
                predicate = criteriaBuilder.and(predicate,status);
                if(!StringUtils.isEmpty(projectTimeVO.getStartTime())){
                    Predicate start = greater(root, projectTimeVO.getStartTime(), "endTime", criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate,start);
                }
                if(!StringUtils.isEmpty(projectTimeVO.getEndTime())){
                    Predicate end = lessThan(root, projectTimeVO.getEndTime(), "startTime", criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate,end);
                }
                if(!StringUtils.isEmpty(projectTimeVO.getBiddingStartTime())){
                    Predicate biddingStart = greater(root, projectTimeVO.getBiddingStartTime(), "biddingEndTime", criteriaBuilder);
                    predicate = criteriaBuilder.and(predicate,biddingStart);
                }
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
        Page<BiddingRecord> findAll = biddingRecordDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }

    /**
     * 保存
     * @param biddingRecord
     * @return
     */
    public BiddingRecord save(BiddingRecord biddingRecord){
        return biddingRecordDao.save(biddingRecord);
    }

    /**
     * 支付
     * @param biddingRecord
     */
    @Transactional
    public BiddingRecord payMoney(BiddingRecord biddingRecord) {
        //修改竞拍信息支付状态
        biddingRecord.setPayStatus(BiddingRecord.STATUS_YES);

        Long homeUserId = biddingRecord.getHomeUser().getId();
        HomeUser homeUser = homeUserDao.find(homeUserId);
        Date date = new Date();
        //修改竞拍项目状态和信息
        BiddingProject biddingProject = biddingRecord.getBiddingProject();
        biddingProject.setProjectStatus(ProjectStatus.CLOSED);
        biddingProject.setHomeUser(homeUser);
        biddingProject.setTransactionTime(date);
        biddingProject.setTransactionPrice(biddingRecord.getBid());
        if(biddingProject.getYearsType() == YearType.YEARS.getCode()){
            Calendar curr = new GregorianCalendar();
            curr.setTime(date);
            curr.add(Calendar.YEAR,biddingProject.getYears());
            date=curr.getTime();
            biddingProject.setCirculationEndTime(date);
        }
        biddingProjectDao.save(biddingProject);
        //修改报名信息状态
        BiddingApply biddingApply = biddingApplyDao.findByBiddingProjectIdAndHomeUserId(biddingProject.getId(), homeUserId);
        biddingApply.setStatus(EarnestMoneyStatus.HAS_RETURN.getCode());
        biddingApplyDao.save(biddingApply);
        //修改用户余额
        homeUser.setBalance(homeUser.getBalance().subtract(new BigDecimal(biddingRecord.getBid())).add(new BigDecimal(biddingApply.getEarnestMoney())));
        homeUserDao.save(homeUser);
        return biddingRecordDao.save(biddingRecord);
    }

    /**
     * 根据项目id查找竞价列表
     * @param projectId
     * @return
     */
    public List<BiddingRecord> findByBiddingProjectId(Long projectId){
        return biddingRecordDao.findByBiddingProjectId(projectId);
    }

    public Predicate greater(Root root,String date,String attribute,CriteriaBuilder criteriaBuilder){
        Date time = DateUtil.toDate(date);
        Predicate predicate = criteriaBuilder.greaterThanOrEqualTo(root.get("biddingProject").get(attribute), time);
        return predicate;
    }

    public Predicate lessThan(Root root,String date,String attribute,CriteriaBuilder criteriaBuilder){
        Date time = DateUtil.toDate(date);
        time = DateUtil.tommorrow(time, 1);
        Predicate predicate = criteriaBuilder.lessThan(root.get("biddingProject").get(attribute), time);
        return predicate;
    }

    /**
     * 查出这个项目领先的人
     * @param projectId
     * @return
     */
    public BiddingRecord findByBiddingProjectIdAndBiddingStatus(Long projectId)
    {
        return biddingRecordDao.findByBiddingProjectIdAndBiddingStatus(projectId, BiddingStatus.LEADING.getCode());
    }

    @Transactional
    public BiddingRecord save(BiddingRecord biddingRecord, int earnestMoney, BiddingProject biddingProject, BiddingApply biddingApply, Organization organization) {

        //机构获得钱
        Organization find = organizationDao.find(organization.getId());
        find.setBalance(find.getBalance().add(new BigDecimal(earnestMoney)));

        organizationDao.save(find);

        //竞价表
        biddingRecord.setOverdueStatus(BiddingRecord.STATUS_YES);

        //设置报名报
        biddingApply.setStatus(EarnestMoneyStatus.BREACH.code);
        biddingApplyDao.save(biddingApply);

        //设置项目状态
        biddingProject.setProjectStatus(ProjectStatus.ENDBIDDING);

        return save(biddingRecord);
    }


    /**
     * 返回报名保险金
     * @param biddingApply
     * @param bond
     */
    @Transactional
    public void moneyBack(BiddingApply biddingApply,int bond){
        Long id = biddingApply.getHomeUser().getId();
        HomeUser homeUser = homeUserDao.find(id);
        BigDecimal balance = homeUser.getBalance();
        balance = balance.add(new BigDecimal(bond));
        homeUserDao.updateBalance(homeUser.getId(),balance);
        biddingApply.setStatus(EarnestMoneyStatus.HAS_RETURN.getCode());
        biddingApplyDao.save(biddingApply);
    }

    /**
     * 根据id更新竞拍状态
     * @param id
     * @param status
     * @return
     */
    public int updateBiddingStatus(Long id,Integer status){
        return biddingRecordDao.updateBiddingStatus(id,status);
    }

    /**
     * 根据项目id查询
     * @param biddingProjectId
     * @param pageBean
     * @return
     */
    public PageBean<BiddingRecord> findByBiddingProject(Long biddingProjectId,PageBean<BiddingRecord> pageBean){
        Specification<BiddingRecord> specification = new Specification<BiddingRecord>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<BiddingRecord> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("biddingProject"), biddingProjectId);
                return predicate;
            }
        };
        Sort sort = Sort.by(Sort.Direction.DESC, "biddingStatus","createTime");
        PageRequest pageable = PageRequest.of(pageBean.getCurrentPage() - 1, pageBean.getPageSize(), sort);
        Page<BiddingRecord> findAll = biddingRecordDao.findAll(specification, pageable);
        pageBean.setContent(findAll.getContent());
        pageBean.setTotal(findAll.getTotalElements());
        pageBean.setTotalPage(findAll.getTotalPages());
        return pageBean;
    }


    /**
     * 竞价更新操作
     * @param oldRecord
     * @param newRecord
     */
    @Transactional
    public void recordExist(BiddingRecord oldRecord, BiddingRecord newRecord){
        try{
            //把之前的领先人更新为出局
            updateBiddingStatus(oldRecord.getId(), BiddingStatus.OUT.getCode());
            //添加新的领先人
            save(newRecord);
            //更新当前价
            int currentPrice = newRecord.getBid();
            BiddingProject biddingProject = newRecord.getBiddingProject();
            Long id = biddingProject.getId();
            biddingProjectDao.updateCurrentPrice(currentPrice,id);
            //更新竞拍次数
            biddingProjectDao.updateAuctionTimes(biddingProject.getAuctionTimes()+1,id);
            //判断是不是最后五分钟
            long biddingEndTime = biddingProject.getBiddingEndTime().getTime();
            long currentTime = System.currentTimeMillis();
            //延时周期
            int delayPeriod = biddingProject.getDelayPeriod();
            long diff=(biddingEndTime-currentTime)/1000/60;
            if(diff<=delayPeriod){
                //延长项目竞价结束时间
                Date date = DateUtil.addDateMinut(biddingProject.getBiddingEndTime(), delayPeriod);
                //更新项目竞价结束时间
                biddingProjectDao.updateBiddingEndTime(date, id);
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

}
