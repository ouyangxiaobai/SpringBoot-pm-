package com.yuanlrc.base.controller.home;

import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.entity.admin.Inform;
import com.yuanlrc.base.entity.home.News;
import com.yuanlrc.base.service.admin.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 通告
 */
@Controller
@RequestMapping("/home/inform")
public class HomeInformController {

    @Autowired
    private InformService informService;

    //首页列表
    @GetMapping("/list")
    public String list(Model model, String province, String city, String county, PageBean<Inform> pageBean)
    {
        model.addAttribute("pageBean", informService.findHomeList(province, city, county, pageBean));
        model.addAttribute("city", city);
        model.addAttribute("county", county);
        model.addAttribute("province", province);
        return "home/inform/list";
    }

    @GetMapping("/detail")
    public String detail(Model model, Long id)
    {
        model.addAttribute("news", informService.find(id));
        return "home/inform/detail";
    }
}
