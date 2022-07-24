package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.admin.ApprovalRecord;
import com.yuanlrc.base.service.admin.ApprovalRecordService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 审批记录管理控制层
 *
 * @author Administrator
 */
@RequestMapping("/admin/approval_record")
@Controller
public class ApprovalRecordController {

    private Logger logger = LoggerFactory.getLogger(ApprovalRecordController.class);

    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private ApprovalRecordService approvalRecordService;

    /**
     * 审批记录列表
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(ApprovalRecord approvalRecord, PageBean<ApprovalRecord> pageBean, Model model) {
        model.addAttribute("title","审批记录列表");
        model.addAttribute("titles", approvalRecord.getBiddingProject() == null ? "" : approvalRecord.getBiddingProject().getTitle());
        model.addAttribute("pageBean", approvalRecordService.findList(approvalRecord, pageBean));
        return "/admin/approval_record/list";
    }


    /**
     * 删除审批记录操作
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name = "id", required = true) Long id) {
        try {
            approvalRecordService.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_APPROVAL_RECORD_DELETE_ERROR);
        }
        operaterLogService.add("删除审批记录,记录ID：" + id);
        return Result.success(true);
    }


}
