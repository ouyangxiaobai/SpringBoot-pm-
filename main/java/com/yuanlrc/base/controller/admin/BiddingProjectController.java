package com.yuanlrc.base.controller.admin;

import com.alipay.api.domain.CommonDescInfo;
import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.admin.ApprovalRecord;
import com.yuanlrc.base.entity.admin.User;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.BiddingApply;
import com.yuanlrc.base.entity.home.BiddingRecord;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.service.admin.ApprovalRecordService;
import com.yuanlrc.base.service.admin.BiddingProjectService;
import com.yuanlrc.base.service.admin.LabelTypeService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.service.home.BiddingApplyService;
import com.yuanlrc.base.service.home.BiddingRecordService;
import com.yuanlrc.base.service.home.BiddingRemindService;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.apache.catalina.manager.util.SessionUtils;
import org.aspectj.apache.bcel.classfile.Code;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 竞拍管理控制层
 *
 * @author Administrator
 */
@RequestMapping("/admin/bidding")
@Controller
public class BiddingProjectController {

    private Logger logger = LoggerFactory.getLogger(BiddingProjectController.class);

    @Autowired
    private BiddingProjectService biddingProjectService;

    @Autowired
    private OperaterLogService operaterLogService;

    @Autowired
    private LabelTypeService labelTypeService;

    @Autowired
    private ApprovalRecordService approvalRecordService;

    @Autowired
    private BiddingApplyService biddingApplyService;

    @Autowired
    private BiddingRecordService biddingRecordService;



