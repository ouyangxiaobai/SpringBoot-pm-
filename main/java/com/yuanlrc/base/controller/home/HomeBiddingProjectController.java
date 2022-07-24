package com.yuanlrc.base.controller.home;

import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.BiddingApply;
import com.yuanlrc.base.entity.home.BiddingRecord;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.ProjectVo;
import com.yuanlrc.base.redis.RedisLock;
import com.yuanlrc.base.service.admin.BiddingProjectService;
import com.yuanlrc.base.service.admin.LabelTypeService;
import com.yuanlrc.base.service.home.*;
import com.yuanlrc.base.util.DateUtil;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.jdbc.Expectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

/**
 * 项目详情控制器
 *
 * @author Administrator
 */
@RequestMapping("/home/bidding")
@Controller
public class HomeBiddingProjectController {

    @Autowired
    private LabelTypeService labelTypeService;

    @Autowired
    private BiddingProjectService biddingProjectService;

    @Autowired
    private BiddingApplyService biddingApplyService;

    @Autowired
    private BiddingCollectService biddingCollectService;

    @Autowired
    private BiddingRemindService biddingRemindService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisLock redisLock;

    @Autowired
    private BiddingRecordService biddingRecordService;

    @Autowired
    private HomeUserService homeUserService;

    private static final int TIMEOUT = 10 * 1000;

    /**
     * 产权交易列表
     *
     * @param
     * @param pageBean
     * @param model
     * @return
     */
    @GetMapping("/transaction")
    public String transaction(PageBean<BiddingProject> pageBean, Model model, ProjectVo projectVo) {
        pageBean.setPageSize(12);

        model.addAttribute("labelTypeList", labelTypeService.findByStatus(UserStatus.ACTIVE.getCode()));
        model.addAttribute("projectVo", projectVo);
        model.addAttribute("pageBean", biddingProjectService.findListBidding(pageBean, projectVo));
        return "home/project/transaction";
    }

    /**
     * 项目详情
     *
     * @param model
     * @return
     */
    @GetMapping("/detail")
    public String projectDetails(@RequestParam("id") Long id, Model model,PageBean<BiddingRecord> pageBean) throws ParseException {
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        BiddingProject biddingProject = biddingProjectService.find(id);
        if (biddingProject == null) {
            model.addAttribute("msg", "未找到该项目!");
            return "/error/404";
        }
        //更新浏览量
        if (biddingProjectService.updateViewsNumber(biddingProject.getViewsNumber() + 1, biddingProject.getId()) <= 0) {
            model.addAttribute("msg", "浏览量更新失败");
            return "/error/404";
        }
        Date date = new Date();
        String currentTime = DateUtil.datetimeFormat.format(date);
        //计算周期
        String cycleDate = DateUtil.getDatePoor(biddingProject.getBiddingEndTime(), biddingProject.getBiddingStartTime());
        biddingProject = biddingProjectService.status(biddingProject, date);
        model.addAttribute("recordList",biddingRecordService.findByBiddingProject(id,pageBean));
        model.addAttribute("systemDate", currentTime);
        model.addAttribute("cycleDate", cycleDate);
        model.addAttribute("projectDetail", biddingProject);
        model.addAttribute("biddingRemind", loginedHomeUser == null ? null : biddingRemindService.findByBiddingProjectIdAndHomeUserId(id, loginedHomeUser.getId()));
        model.addAttribute("projectCollect", loginedHomeUser == null ? null : biddingCollectService.findByBiddingProjectIdAndHomeUserId(id, loginedHomeUser.getId()));
        return "/home/project/detail";
    }

