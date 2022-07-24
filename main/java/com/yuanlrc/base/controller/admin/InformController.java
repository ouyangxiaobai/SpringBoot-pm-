package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.Inform;
import com.yuanlrc.base.service.admin.InformService;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 通知公告管理
 * @author zhong
 * @date 2021-01-31
 */
@Controller
@RequestMapping("/admin/inform")
public class InformController {

    @Autowired
    private InformService informService;

    @RequestMapping("/list")
    public String list(Model model, Inform inform, PageBean<Inform> pageBean)
    {
        model.addAttribute("title", "通知公告管理");
        model.addAttribute("pageBean", informService.findList(inform, pageBean));
        model.addAttribute("caption", inform.getCaption());
        return "admin/inform/list";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(Long id)
    {
        try
        {
            informService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_INFORM_DELETE_ERROR);
        }
        return Result.success(true);
    }

    @GetMapping("/add")
    public String add()
    {
        return "admin/inform/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<Boolean> add(Inform inform)
    {
        CodeMsg codeMsg = ValidateEntityUtil.validate(inform);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        if(informService.findByCaption(inform.getCaption()) != null)
            return Result.error(CodeMsg.ADMIN_INFORM_CAPTION_ERROR);

        if(informService.save(inform) == null)
            return Result.error(CodeMsg.ADMIN_INFORM_ADD_ERROR);

        return Result.success(true);
    }
}
