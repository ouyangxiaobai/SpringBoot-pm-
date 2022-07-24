package com.yuanlrc.base.controller.home;

import com.yuanlrc.base.bean.AuditStatus;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.service.admin.BiddingProjectService;
import com.yuanlrc.base.service.home.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 前台机构
 */
@Controller
@RequestMapping("/home/org")
public class HomeOrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private BiddingProjectService biddingProjectService;

    @GetMapping("/list")
    public String list(Model model, String name, PageBean<Organization> pageBean)
    {
        model.addAttribute("title", "机构列表");
        model.addAttribute("pageBean", organizationService.findListByStatus(name, UserStatus.ACTIVE.getCode(), AuditStatus.PASS.getCode() , pageBean));
        model.addAttribute("name", name);
        return "home/org/list";
    }

    //前台机构展示
    @GetMapping("/detail")
    public String detail(Model model, Long id, PageBean<BiddingProject> pageBean)
    {
        //判断状态
        Organization organization = organizationService.find(id);
        if(organization == null || organization.getAuditStatus() != AuditStatus.PASS.getCode())
            return "redirect:list";

        model.addAttribute("item", organization);
        model.addAttribute("organization", organization);
        model.addAttribute("pageBean", biddingProjectService.findList(organization.getId(),null, pageBean));

        return "home/org/detail";
    }
}
