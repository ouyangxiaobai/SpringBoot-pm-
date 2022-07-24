package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.common.LabelType;
import com.yuanlrc.base.service.admin.BiddingProjectService;
import com.yuanlrc.base.service.admin.LabelTypeService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 标管理控制层
 *
 * @author Administrator
 */
@RequestMapping("/admin/label_type")
@Controller
public class LabelTypeController {

    private Logger logger=LoggerFactory.getLogger(LabelTypeController.class);

    @Autowired
    private LabelTypeService labelTypeService;

    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private BiddingProjectService biddingProjectService;

    /**
     * 标的物类型列表
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(LabelType labelType, PageBean<LabelType> pageBean, Model model) {
        model.addAttribute("pageBean", labelTypeService.findList(labelType, pageBean));
        model.addAttribute("title","标的物类型列表");
        model.addAttribute("name",labelType.getName()==null?"":labelType.getName());
        return "/admin/label_type/list";
    }

    /**
     * 标的物类型添加页面
     *
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String add(Model model) {
        return "/admin/label_type/add";
    }

    /**
     * 标的物类型添加操作
     * @param labelType
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public Result<Boolean> add(LabelType labelType){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(labelType);
        if(validate.getCode()!=CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        //判断标类型名称是否已存在
        if(labelTypeService.findByName(labelType.getName(),-1l)){
            return Result.error(CodeMsg.ADMIN_LABEL_TYPE_NAME_EXIST);
        }
        //保存标类型
        if(labelTypeService.save(labelType)==null){
            return Result.error(CodeMsg.ADMIN_LABEL_TYPE_ADD_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 标类型编辑页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id,Model model){
        LabelType labelType = labelTypeService.find(id);
        if(labelType==null){
            model.addAttribute("msg","不要搞破坏哦，乖!");
            return "/error/404";
        }
        model.addAttribute("labelType",labelType);
        return "/admin/label_type/edit";
    }

    /**
     * 标类型编辑操作
     * @param labelType
     * @return
     */
    @ResponseBody
    @PostMapping("/edit")
    public Result<Boolean> edit(LabelType labelType){
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(labelType);
        if(validate.getCode()!=CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        if(labelType.getId()==null || labelType.getId()<=0){
            return Result.error(CodeMsg.ADMIN_LABEL_TYPE_NOT_EXIST);
        }
        if(biddingProjectService.findByLableType(labelType.getId()).size() != 0){
            return Result.error(CodeMsg.ADMIN_LABEL_TYPE_BE_USED);
        }
        if(labelTypeService.findByName(labelType.getName(),labelType.getId())){
            return Result.error(CodeMsg.ADMIN_LABEL_TYPE_NAME_EXIST);
        }
        LabelType labelType1 = labelTypeService.find(labelType.getId());
        if(labelType1==null){
            return Result.error(CodeMsg.ADMIN_LABEL_TYPE_NOT_EXIST);
        }
        BeanUtils.copyProperties(labelType,labelType1,"id","createTime","updateTime");
        if(labelTypeService.save(labelType1)==null){
            return Result.error(CodeMsg.ADMIN_LABEL_TYPE_EDIT_ERROR);
        }
        operaterLogService.add("编辑标的物类型，名称：" + labelType.getName());
        return Result.success(true);
    }

    /**
     * 删除标的物类型
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name="id",required=true)Long id){
        try {
            labelTypeService.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_LABEL_TYPE_DELETE_ERROR);
        }
        operaterLogService.add("删除标的物类型,物类型ID：" + id);
        return Result.success(true);
    }
}
