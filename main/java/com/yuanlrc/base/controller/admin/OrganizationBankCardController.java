package com.yuanlrc.base.controller.admin;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.entity.home.OrganizationBankCard;
import com.yuanlrc.base.service.home.OrganizationBankCardService;
import com.yuanlrc.base.service.home.OrganizationService;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 银行卡管理
 */
@Controller
@RequestMapping("/admin/org_bank_card")
public class OrganizationBankCardController {

    @Autowired
    private OrganizationBankCardService organizationBankCardService;

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/list")
    public String list(Model model, OrganizationBankCard organizationBankCard, PageBean<OrganizationBankCard> pageBean)
    {
        Organization login = SessionUtil.getLoginedOrganization();
        model.addAttribute("title", "银行卡管理");
        model.addAttribute("pageBean", organizationBankCardService.findListByOrgId(login.getId(),organizationBankCard.getName(), pageBean));
        model.addAttribute("name", organizationBankCard.getName());
        model.addAttribute("organization", SessionUtil.getLoginedOrganization());
        return "admin/org_bank_card/list";
    }

    @GetMapping("/add")
    public String add(Model model)
    {
        Organization login = SessionUtil.getLoginedOrganization();
        if(!organizationService.isOrganizationOperation(login))
            return "redirect:/system/index";

        return "admin/org_bank_card/add";
    }

    @PostMapping("/delete")
    @ResponseBody
    public Result<Boolean> delete(Long id)
    {
        Organization login = SessionUtil.getLoginedOrganization();
        if(!organizationService.isOrganizationOperation(login))
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_AUDIT_STATUS_ERROR);

        try
        {
            organizationBankCardService.delete(id);
        }catch (Exception e)
        {
            return Result.error(CodeMsg.ADMIN_ORG_BANK_CARD_DELETE_ERROR);
        }
        return Result.success(true);
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<Boolean> add(OrganizationBankCard organizationBankCard)
    {
        Organization login = SessionUtil.getLoginedOrganization();
        if(!organizationService.isOrganizationOperation(login))
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_AUDIT_STATUS_ERROR);

        CodeMsg codeMsg = ValidateEntityUtil.validate(organizationBankCard);
        if(codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
            return Result.error(codeMsg);

        if(organizationBankCardService.findByCardNumbers(organizationBankCard.getCardNumbers()) != null)
            return Result.error(CodeMsg.ADMIN_ORG_BANK_CARD_REPETITION_ERROR);

        //手机号验证
        if(!StringUtil.isMobile(organizationBankCard.getPhone()))
            return Result.error(CodeMsg.COMMON_PHONE_FORMAET_ERROR);

        organizationBankCard.setOrganization(login);

        if(organizationBankCardService.save(organizationBankCard) == null)
            return Result.error(CodeMsg.ADMIN_ORG_BANK_CARD_ADD_ERROR);
        return Result.success(true);
    }
}
