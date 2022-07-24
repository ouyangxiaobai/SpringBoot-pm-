package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.dao.admin.CommonProblemDao;
import com.yuanlrc.base.entity.admin.CommonProblem;
import com.yuanlrc.base.service.admin.CommonProblemService;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 常见问题
 * @author zhong
 */
@Controller
@RequestMapping("/admin/common_problem")
public class CommonProblemController {

    @Autowired
    private CommonProblemService commonProblemService;

    @GetMapping("/list")
    public String list(Model model, CommonProblem commonProblem, PageBean<CommonProblem> pageBean)
    {
        model.addAttribute("title", "常见问题管理");
        model.addAttribute("pageBean", commonProblemService.findList(commonProblem, pageBean));
        model.addAttribute("name", commonProblem.getName());
        return "admin/common_problem/list";
    }

    @GetMapping("/add")
    public String add(Model model)
    {
        return "admin/common_problem/add";
    }

    @GetMapping("/edit")
    public String edit(Model model, Long id)
    {
        model.addAttribute("item", commonProblemService.find(id));
        return "admin/common_problem/edit";
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<Boolean> add(CommonProblem commonProblem)
    {
        CodeMsg codeMsg = ValidateEntityUtil.validate(commonProblem);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        if(commonProblemService.findByName(commonProblem.getName(),-1l))
            return Result.error(CodeMsg.ADMIN_COMMON_PROBLEM_EMPTY_ERROR);


        if(commonProblemService.save(commonProblem) == null)
            return Result.error(CodeMsg.ADMIN_COMMON_PROBLEM_ADD_ERROR);

        return Result.success(true);
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result<Boolean> delete(Long id)
    {
        try
        {
            commonProblemService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_COMMON_PROBLEM_DELETE_ERROR);
        }
        return Result.success(true);
    }

    @PostMapping("/edit")
    @ResponseBody
    public Result<Boolean> edit(CommonProblem commonProblem)
    {
        CodeMsg codeMsg = ValidateEntityUtil.validate(commonProblem);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        if(commonProblemService.findByName(commonProblem.getName(),commonProblem.getId()))
            return Result.error(CodeMsg.ADMIN_COMMON_PROBLEM_EMPTY_ERROR);

        CommonProblem find = commonProblemService.find(commonProblem.getId());
        find.setName(commonProblem.getName());
        find.setAnswer(commonProblem.getAnswer());

        if(commonProblemService.save(find) == null)
            return Result.error(CodeMsg.ADMIN_COMMON_PROBLEM_EDIT_ERROR);

        return Result.success(true);
    }
}
