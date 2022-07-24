package com.yuanlrc.base.controller.home;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.News;
import com.yuanlrc.base.entity.home.NewsCollect;
import com.yuanlrc.base.service.admin.NewsService;
import com.yuanlrc.base.service.common.NewsTypeService;
import com.yuanlrc.base.service.home.NewsCollectService;
import com.yuanlrc.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/home/news")
public class HomeNewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private NewsTypeService newsTypeService;

    @Autowired
    private NewsCollectService newsCollectService;

    @GetMapping("/list")
    public String list(Model model, String province, String city, String county, PageBean<News> pageBean)
    {
        model.addAttribute("pageBean", newsService.findHomeList(province, city, county, pageBean));
        model.addAttribute("province", province);
        model.addAttribute("city", city);
        model.addAttribute("county", county);
        return "home/news/list";
    }

    @GetMapping("/detail")
    public String detail(Model model, Long id)
    {
        HomeUser homeUser = SessionUtil.getLoginedHomeUser();

        model.addAttribute("news", newsService.find(id));
        model.addAttribute("newCollect", homeUser == null ? null : newsCollectService.findByNewsIdAndHomeUserId(id, homeUser.getId()));
        return "home/news/detail";
    }

    /**
     * 添加收藏
     * @param id
     * @return
     */
    @PostMapping("/add_collect")
    @ResponseBody
    public Result<Boolean> add(Long id)
    {
        News news = newsService.find(id);
        if(news == null)
            return Result.error(CodeMsg.HOME_COLLECT_ADD_ERROR);

        HomeUser homeUser = SessionUtil.getLoginedHomeUser();
        if(homeUser == null)
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);

        //判断是否收藏过了 收藏过了
        if(newsCollectService.findByNewsIdAndHomeUserId(id, homeUser.getId()) != null)
            return Result.error(CodeMsg.HOME_COLLECT_EXIST_ADD_ERROR);

        NewsCollect collect = new NewsCollect();
        collect.setNews(news);
        collect.setHomeUser(homeUser);


        if(newsCollectService.save(collect) == null)
            return Result.error(CodeMsg.HOME_COLLECT_ADD_ERROR);

        return Result.success(true);
    }

    /**
     * 取消收藏
     * @param id
     * @return
     */
    @PostMapping("/delete_collect")
    @ResponseBody
    public Result<Boolean> delete(Long id)
    {
        HomeUser homeUser = SessionUtil.getLoginedHomeUser();
        if(homeUser == null)
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);

        //判断有没有这个收藏
        NewsCollect collect = newsCollectService.find(id);
        if(collect == null)
            return Result.error(CodeMsg.HOME_COLLECT_DELETE_ERROR);

        //判断是不是自己的项目
        if(homeUser.getId().longValue() != collect.getHomeUser().getId().longValue())
            return Result.error(CodeMsg.HOME_COLLECT_NOT_EXIST_DELETE_ERROR);

        try
        {
            newsCollectService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.HOME_COLLECT_DELETE_ERROR);
        }

        return Result.success(true);
    }
}
