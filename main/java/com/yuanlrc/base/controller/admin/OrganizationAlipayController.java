package com.yuanlrc.base.controller.admin;

import com.alipay.api.internal.util.StringUtils;
import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.PayUserType;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.entity.home.OrganizationAlipay;
import com.yuanlrc.base.service.home.OrganizationAlipayService;
import com.yuanlrc.base.service.home.OrganizationService;
import com.yuanlrc.base.util.AlipayUtil;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 机构充值
 */
@Controller
@RequestMapping("/admin/org_alipay")
public class OrganizationAlipayController {

    @Value("${ylrc.alipay.title}")
    private String subject;

    @Value("${yrlc.alipay.body}")
    private String body;

    @Autowired
    private OrganizationAlipayService organizationAlipayService;

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/list")
    public String list(Model model, OrganizationAlipay organizationAlipay, PageBean<OrganizationAlipay> pageBean)
    {
        Organization login = SessionUtil.getLoginedOrganization();
        model.addAttribute("pageBean",
                organizationAlipayService.findListByOrgId(login.getId(), organizationAlipay.getOutTradeNo(), pageBean));

        model.addAttribute("title", "充值记录管理");
        model.addAttribute("outTradeNo", organizationAlipay.getOutTradeNo());
        return "admin/org_alipay/list";
    }

    @PostMapping("/add")
    @ResponseBody
    public Result<OrganizationAlipay> add(BigDecimal number)
    {
        Organization login = SessionUtil.getLoginedOrganization();
        if(login == null)
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);

        if(!organizationService.isOrganizationOperation(login))
            return Result.error(CodeMsg.ADMIN_ORGANIZATION_AUDIT_STATUS_ERROR);

        if(number.compareTo(BigDecimal.ZERO) < 0)
            return Result.error(CodeMsg.ADMIN_ORG_ALIPAY_NUMBER_ADD_ERROR);


        OrganizationAlipay organizationAlipay = new OrganizationAlipay();
        organizationAlipay.setOutTradeNo(StringUtil.gneerateSn("ylrc",""));
        organizationAlipay.setSubject(subject);
        organizationAlipay.setBody(body);
        organizationAlipay.setOrganization(login);
        organizationAlipay.setTotalAmount(number);

        if(organizationAlipayService.save(organizationAlipay) == null)
            return Result.error(CodeMsg.ADMIN_ORG_ALIPAY_ADD_ERROR);

        return Result.success(organizationAlipay);
    }

    @GetMapping("/to_pay")
    public String toPay(String outTradeNo, Model model) throws Exception {

        OrganizationAlipay alipay = organizationAlipayService.findByOutTradeNo(outTradeNo);
        if(alipay.getStatus() == OrganizationAlipay.pay_status_pid)
        {
            model.addAttribute("html", "你已经支付过了");
            return "/admin/org_alipay/alipay_pe";
        }

        String html = AlipayUtil.genHtml(alipay.getOutTradeNo(), alipay.getTotalAmount().toString(), alipay.getSubject(), "", PayUserType.ORGANIZATION.getCode());
        model.addAttribute("html", html);

        return "/admin/org_alipay/alipay_pe";
    }

    @RequestMapping("/alipay_notify")
    @ResponseBody
    public String alipayNotify(HttpServletRequest request)throws Exception
    {
        if(!AlipayUtil.isValid(request))
        {
            System.out.println("验证未通过");
            return "fail";
        }

        //订单号码
        String sn = request.getParameter("out_trade_no");
        String paySn = request.getParameter("trade_no"); //交易号
        String totalAmount = request.getParameter("total_amount"); //金额
        String status = request.getParameter("trade_status");  //支付状态

        if(StringUtils.isEmpty(sn) || StringUtils.isEmpty(paySn) || StringUtils.isEmpty(totalAmount) || StringUtils.isEmpty(status))
        {
            System.out.println("订单信息为空");
            return "fail";
        }

        OrganizationAlipay alipay = organizationAlipayService.findByOutTradeNo(sn);
        if(alipay == null) {
            System.out.println("订单不存在");
            return "fail";
        }

        if(alipay.getTotalAmount().compareTo(new BigDecimal(totalAmount)) != 0)
        {
            System.out.println("订单余额错误");
            return "fail";
        }

        //支付完成
        alipay.setStatus(OrganizationAlipay.pay_status_pid);
        alipay.setPayTime(new Date());
        alipay.setPaySn(paySn);
        organizationAlipayService.save(alipay);


        //修改余额
        Organization login = alipay.getOrganization();

        Organization organization = organizationService.find(login.getId());
        organization.setBalance(organization.getBalance().add(alipay.getTotalAmount()));

        organizationService.save(organization);

        return "success";
    }

}
