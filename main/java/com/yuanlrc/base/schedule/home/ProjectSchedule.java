package com.yuanlrc.base.schedule.home;

import com.yuanlrc.base.bean.EarnestMoneyStatus;
import com.yuanlrc.base.bean.ProjectStatus;
import com.yuanlrc.base.bean.RemindType;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.BiddingApply;
import com.yuanlrc.base.entity.home.BiddingRecord;
import com.yuanlrc.base.entity.home.BiddingRemind;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.service.admin.BiddingProjectService;
import com.yuanlrc.base.service.admin.DatabaseBakService;
import com.yuanlrc.base.service.home.BiddingApplyService;
import com.yuanlrc.base.service.home.BiddingRecordService;
import com.yuanlrc.base.service.home.BiddingRemindService;
import com.yuanlrc.base.service.home.HomeUserService;
import com.yuanlrc.base.util.SendEmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 备份数据库定时器
 *
 * @author Administrator
 */
@Configuration
@EnableScheduling
public class ProjectSchedule {

    @Value("${ylrc.email.address}")
    private String sourceEmail; //发件人邮箱

    @Value("${ylrc.email.authorization-code}")
    private String authorizationCode; //授权码

    @Autowired
    private BiddingProjectService biddingProjectService;

    @Autowired
    private BiddingRecordService biddingRecordService;

    @Autowired
    private BiddingRemindService biddingRemindService;

    @Autowired
    private HomeUserService homeUserService;


    @Autowired
    private BiddingApplyService biddingApplyService;

    private Logger log = LoggerFactory.getLogger(ProjectSchedule.class);

    // @Scheduled(initialDelay=1000,fixedRate=1000)
    // @Scheduled(cron = "0 */10 * * * ?")
    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    public void projectSchedule() throws Exception {
        List<BiddingProject> biddingProjects = biddingProjectService.findByProjectStatusOrProjectStatus(ProjectStatus.INPUBLIC, ProjectStatus.BIDDING);
        for (BiddingProject biddingProject : biddingProjects) {
            if (biddingProject.getProjectStatus() == ProjectStatus.INPUBLIC || biddingProject.getProjectStatus() == ProjectStatus.BIDDING) {
                Date biddingStartTime = biddingProject.getBiddingStartTime();
                Date biddingEndTime = biddingProject.getBiddingEndTime();
                //竞拍开始时间
                long startTime = biddingStartTime.getTime();
                //竞拍结束时间
                long endTime = biddingEndTime.getTime();
                //当前时间
                long currentTime = System.currentTimeMillis();
                int status = ProjectStatus.INPUBLIC.getCode();
                if (currentTime >= startTime) {
                    //查询该项目设置提醒中未提醒的
                    List<BiddingRemind> biddingReminds = biddingRemindService.findByBiddingProjectIdAndStatus(biddingProject.getId(), RemindType.NOREMIND.getCode());
                    for (BiddingRemind biddingRemind : biddingReminds) {
                        sendEmailUtil(biddingRemind.getHomeUser().getEmail(), biddingProject.getTitle());
                        biddingRemindService.updateStatus(biddingRemind.getId(), RemindType.REMIND.getCode());
                    }
                    if (currentTime <= endTime) {
                        status = ProjectStatus.BIDDING.getCode();
                    } else if (currentTime >= endTime) {
                        List<BiddingRecord> biddingProjectId = biddingRecordService.findByBiddingProjectId(biddingProject.getId());
                        if (biddingProjectId.size() == 0) {
                            //表示没人竞价
                            status = ProjectStatus.ENDBIDDING.getCode();
                            //查询项目所有报名未返还的
                            List<BiddingApply> biddingApplies = biddingApplyService.findByBiddingProjectAndStatus(biddingProject.getId(), EarnestMoneyStatus.NOT_RETURN.getCode());
                            moneyBack(biddingApplies, biddingProject.getBond());

                        } else {
                            status = ProjectStatus.SUCCESSFULBIDDING.getCode();
                            //查询竞拍该项目领先的记录
                            BiddingRecord biddingRecord = biddingRecordService.findByBiddingProjectIdAndBiddingStatus(biddingProject.getId());
                            //查询该项目除了领先的其他人的报名
                            List<BiddingApply> biddingApplies = biddingApplyService.findByBiddingProjectAndHomeUserNot(biddingProject.getId(), biddingRecord.getHomeUser().getId(), EarnestMoneyStatus.NOT_RETURN.getCode());
                            moneyBack(biddingApplies, biddingProject.getBond());
                        }
                    }
                    biddingProjectService.updateStatus(biddingProject.getId(), status);
                }
            }

        }
    }

    public SendEmailUtil sendEmailUtil(String email, String title) throws Exception {
        SendEmailUtil sendEmailUtil = new SendEmailUtil();
        sendEmailUtil.setSourceEmail(sourceEmail);  //设置发件人
        sendEmailUtil.setEmail(email); //设置收件人
        sendEmailUtil.setCode(authorizationCode); //设置授权码
        sendEmailUtil.sendMsg("猿来如此竞拍：你设置提醒的项目:<" + title + ">已开始竞拍。");//发送信息
        return sendEmailUtil;
    }

    /**
     * 退报名金
     */
    public void moneyBack(List<BiddingApply> biddingApplies, int bond) {

        for (BiddingApply biddingApply : biddingApplies) {
            biddingRecordService.moneyBack(biddingApply, bond);
        }
    }
}
