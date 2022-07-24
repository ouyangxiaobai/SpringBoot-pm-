package com.yuanlrc.base.controller.home;

import com.yuanlrc.base.service.admin.CommonProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 常见问题 controller
 * @author zhong
 */
@Controller
@RequestMapping("/home/common_problem")
public class HomeCommonProblemController {

    @Autowired
    private CommonProblemService commonProblemService;

    @GetMapping("/list")
    public String list(Model model)
    {

        model.addAttribute("commonProblemList", commonProblemService.findAll());

        return "home/common_problem/list";
    }
}