    /**
     * 报名
     *
     * @param payPassword
     * @return
     */
    @ResponseBody
    @PostMapping("/signUp")
    public Result<Boolean> signUp(@RequestParam("payPassword") String payPassword, @RequestParam("projectId") Long projectId) {
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        //判断用户是否登录
        if (loginedHomeUser == null) {
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        if (!loginedHomeUser.getPayPassword().equals(payPassword)) {
            return Result.error(CodeMsg.COMMON_PAY_PASSWORD_ERROR);
        }
        //判断是否存在该竞拍
        BiddingProject biddingProject = biddingProjectService.find(projectId);
        if (biddingProject == null) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_NOT_EXIST_ERROR);
        }
        int bond = biddingProject.getBond();
        BigDecimal balance = loginedHomeUser.getBalance();
        BigDecimal projectBond = BigDecimal.valueOf(bond);
        BigDecimal subtract = balance.subtract(projectBond);
        BigDecimal zero = BigDecimal.ZERO;
        if (subtract.compareTo(zero) == -1) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_BOND_PAY_ERROR);
        }
        //修改用户的账户余额
        if(homeUserService.updateBalance(loginedHomeUser.getId(), subtract)<=0){
            return Result.error(CodeMsg.BALANCE_UPDATE_ERROR);
        }
        BiddingApply biddingApply1 = biddingApplyService.findByBiddingProjectIdAndHomeUserId(biddingProject.getId(), loginedHomeUser.getId());
        if (biddingApply1 == null) {
            //说明没有报名过
            //表示可以支付 进入报名表
            BiddingApply biddingApply = new BiddingApply();
            biddingApply.setHomeUser(loginedHomeUser);
            biddingApply.setBiddingProject(biddingProject);
            biddingApply.setEarnestMoney(bond);
            if (biddingApplyService.save(biddingApply) == null) {
                return Result.error(CodeMsg.HOME_SIGN_UP_ADD_ERROR);
            }
            //更新报名次数
            if(biddingProjectService.updateApplicantsNumber(biddingProject.getApplicantsNumber() + 1, projectId)<=0){
                return Result.error(CodeMsg.HOME_BIDDING_PROJECT_APPLICANTSNUMBER_UPDATE_ERROR);
            }
        } else {
            //说明已经报名过了
            return Result.error(CodeMsg.HOME_SIGN_UP_EXIST_ERROR);
        }
        return Result.success(true);
    }


    /**
     * 确认出价
     *
     * @param projectId
     * @param money
     * @return
     */
    @ResponseBody
    @PostMapping("/offer")
    public Result<Boolean> offerPrice(@RequestParam("projectId") Long projectId, @RequestParam("money") int money) {
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        BiddingProject biddingProject = biddingProjectService.find(projectId);
        if (biddingProject == null) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_NOT_EXIST_ERROR);
        }
        BiddingApply biddingApply = biddingApplyService.findByBiddingProjectIdAndHomeUserId(biddingProject.getId(), loginedHomeUser.getId());
        if (biddingApply == null) {
            return Result.error(CodeMsg.HOME_SIGN_UP_NOT_EXIST_ERROR);
        }
        //判断竞拍价是否大于起拍价
        if(biddingProject.getStartPrice()>money){
            return Result.error(CodeMsg.HOME_BIDDING_PROJECT_START_PRICE_ERROR);
        }
        //判断项目状态是否为竞价中
        if (biddingProject.getProjectStatus() != ProjectStatus.BIDDING) {
            return Result.error(CodeMsg.HOME_BIDDING_PROJECT_STATUS_ERROR);
        }
        //锁
        long time = System.currentTimeMillis() + TIMEOUT;
        synchronized(this) {
            try {
                boolean lock = redisLock.lock(projectId.toString(), String.valueOf(time));
                if (!lock) {
                    return Result.error(CodeMsg.HOME_BIDDING_PROJECT_PERSON_MANY);
                }
                //开始竞价
                String maxMoney = redisTemplate.opsForValue().get("stock" + projectId);
                //如果Redis中为空
                if (StringUtils.isEmpty(maxMoney)) {
                    //从数据库获取
                    BiddingRecord leadingRecord = biddingRecordService.findByBiddingProjectIdAndBiddingStatus(projectId);
                    if (leadingRecord == null) {
                        //如果Redis中为空 数据库中也为空
                        return recordNotExist(biddingProject, money, loginedHomeUser, time);
                    } else {
                        //如果Redis中为空 数据库中不为空
                        return recordExist(biddingProject, money, loginedHomeUser, time, leadingRecord);
                    }
                } else {
                    //如果Redis中不为空
                    Integer maxMoneys = Integer.valueOf(maxMoney);
                    //判断竞价价格和最高价格
                    if (maxMoneys < money) {
                        maxMoneys = money;
                        redisTemplate.opsForValue().set("stock" + projectId, String.valueOf(maxMoneys));
                    }
                    BiddingRecord oldRecord = biddingRecordService.findByBiddingProjectIdAndBiddingStatus(projectId);
                    if(oldRecord.getBid()>=money){
                        return Result.error(CodeMsg.HOME_BIDDING_PROJECT_MONEY_ERROR);
                    }
                    return recordRedisExist(biddingProject, maxMoneys, loginedHomeUser, oldRecord, time);
                }
            }catch (Exception e){
                e.printStackTrace();
                return Result.error(CodeMsg.HOME_BIDDING_PROJECT_PERSON_MANY_ERROR);
            }
        }
    }

    /**
     * 如果redis中为空 数据库中也为空
     *
     * @param biddingProject
     * @param maxMoney
     * @param homeUser
     * @return
     */
    public Result<Boolean> recordNotExist(BiddingProject biddingProject, Integer maxMoney, HomeUser homeUser, Long time) {
        Long projectId = biddingProject.getId();
        BiddingRecord biddingRecord = recordInit(homeUser, biddingProject, maxMoney);
        //保存到竞价表
        if (biddingRecordService.save(biddingRecord) == null) {
            return Result.error(CodeMsg.HOME_BIDDING_RECORD_ADD);
        }
        //更新项目的当前价
        if(biddingProjectService.updateCurrentPrice(maxMoney, projectId)<=0){
            return Result.error(CodeMsg.HOME_BIDDING_PROJECT_CURRENT_PRICE_ERROR);
        }
        //更新竞拍次数
        if(biddingProjectService.updateAuctionTimes(biddingProject.getAuctionTimes() + 1, projectId)<=0){
            return Result.error(CodeMsg.HOME_BIDDING_PROJECT_AUCTIONTIMES_ADD_ERROR);
        }
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
            if(biddingProjectService.updateBiddingEndTime(date, projectId)<=0){
                return Result.error(CodeMsg.HOME_BIDDING_PROJECT_ENDTIME_UPDATE_ERROR);
            }
        }
        redisTemplate.opsForValue().set("stock" + projectId, String.valueOf(maxMoney));
        redisLock.unlock(projectId.toString(), String.valueOf(time));
        return Result.success(true);
    }


    /**
     * Redis中为空 数据库中不为空
     *
     * @param biddingProject
     * @param money
     * @param homeUser
     * @param time
     * @param oldRecord
     * @return
     */
    public Result<Boolean> recordExist(BiddingProject biddingProject, Integer money, HomeUser homeUser, Long time, BiddingRecord oldRecord) {
        if (money <= oldRecord.getBid()) {
            return Result.error(CodeMsg.HOME_BIDDING_RECORD_BID_MIN_ERROR);
        }
        BiddingRecord newRecord = recordInit(homeUser, biddingProject, money);
        Long projectId = biddingProject.getId();
        biddingRecordService.recordExist(oldRecord, newRecord);
        redisTemplate.opsForValue().set("stock" + projectId, String.valueOf(money));
        redisLock.unlock(projectId.toString(), String.valueOf(time));
        return Result.success(true);
    }

    /**
     * Redis中不为空
     *
     * @param biddingProject
     * @param money
     * @param homeUser
     * @param oldRecord
     * @return
     */
    public Result<Boolean> recordRedisExist(BiddingProject biddingProject, Integer money, HomeUser homeUser, BiddingRecord oldRecord, Long time) {
        BiddingRecord newRecord = recordInit(homeUser, biddingProject, money);
        Long projectId = biddingProject.getId();
        biddingRecordService.recordExist(oldRecord, newRecord);
        redisLock.unlock(projectId.toString(), String.valueOf(time));
        return Result.success(true);
    }

    /**
     * 添加竞价记录
     * @param homeUser
     * @param biddingProject
     * @param money
     * @return
     */
    public BiddingRecord recordInit(HomeUser homeUser,BiddingProject biddingProject,Integer money){
        BiddingRecord newRecord = new BiddingRecord();
        newRecord.setHomeUser(homeUser);
        newRecord.setBiddingProject(biddingProject);
        newRecord.setBiddingStatus(BiddingStatus.LEADING.code);
        newRecord.setBid(money);
        return newRecord;
    }


}
