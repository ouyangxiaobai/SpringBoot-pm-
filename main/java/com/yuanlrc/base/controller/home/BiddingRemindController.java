package com.yuanlrc.base.controller.home;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.ProjectStatus;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.BiddingRemind;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.service.admin.BiddingProjectService;
import com.yuanlrc.base.service.home.BiddingApplyService;
import com.yuanlrc.base.service.home.BiddingRemindService;
import com.yuanlrc.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 提醒Controller
 */
@Controller
@RequestMapping("/home/remind")
public class BiddingRemindController {

    @Autowired
    private BiddingRemindService biddingRemindService;

    @Autowired
    private BiddingProjectService biddingProjectService;

    @Autowired
    private BiddingApplyService biddingApplyService;
    /**
     * 添加提醒
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addRemind(Long projectId){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        BiddingProject biddingProject = biddingProjectService.find(projectId);
        if(biddingProject == null){
            return Result.error(CodeMsg.HOME_REMIND_ADD_ERROR);
        }

        if(biddingApplyService.findByBiddingProjectIdAndHomeUserId(projectId, loginedHomeUser.getId()) == null){
            return Result.error(CodeMsg.NOT_APPLY_ERROR);
        }

        if(biddingProject.getProjectStatus() != ProjectStatus.INPUBLIC){
            return Result.error(CodeMsg.PROJECT_STATUS_ERROR);
        }

        if(biddingRemindService.findByBiddingProjectIdAndHomeUserId(projectId, loginedHomeUser.getId()) != null){
            return Result.error(CodeMsg.HOME_REMIND_EXIST_ADD_ERROR);
        }

        BiddingRemind biddingRemind = new BiddingRemind();
        biddingRemind.setHomeUser(loginedHomeUser);
        biddingRemind.setBiddingProject(biddingProject);
        if(biddingRemindService.save(biddingRemind) == null){
            return Result.error(CodeMsg.HOME_REMIND_ADD_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 取消提醒
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(Long id){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        BiddingRemind biddingRemind = biddingRemindService.find(id);
        if(biddingRemind == null){
            return Result.error(CodeMsg.HOME_REMIND_DELETE_ERROR);
        }
        if(biddingRemind.getHomeUser().getId().longValue() != loginedHomeUser.getId().longValue()){
            return Result.error(CodeMsg.HOME_REMIND_NOT_EXIST_DELETE_ERROR);
        }
        try{
            biddingRemindService.delete(id);
        }catch (Exception e){
            return Result.error(CodeMsg.HOME_REMIND_DELETE_ERROR);
        }
        return Result.success(true);
    }
}
