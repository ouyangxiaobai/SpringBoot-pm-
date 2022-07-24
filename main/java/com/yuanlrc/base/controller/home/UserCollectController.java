package com.yuanlrc.base.controller.home;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.BiddingCollect;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.NewsCollect;
import com.yuanlrc.base.service.admin.BiddingProjectService;
import com.yuanlrc.base.service.home.BiddingCollectService;
import com.yuanlrc.base.service.home.NewsCollectService;
import com.yuanlrc.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户收藏Controller
 */
@Controller
@RequestMapping("/home/collect")
public class UserCollectController {

    @Autowired
    private NewsCollectService newsCollectService;

    @Autowired
    private BiddingCollectService biddingCollectService;

    @Autowired
    private BiddingProjectService biddingProjectService;
    /**
     * 查看收藏的项目列表
     * @param model
     * @param pageBean
     * @return
     */
    @RequestMapping("/bidding")
    public String bidding(Model model, PageBean<BiddingCollect> pageBean){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return "redirect:/home/user/login";
        }
        model.addAttribute("pageBean",biddingCollectService.findList(loginedHomeUser.getId(),pageBean));
        return "home/home_user/bidding_collect";
    }

    /**
     * 查询收藏的新闻列表
     * @param model
     * @param pageBean
     * @return
     */
    @RequestMapping("/news")
    public String news(Model model, PageBean<NewsCollect> pageBean){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return "redirect:/home/user/login";
        }
        model.addAttribute("pageBean",newsCollectService.findList(loginedHomeUser.getId(),pageBean));
        return "home/home_user/news_collect";
    }

    /**
     * 竞拍收藏
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/add_project",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addProject(Long projectId){
        BiddingProject biddingProject = biddingProjectService.find(projectId);
        if(biddingProject == null){
            return Result.error(CodeMsg.HOME_COLLECT_ADD_ERROR);
        }
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }

        if(biddingCollectService.findByBiddingProjectIdAndHomeUserId(projectId,loginedHomeUser.getId()) != null){
            return Result.error(CodeMsg.HOME_COLLECT_EXIST_ADD_ERROR);
        }

        BiddingCollect biddingCollect = new BiddingCollect();
        biddingCollect.setBiddingProject(biddingProject);
        biddingCollect.setHomeUser(loginedHomeUser);

        if(biddingCollectService.save(biddingCollect) == null){
            return Result.error(CodeMsg.HOME_COLLECT_ADD_ERROR);
        }

        return Result.success(true);
    }

    /**
     * 取消竞拍收藏
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete_project",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> deleteProject(Long id){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        BiddingCollect biddingCollect = biddingCollectService.find(id);
        if(biddingCollect == null){
            return Result.error(CodeMsg.HOME_COLLECT_DELETE_ERROR);
        }
        if(biddingCollect.getHomeUser().getId().longValue() != loginedHomeUser.getId().longValue()){
            return Result.error(CodeMsg.HOME_COLLECT_NOT_EXIST_DELETE_ERROR);
        }

        try
        {
            biddingCollectService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.HOME_COLLECT_DELETE_ERROR);
        }

        return Result.success(true);
    }


}
