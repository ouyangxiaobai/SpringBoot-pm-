package com.yuanlrc.base.controller.admin;


import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.constant.SessionConstant;
import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.service.home.OrganizationService;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 机构管理
 * @author zhong
 */
@Controller
@RequestMapping("/admin/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;


    @RequestMapping("/list")
    public String list(Model model, Organization organization, PageBean<Organization> pageBean)
    {
        model.addAttribute("title", "机构管理");
        model.addAttribute("pageBean", organizationService.findList(organization, pageBean));
        model.addAttribute("name", organization.getName());
        return "admin/organization/list";
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> delete(Long id)
    {
        try
        {
            organizationService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_DELETE_ERROR);
        }
        return Result.success(true);
    }

    @RequestMapping("/edit")
    public String edit(Model model, Long id)
    {
        model.addAttribute("item", organizationService.find(id));

        return "admin/organization/edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> edit(Organization organization)
    {
        Organization find = organizationService.find(organization.getId());

        if(organization.getAuditStatus() == find.getAuditStatus())
            return Result.success(true);

        if(organization.getAuditStatus() == AuditStatus.FREEZE.getCode())
        {
            if(find.getAuditStatus() != AuditStatus.PASS.getCode())
                return Result.error(CodeMsg.ADMIN_EDIT_AUDIT_STATUS_ERROR);

            //设置冻结
            find.setEarnestMoney(Organization.NOT_PAY);

        }
        else if (organization.getAuditStatus() == AuditStatus.PASS.getCode())
        {
            //设置可用
            if(find.getAuditStatus() != AuditStatus.FREEZE.getCode())
                return Result.error(CodeMsg.ADMIN_EDIT_AUDIT_STATUS_ERROR2);
        }
        else
        {
            return Result.success(true);
        }

        find.setAuditStatus(organization.getAuditStatus());

        if(organizationService.save(find) == null)
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_EDIT_ERROR);

        return Result.success(true);
    }

    /**
     * 忘记密码
     * @return
     */
    @PostMapping("/modification_password")
    @ResponseBody
    public Result<Boolean> modificationPassword(String password, String email, String code)
    {

        //请输入邮箱
        if(StringUtils.isEmpty(email))
            return Result.error(CodeMsg.ADMIN_EMAIL_NULL_ERROR);

        //请输入密码
        if(StringUtils.isEmpty(password))
            return Result.error(CodeMsg.ADMIN_PASSWORD_NULL_ERROR);

        //请输入验证码
        if(StringUtils.isEmpty(code))
            return Result.error(CodeMsg.ADMIN_CODE_NULL_ERROR);

        //邮箱是否存在数据库
        Organization find = organizationService.findByEmail(email);
        if(find == null)
            return Result.error(CodeMsg.ADMIN_EMAIL_NOT_FOUND_ERROR);

        //判断是否已发送验证码
        Object attribute = SessionUtil.get(SessionConstant.SESSION_UPDATE_ORGANIZATION_CODE);
        String sessionEmail = (String)SessionUtil.get("email");
        if(attribute == null)
            return Result.error(CodeMsg.ADMIN_ATTRIBUTE_NOT_SEND);

        //判断邮箱
        if(!StringUtil.emailFormat(email))
            return Result.error(CodeMsg.COMMON_EMAIL_FORMAET_ERROR);

        //判断邮箱是否改动
        if(!sessionEmail.trim().equals(email))
            return Result.error(CodeMsg.ADMIN_EMAIL_UPDATE_ERROR);

        //日期比较
        Date datetime = (Date) SessionUtil.get("sendTime");

        //验证码是否超过六十秒
        if((datetime.getTime() + (60 * 1000)) < new Date().getTime())
        {
            //不要忘记清空
            SessionUtil.setRegisterSession(SessionConstant.SESSION_UPDATE_ORGANIZATION_CODE);

            return Result.error(CodeMsg.SESSION_CODE_OVER_TIME_ERROR);
        }

        //验证码是否相同
        if (!code.equalsIgnoreCase(attribute.toString()))
            return Result.error(CodeMsg.CPACHA_ERROR);

        //密码验证
        find.setPassword(password);
        CodeMsg codeMsg = ValidateEntityUtil.validate(find);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        if(organizationService.save(find) == null)
            return Result.error(CodeMsg.ADMIN_MODIFICATION_PASSWORD_ERROR);

        //清空验证码
        SessionUtil.setRegisterSession(SessionConstant.SESSION_UPDATE_ORGANIZATION_CODE);

        return Result.success(true);
    }

    /**
     * 机构信息
     * @param model
     * @return
     */
    @RequestMapping("/info")
    public String info(Model model)
    {
        Organization login = SessionUtil.getLoginedOrganization();
        login.cardImgToImages();

        Organization find = organizationService.find(login.getId());
        find.setRole(login.getRole());
        SessionUtil.set(SessionConstant.SESSION_USER_ORGANIZATION, find);


        model.addAttribute("item", login);
        model.addAttribute("title", "我的机构信息");

        return "admin/organization/info";
    }

    /**
     * 修改机构信息
     * @param organization
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> info(Organization organization)
    {
        Organization loginde = SessionUtil.getLoginedOrganization();
        Organization find = organizationService.find(loginde.getId());
        find.setHeadPic(organization.getHeadPic());

        CodeMsg codeMsg = ValidateEntityUtil.validate(find);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        if(organizationService.save(find) == null)
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_INFO_ERROR);

        loginde.setHeadPic(find.getHeadPic());
        SessionUtil.set(SessionConstant.SESSION_USER_ORGANIZATION, loginde);
        return Result.success(true);
    }


    /**
     * 支付押金
     * @return
     */
    @RequestMapping(value = "/pay_cash_pledge", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> payCashPledge()
    {
        Organization login = SessionUtil.getLoginedOrganization();
        if(login == null)
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);

        Organization find = organizationService.find(login.getId());
        if(!organizationService.isOrganizationOperation(find))
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_AUDIT_STATUS_ERROR);

        if(find.getEarnestMoney() == Organization.PAY)
            return Result.error(CodeMsg.ADMIN_ORG_PAY_ERROR);

        BigDecimal balance = find.getBalance();

        //押金
        BigDecimal cashPledge = new BigDecimal(100000);

        //判断余额够不够
        if(balance.compareTo(cashPledge) < 0)
            return Result.error(CodeMsg.ADMIN_BALANCE_ERROR);


        find.setEarnestMoney(Organization.PAY);
        find.setBalance(balance.subtract(cashPledge));

        if(organizationService.save(find) == null)
            return Result.error(CodeMsg.ADMIN_PAY_CASH_PLEDGE_ERROR);

        login.setBalance(find.getBalance());
        login.setEarnestMoney(Organization.PAY);

        SessionUtil.set(SessionConstant.SESSION_USER_ORGANIZATION, login);
        return Result.success(true);
    }

    @GetMapping("/register")
    public String register(Model model)
    {
        return "admin/organization/register";
    }


    @PostMapping("/register")
    @ResponseBody
    public Result<Boolean> register(Organization organization, String code)throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        organization.setBalance(new BigDecimal(100000));
        organization.setRole(organizationService.findOrganizationRole());
        CodeMsg codeMsg = ValidateEntityUtil.validate(organization);

        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        //判断验证码
        if(StringUtils.isEmpty(code))
            return Result.error(CodeMsg.CPACHA_EMPTY);

        //验证邮箱
        if(!StringUtil.emailFormat(organization.getEmail()))
            return Result.error(CodeMsg.COMMON_EMAIL_FORMAET_ERROR);

        //验证手机号
        if(!StringUtil.isMobile(organization.getPhone()))
            return Result.error(CodeMsg.COMMON_PHONE_FORMAET_ERROR);

        //判断手机号是否注册过和邮箱
        if(organizationService.findByEmail(organization.getEmail()) != null)
            return Result.error(CodeMsg.COMMON_EMAIL_EXSITER_ERROR);

        if(organizationService.findByPhone(organization.getPhone()) != null)
            return Result.error(CodeMsg.COMMON_PHONE_EXSITER_ERROR);

        //判断机构名称
        if(organizationService.findByName(organization.getName()) != null)
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_NAME_ERROR);

        //是否发送过验证码
        Object attribute = SessionUtil.get(SessionConstant.SESSION_REGISTER_ORGANIZATION);
        if(attribute == null)
            return Result.error(CodeMsg.ADMIN_ATTRIBUTE_NOT_SEND);

        //获取邮箱
        String verCode = attribute.toString();
        String email = (String) SessionUtil.get("email");

        //日期比较
        Date datetime = (Date) SessionUtil.get("sendTime");

        //验证码是否超过六十秒
        if((datetime.getTime() + (60 * 1000)) < new Date().getTime())
        {
            //不要忘记清空
            SessionUtil.setRegisterSession(SessionConstant.SESSION_REGISTER_ORGANIZATION);

            return Result.error(CodeMsg.SESSION_CODE_OVER_TIME_ERROR);
        }

        //验证码验证
        if(!code.equalsIgnoreCase(verCode))
        {
            return Result.error(CodeMsg.CPACHA_ERROR);
        }

        //邮箱对比
        if(!organization.getEmail().trim().equals(email))
        {
            return Result.error(CodeMsg.ADMIN_REGISTER_EMAIL_ERROR);
        }

        if(organizationService.save(organization) == null)
        {
            return Result.error(CodeMsg.ADMIN_REGISTER_ERROR);
        }

        //不要忘记清空
        SessionUtil.setRegisterSession(SessionConstant.SESSION_REGISTER_ORGANIZATION);

        return Result.success(true);
    }

    @GetMapping("/audit_list")
    public String auditList(Model model)
    {
        Organization login = SessionUtil.getLoginedOrganization();
        login.cardImgToImages();

        Organization find = organizationService.find(login.getId());
        find.setRole(login.getRole());
        SessionUtil.set(SessionConstant.SESSION_USER_ORGANIZATION, find);

        model.addAttribute("item", login);
        model.addAttribute("title", "我的机构信息");
        return "admin/organization/audit_list";
    }

    @GetMapping("/audit_edit")
    public String auditEdit(Model model)
    {
        Organization login = SessionUtil.getLoginedOrganization();
        login.cardImgToImages();
        if(!organizationService.isOrganizationEdit(login))
            return "redirect:/system/index";


        model.addAttribute("item", login);
        return "admin/organization/audit_edit";
    }

    /**
     * 编辑信息
     * @param organization
     * @param code
     * @return
     * @throws Exception
     */
    @PostMapping("/audit_edit")
    @ResponseBody
    public Result<Boolean> auditEdit(Organization organization, String code)throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Organization login = SessionUtil.getLoginedOrganization();
        if(login == null)
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        Organization find = organizationService.find(login.getId());

        find.setTradingImg(organization.getTradingImg());
        find.setCardImg(organization.getCardImg());
        find.setEmail(organization.getEmail());
        find.setName(organization.getName());
        find.setAddress(organization.getAddress());
        find.setLegalPerson(organization.getLegalPerson());

        CodeMsg codeMsg = ValidateEntityUtil.validate(find);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        //判断验证码
        if(StringUtils.isEmpty(code))
            return Result.error(CodeMsg.CPACHA_EMPTY);

        //判断是否编辑
        if(!organizationService.isOrganizationEdit(login))
            return Result.error(CodeMsg.ADMIN_AUDIT_EDIT_ERROR);

        //验证邮箱
        if(!StringUtil.emailFormat(organization.getEmail()))
            return Result.error(CodeMsg.COMMON_EMAIL_FORMAET_ERROR);

        //比较机构名称
        Organization findByName = organizationService.findByName(organization.getName());
        if(findByName != null && findByName.getId().longValue() != login.getId().longValue())
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_NAME_ERROR);

        //判断手机号是否注册过和邮箱
        Organization findByEmail = organizationService.findByEmail(organization.getEmail());
        if(findByEmail != null && findByEmail.getId().longValue() != login.getId().longValue())
            return Result.error(CodeMsg.COMMON_EMAIL_EXSITER_ERROR);

        //是否发送过验证码
        Object attribute = SessionUtil.get(SessionConstant.SESSION_ORGANIZATION_EDIT);
        if(attribute == null)
            return Result.error(CodeMsg.ADMIN_ATTRIBUTE_NOT_SEND);

        //获取邮箱
        String verCode = attribute.toString();
        String email = (String) SessionUtil.get("email");

        //日期比较
        Date datetime = (Date) SessionUtil.get("sendTime");

        //验证码是否超过六十秒
        if((datetime.getTime() + (60 * 1000)) < new Date().getTime())
        {
            //不要忘记清空
            SessionUtil.setRegisterSession(SessionConstant.SESSION_ORGANIZATION_EDIT);

            return Result.error(CodeMsg.SESSION_CODE_OVER_TIME_ERROR);
        }

        //验证码验证
        if(!code.equalsIgnoreCase(verCode))
        {
            return Result.error(CodeMsg.CPACHA_ERROR);
        }

        //邮箱对比
        if(!organization.getEmail().trim().equals(email))
        {
            return Result.error(CodeMsg.ADMIN_REGISTER_EMAIL_ERROR);
        }

        if(organizationService.save(find) == null)
        {
            return Result.error(CodeMsg.ADMIN_AUDIT_EDIT_ERROR);
        }

        //不要忘记清空
        SessionUtil.setRegisterSession(SessionConstant.SESSION_ORGANIZATION_EDIT);

        find.setRole(login.getRole());

        //设置session
        SessionUtil.set(SessionConstant.SESSION_USER_ORGANIZATION, find);

        return Result.success(true);
    }

    /**
     * 提交盛和
     * @return
     */
    @PostMapping("/submit_audit")
    @ResponseBody
    public Result<Boolean> submitAudit()
    {
        Organization login = SessionUtil.getLoginedOrganization();

        //判断是否编辑
        if(!organizationService.isOrganizationEdit(login))
            return Result.error(CodeMsg.ADMIN_SUBMIT_AUDIT_ERROR);

        Organization find = organizationService.find(login.getId());
        find.setAuditStatus(AuditStatus.AUDIT.getCode());

        if(organizationService.save(find) == null)
            return Result.error(CodeMsg.ADMIN_SUBMIT_AUDIT_ERROR2);

        //设置Session
        login.setAuditStatus(find.getAuditStatus());
        SessionUtil.set(SessionConstant.SESSION_USER_ORGANIZATION, login);

        return Result.success(true);
    }

    /**
     * 审核
     * @param id
     * @param flag
     * @param notPassReason
     * @return
     */
    @PostMapping("/approval")
    @ResponseBody
    public Result<Boolean> approval(Long id, Boolean flag, String notPassReason)
    {
        Organization find = organizationService.find(id);
        if(find == null)
            return Result.error(CodeMsg.DATA_ERROR);

        if(find.getAuditStatus().intValue() != AuditStatus.AUDIT.getCode().intValue())
            return Result.error(CodeMsg.ADMIN_APPROVAL_ERROR);

        if(flag)
        {
            find.setAuditStatus(AuditStatus.PASS.getCode());
        }
        else
        {
            if(notPassReason != null)
            {
                if(notPassReason.trim().length() > 50)
                    return Result.error(CodeMsg.ADMIN_APPROVAL_MSG_LENGTH_ERROR);
            }
            find.setAuditStatus(AuditStatus.NOT_PASS.getCode());
            find.setNotPassReason(notPassReason);
        }

        if(organizationService.save(find) == null)
            return Result.error(CodeMsg.ADMIN_APPROVAL_ERROR);

        return Result.success(true);
    }

    /**
     * 支付密码验证
     */
    @PostMapping("/pay_password_verify")
    @ResponseBody
    public Result<Boolean> payPasswordVerify(String password)
    {
        Organization login = SessionUtil.getLoginedOrganization();

        login = organizationService.find(login.getId());

        if(!login.getPayPassword().equals(password))
            return Result.error(CodeMsg.COMMON_PAY_PASSWORD_ERROR);

        return Result.success(true);
    }


    /**
     * 修改支付密码
     * @return
     */
    @GetMapping("/update_pay_pwd")
    public String updatePayPwd(Model model)
    {
        model.addAttribute("title","支付密码管理");
        return "admin/organization/update_pay_pwd";
    }

    /**
     * 修改支付密码
     * @param password
     * @param payPwd
     * @return
     */
    @PostMapping("/update_pay_pwd")
    @ResponseBody
    public Result<Boolean> updatePayPwd(String password, String payPwd)
    {
        Organization login = SessionUtil.getLoginedOrganization();
        if(!organizationService.isOrganizationOperation(login))
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_AUDIT_STATUS_ERROR);

        if(StringUtils.isEmpty(password))
            return Result.error(CodeMsg.PASSWORD_NULL_ERROR);

        if(StringUtils.isEmpty(payPwd))
            return Result.error(CodeMsg.ADMIN_PAY_PWD_NULL_ERROR);

        Organization find = organizationService.find(login.getId());

        //判断密码是否与旧密码一致
        if(!find.getPassword().equals(password))
            return Result.error(CodeMsg.PASSWORD_ERROR);

        find.setPayPassword(payPwd);
        CodeMsg codeMsg = ValidateEntityUtil.validate(find);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        if(organizationService.save(find) == null)
            return Result.error(CodeMsg.ADMIN_PAY_PWD_UPDATE_ERROR);

        //修改支付密码
        login.setPayPassword(payPwd);
        SessionUtil.set(SessionConstant.SESSION_USER_ORGANIZATION, login);

        return Result.success(true);
    }
}
