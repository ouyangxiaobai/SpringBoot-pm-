package com.yuanlrc.base.controller.common;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.RemindType;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.constant.SessionConstant;
import com.yuanlrc.base.entity.common.BiddingProject;
import com.yuanlrc.base.entity.home.BiddingRemind;
import com.yuanlrc.base.service.home.BiddingRemindService;
import com.yuanlrc.base.util.CpachaUtil;
import com.yuanlrc.base.util.SendEmailUtil;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author zhong
 * @date 2020-01-06
 * @info 邮箱验证码控制器
 */
@Controller
@RequestMapping("/send_email")
public class SendEmailController {

    @Value("${ylrc.email.address}")
    private String sourceEmail; //发件人邮箱

    @Value("${ylrc.email.authorization-code}")
    private String authorizationCode; //授权码

    /**
     * 发送随机验证码
     * @param length 验证码长度
     * @param email 收件人
     * @param method session key
     * @return
     */
    @ResponseBody
    @PostMapping("/generate_code")
    public Result<Boolean> generateCode(@RequestParam(value = "len", defaultValue = "4") Integer length,
                                        @RequestParam(value = "email", defaultValue = "")String email,
                                        @RequestParam(value = "method", defaultValue = "email_code")String method)throws Exception
    {
        //邮箱验证
        if(!StringUtil.emailFormat(email)) {
            return Result.error(CodeMsg.COMMON_EMAIL_FORMAET_ERROR);
        }

        CpachaUtil cpachaUtil = new CpachaUtil(length);
        String verificationCode = cpachaUtil.generatorVCode().toUpperCase();
        SendEmailUtil sendEmailUtil = new SendEmailUtil();
        sendEmailUtil.setSourceEmail(sourceEmail);  //设置发件人
        sendEmailUtil.setEmail(email); //设置收件人
        sendEmailUtil.setCode(authorizationCode); //设置授权码
        sendEmailUtil.sendMsg("猿礼科技验证码查收:" + verificationCode); //发送消息

        SessionUtil.set(method, verificationCode);  //设置验证码
        SessionUtil.set("email", email); //设置邮箱
        SessionUtil.set("sendTime",new Date());//发送时间

        return Result.success(true);
    }

}
