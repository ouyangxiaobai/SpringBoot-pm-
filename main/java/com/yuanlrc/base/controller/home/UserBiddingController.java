package com.yuanlrc.base.controller.home;

import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.*;
import com.yuanlrc.base.service.home.BiddingApplyService;
import com.yuanlrc.base.service.home.BiddingRecordService;
import com.yuanlrc.base.service.home.HomeUserService;
import com.yuanlrc.base.service.home.MessageAndReplyService;
import com.yuanlrc.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * 前台用户项目相关Controller
 */
@Controller
@RequestMapping("/home/bidding")
public class UserBiddingController {

    @Autowired
    private BiddingApplyService biddingApplyService;

    @Autowired
    private BiddingRecordService biddingRecordService;

    @Autowired
    private HomeUserService homeUserService;

    @Autowired
    private MessageAndReplyService messageAndReplyService;

    /**
     * 前台用户报名项目列表
     * @return
     */
    @RequestMapping("/apply")
    public String biddingApply(Model model, ProjectTimeVO projectTimeVO, PageBean<BiddingApply> pageBean){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return "redirect:/home/user/login";
        }
        model.addAttribute("biddingStatusList", UserBiddingStatus.values());
        model.addAttribute("projectTimeVO",projectTimeVO);
        model.addAttribute("pageBean",biddingApplyService.findList(loginedHomeUser.getId(),projectTimeVO,pageBean));
        return "home/home_user/bidding_apply";
    }

    /**
     * 前台用户项目竞价列表
     * @return
     */
    @RequestMapping("/record")
    public String biddingRecord(Model model, ProjectTimeVO projectTimeVO, PageBean<BiddingRecord> pageBean){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return "redirect:/home/user/login";
        }
        model.addAttribute("biddingStatusList", UserBiddingStatus.values());
        model.addAttribute("projectTimeVO",projectTimeVO);
        model.addAttribute("pageBean",biddingRecordService.findList(loginedHomeUser.getId(),projectTimeVO,pageBean, BiddingStatus.LEADING.getCode()));
        model.addAttribute("successStatus",ProjectStatus.SUCCESSFULBIDDING.getCode());
        return "home/home_user/bidding_record";
    }

    /**
     * 留言表
     * @param model
     * @param title
     * @param pageBean
     * @return
     */
    @RequestMapping("/message")
    public String message(Model model, String title, PageBean<MessageAndReply> pageBean){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return "redirect:/home/user/login";
        }
        model.addAttribute("title",title);
        model.addAttribute("pageBean",messageAndReplyService.findList(loginedHomeUser.getId(),pageBean,title ==null ? "" : title));
        return "home/home_user/message";
    }

    /**
     * 中标项目确认支付
     * @param biddingRecordId
     * @return
     */
    @RequestMapping(value = "/payMoney",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> payMoney(@RequestParam("biddingRecordId") Long biddingRecordId){
        //是否登录
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        //是否存在
        BiddingRecord biddingRecord = biddingRecordService.find(biddingRecordId);
        if(biddingRecord == null){
            return Result.error(CodeMsg.DATA_ERROR);
        }

        //是否是竞价成功状态
        if(biddingRecord.getBiddingProject().getProjectStatus().getCode() != ProjectStatus.SUCCESSFULBIDDING.getCode()){
            return Result.error(CodeMsg.BIDDING_PROJECT_STATUS_ERROR);
        }

        //是否是领先状态
        if(biddingRecord.getBiddingStatus() != BiddingStatus.LEADING.getCode()){
            return Result.error(CodeMsg.BIDDING_STATUS_ERROR);
        }

        //是否是该用户的
        if(biddingRecord.getHomeUser().getId().longValue() != loginedHomeUser.getId().longValue()){
            return Result.error(CodeMsg.RECORD_HOME_USER_ERROR);
        }

        //是否已支付
        if(biddingRecord.getPayStatus() == BiddingRecord.STATUS_YES){
            return Result.error(CodeMsg.BIDDING_PAY_STATUS_ERROR);
        }

        //是否已逾期
        if(biddingRecord.getOverdueStatus() == BiddingRecord.STATUS_YES){
            return Result.error(CodeMsg.BIDDING_OVERDUE_STATUS_ERROR);
        }

        //用户余额是否足够
        HomeUser homeUser = homeUserService.find(loginedHomeUser.getId());
        if(homeUser.getBalance().compareTo(new BigDecimal(biddingRecord.getBid())) < 0){
            return Result.error(CodeMsg.BALANCE_LESS_ERROR);
        }

        if(biddingRecordService.payMoney(biddingRecord) == null){
            return Result.error(CodeMsg.PAY_MONEY_ERROR);
        }

        return Result.success(true);
    }
}
