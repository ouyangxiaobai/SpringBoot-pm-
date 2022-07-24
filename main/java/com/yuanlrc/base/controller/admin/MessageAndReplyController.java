package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.MessageAndReply;
import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.service.admin.BiddingProjectService;
import com.yuanlrc.base.service.home.MessageAndReplyService;
import com.yuanlrc.base.service.home.OrganizationService;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 留言管理
 */
@Controller
@RequestMapping("/admin/bidding")
public class MessageAndReplyController {

    @Autowired
    private MessageAndReplyService messageAndReplyService;

    @Autowired
    private BiddingProjectService biddingProjectService;

    /**
     *
     * @param model
     * @param projectId 项目id
     * @return
     */
    @GetMapping("/msg_list")
    public String list(Model model, @RequestParam("id") Long projectId, String msg, PageBean<MessageAndReply> pageBean)
    {
        Organization organization = SessionUtil.getLoginedOrganization();

        //根据ID和项目ID查询
        BiddingProject project = biddingProjectService.find(projectId);

        if(organization == null || project == null)
            return "redirect:/system/index";

        //判断用户ID是否相等
        if(project.getOrganization().getId().longValue() != organization.getId().longValue())
            return "redirect:/system/index";

        pageBean = messageAndReplyService.findListByOrgId(organization.getId(),projectId, pageBean, msg);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("title", "回复管理");
        model.addAttribute("id", projectId);
        model.addAttribute("msg", msg);
        return "admin/message/list";
    }


    /**
     * 回复
     * @param reply
     * @param id
     * @return
     */
    @PostMapping("/reply")
    @ResponseBody
    public Result<Boolean> reply(String reply, Long id)
    {
        MessageAndReply msg = messageAndReplyService.find(id);

        //是否回复过了
        if(msg == null)
            return Result.error(CodeMsg.DATA_ERROR);

        //已经回复过了
        if(!StringUtils.isEmpty(msg.getReply()))
            return Result.error(CodeMsg.ADMIN_REPLY_ERROR);

        if(StringUtils.isEmpty(reply))
            return Result.error(CodeMsg.ADMIN_REPLY_NULL_ERROR);

        //判断长度
        if(reply.length() < 4 || reply.length() > 100)
            return Result.error(CodeMsg.ADMIN_REPLY_LENGTH_ERROR);

        msg.setReply(reply);
        if(messageAndReplyService.save(msg) == null)
            return Result.error(CodeMsg.ADMIN_REPLY_ERROR);

        return Result.success(true);
    }
}
