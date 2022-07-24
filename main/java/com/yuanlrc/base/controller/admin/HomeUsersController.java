package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.bean.WithdrawalStatus;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.UserWithdrawalRecord;
import com.yuanlrc.base.service.home.HomeUserService;
import com.yuanlrc.base.service.home.UserWithdrawalRecordService;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 后台显示前台用户Controller
 */
@RequestMapping("/admin/homeUser")
@Controller
public class HomeUsersController {

    @Autowired
    private HomeUserService homeUserService;

    @Autowired
    private UserWithdrawalRecordService userWithdrawalRecordService;

    /**
     * 前台用户分页查询
     * @param homeUser
     * @param pageBean
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(HomeUser homeUser, PageBean<HomeUser> pageBean, Model model){
        model.addAttribute("title","前台用户列表");
        model.addAttribute("username",homeUser.getUsername());
        model.addAttribute("pageBean",homeUserService.findList(homeUser,pageBean));
        return "admin/home_user/list";
    }

    /**
     * 编辑前台用户页面
     * @param homeUserId
     * @param model
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Long homeUserId,Model model){
        model.addAttribute("homeUser",homeUserService.find(homeUserId));
        return "admin/home_user/edit";
    }

    /**
     * 更改前台用户状态
     * @param homeUser
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(HomeUser homeUser){
        if(homeUserService.updateStatus(homeUser) != 1){
            return Result.error(CodeMsg.UPDATE_STATUS_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 前台用户提现审批列表
     * @return
     */
    @RequestMapping("/wr_list")
    public String wrList(Model model, UserWithdrawalRecord userWithdrawalRecord,PageBean<UserWithdrawalRecord> pageBean){
        model.addAttribute("title","前台用户提现记录");
        model.addAttribute("pageBean",userWithdrawalRecordService.findAll(userWithdrawalRecord,pageBean));
        return "admin/home_user/wr_list";
    }

    /**
     * 审批提现
     * @param id
     * @param flag
     * @param notPassReason
     * @return
     */
    @RequestMapping(value = "/approval",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> approval(Long id,Boolean flag,String notPassReason){
        UserWithdrawalRecord userWithdrawalRecord = userWithdrawalRecordService.findById(id);

        if(userWithdrawalRecord == null){
            return Result.error(CodeMsg.WITHDRAWAL_NO_EXIST);
        }
        if(userWithdrawalRecord.getStatus() != WithdrawalStatus.AUDIT.getCode()){
            return Result.error(CodeMsg.WITHDRAWAL_HAS_APPROVAL);
        }

        if(notPassReason.length() > 128){
            return Result.error(CodeMsg.WITHDRAWAL_NOT_PASS_REASON_LONG_ERROR);
        }

        if(!flag){
            userWithdrawalRecord.setStatus(WithdrawalStatus.NOT_PASS.getCode());
            userWithdrawalRecord.setNotPassReason(notPassReason);
            if(userWithdrawalRecordService.save(userWithdrawalRecord) == null){
                return Result.error(CodeMsg.WITHDRAWAL_APPROVAL_ERROR);
            }
        }else{
            HomeUser homeUser = homeUserService.find(userWithdrawalRecord.getHomeUser().getId());

            if(userWithdrawalRecord.getMoney().compareTo(homeUser.getBalance())>0){
                return Result.error(CodeMsg.WITHDRAWAL_MONEY_MORE_BALANCE);
            }

            userWithdrawalRecord.setStatus(WithdrawalStatus.PASS.getCode());

            if(userWithdrawalRecordService.approval(userWithdrawalRecord,homeUser) == null){
                return Result.error(CodeMsg.WITHDRAWAL_APPROVAL_ERROR);
            }
        }

        return Result.success(true);
    }


}