    /**
     * 竞拍列表
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(BiddingProject biddingProject, PageBean<BiddingProject> pageBean, Model model) {
        Organization loginedOrganization = SessionUtil.getLoginedOrganization();
        if (loginedOrganization == null) {
            model.addAttribute("msg", "不要搞破坏哦，乖!");
            return "/error/404";
        }
        biddingProject.setOrganization(loginedOrganization);
        model.addAttribute("pageBean", biddingProjectService.findList(biddingProject, pageBean));
        model.addAttribute("title", "竞拍列表");
        model.addAttribute("titles", biddingProject.getTitle() == null ? "" : biddingProject.getTitle());
        return "admin/bidding_project/list";
    }
    /**
     * 竞拍项目添加页面
     * @param model
     * @return
     */
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("labelTypeList", labelTypeService.findByStatus(UserStatus.ACTIVE.getCode()));
        model.addAttribute("cycleTypeList", CycleType.values());
        return "/admin/bidding_project/add";
    }
    /**
     * 竞拍添加操作
     *
     * @param biddingProject
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public Result<Boolean> add(BiddingProject biddingProject) {
        Organization loginedOrganization = SessionUtil.getLoginedOrganization();
        if (loginedOrganization == null) {
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(biddingProject);
        if (validate.getCode() != CodeMsg.SUCCESS.getCode()) {
            return Result.error(validate);
        }
        //报名日期比较
        if (!biddingProject.getStartTime().before(biddingProject.getEndTime())) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_SIGNUP_DATE_ERROR);
        }
        //竞拍日期比较
        if (!biddingProject.getBiddingStartTime().before(biddingProject.getBiddingEndTime())) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_BIDDING_DATE_ERROR);
        }
        String projectNumber = StringUtil.gneerateSn("xm");
        biddingProject.setProjectNumber(projectNumber);
        biddingProject.setOrganization(loginedOrganization);
        biddingProject.setCurrentPrice(biddingProject.getStartPrice());
        //保存竞拍
        if (biddingProjectService.save(biddingProject) == null) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_ADD_ERROR);
        }
        operaterLogService.add("添加竞拍，标题名称：" + biddingProject.getTitle());
        return Result.success(true);
    }

    /**
     * 竞拍编辑页面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit")
    public String edit(@RequestParam("id") Long id, Model model) {
        BiddingProject biddingProject = biddingProjectService.find(id);
        if (biddingProject == null) {
            model.addAttribute("msg", "不要搞破坏哦，乖!");
            return "/error/404";
        }
        model.addAttribute("biddingProject", biddingProject);
        model.addAttribute("labelTypeList", labelTypeService.findByStatus(UserStatus.ACTIVE.getCode()));
        model.addAttribute("cycleTypeList", CycleType.values());
        return "/admin/bidding_project/edit";
    }

    /**
     * 竞拍编辑操作
     *
     * @param biddingProject
     * @return
     */
    @ResponseBody
    @PostMapping("/edit")
    public Result<Boolean> edit(BiddingProject biddingProject) {
        Organization loginedOrganization = SessionUtil.getLoginedOrganization();
        if (loginedOrganization == null) {
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(biddingProject);
        if (validate.getCode() != CodeMsg.SUCCESS.getCode()) {
            return Result.error(validate);
        }
        if (biddingProject.getId() == null || biddingProject.getId() <= 0) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_NOT_EXIST_ERROR);
        }
        //报名时间和当前时间比较
        if(biddingProject.getStartTime().before(new Date())){
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_SIGNUP_TIME_ERROR);
        }
        //报名日期比较
        if (!biddingProject.getStartTime().before(biddingProject.getEndTime())) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_SIGNUP_DATE_ERROR);
        }
        //竞拍日期比较
        if (!biddingProject.getBiddingStartTime().before(biddingProject.getBiddingEndTime())) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_BIDDING_DATE_ERROR);
        }
        BiddingProject biddingProject1 = biddingProjectService.find(biddingProject.getId());
        if (biddingProject1 == null) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_NOT_EXIST_ERROR);
        }
        ProjectStatus projectStatus = biddingProject1.getProjectStatus();
        if(projectStatus!=ProjectStatus.NOTSUBMIT && projectStatus!=ProjectStatus.SHELF && projectStatus!=ProjectStatus.PASSAUDIT){
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_STAUTS_ERROR);
        }
        BeanUtils.copyProperties(biddingProject, biddingProject1, "id", "createTime", "updateTime", "projectNumber", "organization", "currentPrice");
        if (biddingProjectService.save(biddingProject1) == null) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_EDIT_ERROR);
        }
        operaterLogService.add("编辑竞拍，标题名称：" + biddingProject.getTitle());
        return Result.success(true);
    }

    /**
     * 删除竞拍操作
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(@RequestParam(name = "id", required = true) Long id) {
        try {
            biddingProjectService.delete(id);
        } catch (Exception e) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_DELETE_ERROR);
        }
        operaterLogService.add("删除竞拍,竞拍ID：" + id);
        return Result.success(true);
    }

    /**
     * 竞拍发布
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/update_status")
    public Result<Boolean> updateStatus(@RequestParam("id") Long id) {
        //机构
        Organization organization = SessionUtil.getLoginedOrganization();
        //判断状态是否已审核
        if(!organization.getAuditStatus().equals(AuditStatus.PASS.getCode())){
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_AUDITSTATUS_ERROR);
        }
        //判断保证金是不是已交
        if(organization.getEarnestMoney().equals(Organization.NOT_PAY)){
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_EARNESTMONEY_STATUS_ERROR);
        }
        BiddingProject biddingProject = biddingProjectService.find(id);
        if (biddingProject == null) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_NOT_EXIST_ERROR);
        }
        //更新项目状态 更新为待审核
        if (biddingProjectService.updateStatus(id, ProjectStatus.REVIEWED.getCode()) <= 0) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_RELEASE_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 项目审核列表
     * @param model
     * @return
     */
    @GetMapping("/project_audit")
    public String projectAudit(BiddingProject biddingProject,PageBean<BiddingProject> pageBean,Model model){
        biddingProject.setProjectStatus(ProjectStatus.REVIEWED);
        model.addAttribute("pageBean", biddingProjectService.findList(biddingProject, pageBean));
        model.addAttribute("title", "竞拍审核");
        model.addAttribute("titles", biddingProject.getTitle() == null ? "" : biddingProject.getTitle());
        return "/admin/bidding_project/project_audit";
    }

    /**
     * 查看项目详情页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/project_detail")
    public String projectDetail(@RequestParam("id")Long id,Model model){
        BiddingProject biddingProject = biddingProjectService.find(id);
        if (biddingProject == null) {
            model.addAttribute("msg", "不要搞破坏哦，乖!");
            return "/error/404";
        }
        model.addAttribute("biddingProject", biddingProject);
        model.addAttribute("labelTypeList", labelTypeService.findByStatus(UserStatus.ACTIVE.getCode()));
        model.addAttribute("cycleTypeList", CycleType.values());
        return "/admin/bidding_project/project_detail";
    }

    /**
     * 管理员项目审核
     * @param type
     * @param approvalRecord
     * @return
     */
    @ResponseBody
    @PostMapping("/examine")
    public Result<Boolean> approved(@RequestParam("type")String type,ApprovalRecord approvalRecord){
        User loginedUser = SessionUtil.getLoginedUser();
        if(loginedUser==null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        Long id = approvalRecord.getBiddingProject().getId();
        BiddingProject biddingProject = biddingProjectService.find(id);
        if ( biddingProject== null) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_NOT_EXIST_ERROR);
        }
            //表示同意
        if(type.equals("true")){
            if(!biddingProject.getStartTime().before(new Date())){
                approvalRecord.setProjectStatus(ProjectStatus.INPUBLIC);
            }else{
                return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_SIN_TIME_ERROR);
            }
        }else if(type.equals("false")){
            //表示拒绝
            approvalRecord.setProjectStatus(ProjectStatus.PASSAUDIT);
        }
        //更新项目状态
        if (biddingProjectService.updateApproval(approvalRecord) <= 0) {
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_EXAMINE_ERROR);
        }
        approvalRecord.setUser(loginedUser);
        //保存审批记录
        if(approvalRecordService.save(approvalRecord)==null){
            return Result.error(CodeMsg.ADMIN_APPROVAL_RECORD_ADD_ERROR);
        }
        return Result.success(true);
    }

    /**
     * 交易中心列表
     * @param biddingProject
     * @param pageBean
     * @param model
     * @return
     */
    @GetMapping("/trading_center")
    public String findTradingCenter(BiddingProject biddingProject,PageBean<BiddingProject> pageBean,Model model){
        model.addAttribute("pageBean", biddingProjectService.findList(biddingProject, pageBean));
        model.addAttribute("titles", biddingProject.getTitle() == null ? "" : biddingProject.getTitle());
        return "/admin/bidding_project/trading_center";
    }

    /**
     * 上下架
     * @param type
     * @return
     */
    @PostMapping("/frame")
    @ResponseBody
    public Result<Boolean> loadAndUnload(@RequestParam("type")String type,@RequestParam("id") Long id){
        List<BiddingApply> biddingApplies = biddingApplyService.findByBiddingProjectId(id);
        if(biddingApplies.size()!=0){
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_APPLY_ERROR);
        }
        BiddingProject biddingProject = biddingProjectService.find(id);
        if(biddingProject==null){
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_NOT_EXIST_ERROR);
        }
        if("shelf".equals(type)){
            //下架
            if(biddingProjectService.updateStatus(id,ProjectStatus.SHELF.getCode())<=0){
                return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_UNLOADING_ERROR);
            }
        }
        return Result.success(true);
    }

    /**
     * 设置逾期
     * @param id
     * @return
     */
    @PostMapping("/set_overdue")
    @ResponseBody
    public Result<Boolean> setOverdue(Long id)
    {
        Organization organization = SessionUtil.getLoginedOrganization();
        if(organization == null)
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);

        BiddingProject biddingProject = biddingProjectService.find(id);

        if(biddingProject.getProjectStatus().getCode() != ProjectStatus.SUCCESSFULBIDDING.getCode())
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_PROJECT_STATUS_ERROR);

        //查出已经拍到的人
        BiddingRecord biddingRecord = biddingRecordService.findByBiddingProjectIdAndBiddingStatus(id);
        if(biddingRecord == null)
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_SET_OVERDUE_ERROR);
        //判断是否在七天之后
        Calendar cal = Calendar.getInstance();
        Date biddingEndTime = biddingProject.getBiddingEndTime();
        cal.setTime(biddingEndTime);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        if(new Date().getTime() < cal.getTime().getTime())
            return Result.error(CodeMsg.ADMIN_SET_OVERDUE_DATE_TIME_ERROR);
        HomeUser homeUser = biddingRecord.getHomeUser();
        //把钱给机构
        BiddingApply biddingApply = biddingApplyService.findByBiddingProjectIdAndHomeUserId(id, homeUser.getId());
        if(biddingApply == null)
            return Result.error(CodeMsg.ADMIN_BIDDING_PROJECT_SET_OVERDUE_ERROR);
        //报名费
        int earnestMoney = biddingApply.getEarnestMoney();
        biddingRecordService.save(biddingRecord, earnestMoney, biddingProject, biddingApply, organization);
        return Result.success(true);
    }
}
