package com.yuanlrc.base.controller.admin;

import com.alipay.api.domain.BankCardInfo;
import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.entity.home.OrganizationBankCard;
import com.yuanlrc.base.entity.home.OrganizationWithdrawalRecord;
import com.yuanlrc.base.service.home.OrganizationBankCardService;
import com.yuanlrc.base.service.home.OrganizationService;
import com.yuanlrc.base.service.home.OrganizationWithdrawalRecordService;
import com.yuanlrc.base.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

/**
 * 机构提现记录
 */
@Controller
@RequestMapping("/admin/org_wr")
public class OrgWithdrawalRecordController {

    @Autowired
    private OrganizationWithdrawalRecordService organizationWithdrawalRecordService;

    @Autowired
    private OrganizationBankCardService organizationBankCardService;

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/my_list")
    public String myList(Model model, String bankCard, PageBean<OrganizationWithdrawalRecord> pageBean)
    {
        Organization login = SessionUtil.getLoginedOrganization();

        model.addAttribute("title", "提现记录");
        model.addAttribute("pageBean", organizationWithdrawalRecordService.findListByOrgId(login.getId(), bankCard, pageBean));
        model.addAttribute("bankCard", bankCard);
        model.addAttribute("bankCards", organizationBankCardService.findByOrganizationId(login.getId()));

        return "/admin/org_wr/my_list";
    }

    /**
     * 添加提现记录
     */
    @PostMapping("/add")
    @ResponseBody
    public Result<Boolean> add(Long bankId, BigDecimal money)
    {
        //判断用户是否交了押金和是否在通过状态
        Organization login = SessionUtil.getLoginedOrganization();

        Organization find = organizationService.find(login.getId());

        //是否可用提现
        if(!organizationService.isOrganizationOperation(find))
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_AUDIT_STATUS_ERROR);

        //判断是否支付过了
        if(find.getEarnestMoney() == Organization.NOT_PAY)
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_NOT_PAY_MONEY);

        //体现的金额不能小于0
        if(money.compareTo(BigDecimal.ZERO) < 0)
            return Result.error(CodeMsg.ADMIN_ORG_WR_ADD_MONEY_ERROR);

        //判断自己的余额够不够
        BigDecimal balance = find.getBalance();

        BigDecimal surplus = balance.subtract(money);
        if(surplus.compareTo(BigDecimal.ZERO) < 0)
            return Result.error(CodeMsg.ADMIN_ORG_BALANCE_ERROR);

        OrganizationBankCard bankCardInfo = organizationBankCardService.find(bankId);
        if(bankCardInfo == null)
            return Result.error(CodeMsg.ADMIN_BANKCARDINFO_NOT_FOUND_ERROR);

        //生成订单
        OrganizationWithdrawalRecord organizationWithdrawalRecord = new OrganizationWithdrawalRecord();
        organizationWithdrawalRecord.setMoney(money);
        organizationWithdrawalRecord.setBankCard(bankCardInfo.getCardNumbers());
        organizationWithdrawalRecord.setOrganization(find);

        if(organizationWithdrawalRecordService.save(organizationWithdrawalRecord) == null)
            return Result.error(CodeMsg.ADMIN_ORG_WR_ADD_ERROR);

        return Result.success(true);
    }

    /**
     * 管理员审批
     * @return
     */
    @GetMapping("/list")
    public String list(Model model, OrganizationWithdrawalRecord organizationWithdrawalRecord, PageBean<OrganizationWithdrawalRecord> pageBean)
    {
        model.addAttribute("title", "提现记录");
        model.addAttribute("pageBean", organizationWithdrawalRecordService.findList(organizationWithdrawalRecord, pageBean));
        model.addAttribute("name", organizationWithdrawalRecord == null
                || organizationWithdrawalRecord.getOrganization() == null
                || organizationWithdrawalRecord.getOrganization().getName() == null ? ""
                :  organizationWithdrawalRecord.getOrganization().getName());
        return "admin/org_wr/list";
    }

    /**
     * 审批
     * @param id
     * @param flag
     * @param notPassReason
     * @return
     */
    @PostMapping("/approval")
    @ResponseBody
    public Result<Boolean> approval(Long id, Boolean flag, String notPassReason)
    {
        OrganizationWithdrawalRecord find = organizationWithdrawalRecordService.find(id);
        if(find == null)
            return Result.error(CodeMsg.DATA_ERROR);

        if(find.getStatus() != WithdrawalStatus.AUDIT.getCode())
            return Result.error(CodeMsg.ADMIN_APPROVAL_ERROR);

        Organization organization = organizationService.find(find.getOrganization().getId());
        if(organization == null)
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_NOT_FOUND_ERROR);

        if(flag)
        {
            BigDecimal money = find.getMoney();


            BigDecimal balance = organization.getBalance(); // 用户余额

            //判断自己余额是否足够
            BigDecimal surplus = balance.subtract(money);
            if(surplus.compareTo(BigDecimal.ZERO) < 0 )
                return Result.error(CodeMsg.ADMIN_ORG_BALANCE_ERROR);

            organization.setBalance(surplus);

            find.setStatus(WithdrawalStatus.PASS.getCode());
        }
        else
        {
            if(notPassReason != null)
            {
                if(notPassReason.trim().length() > 50)
                    return Result.error(CodeMsg.ADMIN_APPROVAL_MSG_LENGTH_ERROR);
            }
            find.setStatus(WithdrawalStatus.NOT_PASS.getCode());
            find.setNotPassReason(notPassReason);
        }

        if(organizationWithdrawalRecordService.save(find, organization) == null)
            return Result.error(CodeMsg.ADMIN_APPROVAL_ERROR);

        return Result.success(true);
    }
}
