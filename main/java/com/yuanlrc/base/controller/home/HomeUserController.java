package com.yuanlrc.base.controller.home;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.PageBean;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.bean.UserStatus;
import com.yuanlrc.base.constant.SessionConstant;
import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.UserAlipay;
import com.yuanlrc.base.entity.home.UserBankCard;
import com.yuanlrc.base.entity.home.UserWithdrawalRecord;
import com.yuanlrc.base.service.home.HomeUserService;
import com.yuanlrc.base.service.home.UserAlipayService;
import com.yuanlrc.base.service.home.UserBankCardService;
import com.yuanlrc.base.service.home.UserWithdrawalRecordService;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 前台用户Controller
 */
@Controller
@RequestMapping("/home/user")
public class HomeUserController {

    @Autowired
    private HomeUserService homeUserService;

    @Autowired
    private UserBankCardService userBankCardService;

    @Autowired
    private UserAlipayService userAlipayService;

    @Autowired
    private UserWithdrawalRecordService userWithdrawalRecordService;

    /**
     * 注册页面
     * @return
     */
    @GetMapping("/register")
    public String register(){
        return "home/home_user/register";
    }

    /**
     * 注册表单提交
     * @param homeUser
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public Result<Boolean> register(HomeUser homeUser,@RequestParam("identifyCode")String identifyCode){
        homeUser.setPayPassword(HomeUser.DEFAULT_PAY_PASSWORD);

        if(StringUtils.isEmpty(identifyCode)){
            return Result.error(CodeMsg.CODE_NULL_ERROR);
        }
        CodeMsg validate = ValidateEntityUtil.validate(homeUser);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }

        Object registerCode = SessionUtil.get(SessionConstant.SESSION_HOME_USER_REGISTER_CODE);
        if(registerCode == null){
            return Result.error(CodeMsg.EMAIL_CODE_NOT_SEND);
        }

        String codeString = registerCode.toString();
        String email = (String) SessionUtil.get("email");
        Date sendTime = (Date) SessionUtil.get("sendTime");


        if(sendTime.getTime() + (120*1000) < new Date().getTime()){
            SessionUtil.setRegisterSession(SessionConstant.SESSION_HOME_USER_REGISTER_CODE);
            return Result.error(CodeMsg.CODE_TIME_OVER_ERROR);
        }

        if(!identifyCode.equalsIgnoreCase(codeString)){
            return Result.error(CodeMsg.CODE_ERROR);
        }

        if(!homeUser.getEmail().trim().equals(email)){
            return Result.error(CodeMsg.EMAIL_ERROR);
        }

        if(!StringUtil.emailFormat(email)){
            return Result.error(CodeMsg.COMMON_EMAIL_FORMAET_ERROR);
        }
        HomeUser byEmail = homeUserService.findByEmail(email);
        if(byEmail != null){
            return Result.error(CodeMsg.EMAIL_HAS_REGISTER);
        }

        if(!StringUtil.isMobile(homeUser.getMobile())){
            return Result.error(CodeMsg.MOBILE_FORMAT_ERROR);
        }
        HomeUser byMobile = homeUserService.findByMobile(homeUser.getMobile());
        if(byMobile != null){
            return Result.error(CodeMsg.MOBILE_HAS_REGISTER);
        }

        if(!StringUtil.isCard(homeUser.getIdNumber())){
            return Result.error(CodeMsg.ID_NUMBER_FORMAT_ERROR);
        }
        HomeUser byIdNumber = homeUserService.findByIdNumber(homeUser.getIdNumber());
        if(byIdNumber != null){
            return Result.error(CodeMsg.ID_NUMBER_HAS_REGISTER);
        }

        if(homeUserService.save(homeUser) == null){
            return Result.error(CodeMsg.HOME_USER_SAVE_ERROR);
        }
        System.out.println(homeUser);
        return Result.success(true);
    }

    /**
     * 登录页面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "home/home_user/login";
    }

    /**
     * 根据密码登录提交
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/loginByPass")
    @ResponseBody
    public Result<Boolean> loginByPass(String email,String password){
        if(StringUtils.isEmpty(email)){
            return Result.error(CodeMsg.EMAIL_NULL_ERROR);
        }
        if(StringUtils.isEmpty(password)){
            return Result.error(CodeMsg.PASSWORD_NULL_ERROR);
        }
        if(!StringUtil.emailFormat(email)){
            return Result.error(CodeMsg.COMMON_EMAIL_FORMAET_ERROR);
        }
        HomeUser byEmail = homeUserService.findByEmail(email);
        if(byEmail.getStatus() == UserStatus.FREEZE.code){
            return Result.error(CodeMsg.HOME_USER_UNABLE);
        }
        if(byEmail == null){
            return Result.error(CodeMsg.EMAIL_NOT_REGISTER);
        }
        if(!byEmail.getPassword().equals(password)){
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }

        SessionUtil.set(SessionConstant.SESSION_HOME_USER_LOGIN_CODE,null);
        SessionUtil.set(SessionConstant.SESSION_HOME_USER_LOGIN_KEY,byEmail);


        return Result.success(true);
    }

    /**
     * 根据验证码登录提交
     * @param email
     * @param identifyCode
     * @return
     */
    @PostMapping("/loginByVal")
    @ResponseBody
    public Result<Boolean> loginByVal(String email,String identifyCode){
        if(StringUtils.isEmpty(email)){
            return Result.error(CodeMsg.EMAIL_NULL_ERROR);
        }
        if(StringUtils.isEmpty(identifyCode)){
            return Result.error(CodeMsg.PASSWORD_NULL_ERROR);
        }

        Object code = SessionUtil.get(SessionConstant.SESSION_HOME_USER_LOGIN_CODE);
        if(code == null){
            return Result.error(CodeMsg.EMAIL_CODE_NOT_SEND);
        }

        String codeString = code.toString();
        String sendEmail = (String) SessionUtil.get("email");
        Date sendTime = (Date) SessionUtil.get("sendTime");

        if(sendTime.getTime() + (120*1000) < new Date().getTime()){
            SessionUtil.setRegisterSession(SessionConstant.SESSION_HOME_USER_LOGIN_CODE);
            return Result.error(CodeMsg.CODE_TIME_OVER_ERROR);
        }

        if(!identifyCode.equalsIgnoreCase(codeString)){
            return Result.error(CodeMsg.CODE_ERROR);
        }

        if(!sendEmail.trim().equals(email)){
            return Result.error(CodeMsg.EMAIL_ERROR);
        }

        if(!StringUtil.emailFormat(email)){
            return Result.error(CodeMsg.COMMON_EMAIL_FORMAET_ERROR);
        }

        HomeUser byEmail = homeUserService.findByEmail(email);
        if(byEmail == null){
            return Result.error(CodeMsg.EMAIL_NOT_REGISTER);
        }

        SessionUtil.set(SessionConstant.SESSION_HOME_USER_LOGIN_CODE,null);
        SessionUtil.set(SessionConstant.SESSION_HOME_USER_LOGIN_KEY,byEmail);


        return Result.success(true);
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public String logout(){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser != null){
            SessionUtil.set(SessionConstant.SESSION_HOME_USER_LOGIN_KEY,null);
        }
        return "redirect:login";
    }

    /**
     * 个人中心首页  个人基本资料
     * @return
     */
    @GetMapping("/index")
    public String index(Model model){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return "redirect:login";
        }
        HomeUser homeUser = homeUserService.find(loginedHomeUser.getId());
        model.addAttribute("homeUser",homeUser);
        return "home/home_user/index";
    }

    /**
     * 基本信息修改
     * @param homeUser
     * @return
     */
    @PostMapping("/updateBasic")
    @ResponseBody
    public Result<Boolean> updateBasic(HomeUser homeUser){
        if(homeUser == null){
            return Result.error(CodeMsg.DATA_ERROR);
        }
        if(homeUser.getUsername() == null){
            return Result.error(CodeMsg.USER_NAME_NULL_ERROR);
        }
        if(homeUser.getUsername().length() < 2 || homeUser.getUsername().length() > 18){
            return Result.error(CodeMsg.USER_NAME_LENGTH_ERROR);
        }
        if(homeUser.getName().length() > 18){
            return Result.error(CodeMsg.NAME_LENGTH_ERROR);
        }
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }

        HomeUser find = homeUserService.find(loginedHomeUser.getId());
        find.setUsername(homeUser.getUsername());
        find.setName(homeUser.getName());
        find.setSex(homeUser.getSex());
        if(homeUserService.save(find) == null){
            return Result.error(CodeMsg.HOME_USER_EDIT_ERROR);
        }

        SessionUtil.set(SessionConstant.SESSION_HOME_USER_LOGIN_KEY,find);
        return Result.success(true);
    }

    /**
     * 更新头像
     * @param headPic
     * @return
     */
    @PostMapping("/updatePic")
    @ResponseBody
    public Result<Boolean> updatePic(String headPic){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        HomeUser find = homeUserService.find(loginedHomeUser.getId());
        find.setHeadPic(headPic);
        if(homeUserService.save(find) == null){
            return Result.error(CodeMsg.HOME_USER_EDIT_HEAD_PIC_ERROR);
        }

        loginedHomeUser.setHeadPic(headPic);
        SessionUtil.set(SessionConstant.SESSION_HOME_USER_LOGIN_KEY,loginedHomeUser);
        return Result.success(true);
    }

    /**
     * 修改密码页面
     * @return
     */
    @GetMapping("/updatePassword")
    public String updatePassword(){
        return "home/home_user/update_password";
    }

    /**
     * 更改密码提交
     * @param password
     * @return
     */
    @PostMapping("/updatePassword")
    @ResponseBody
    public Result<Boolean> updatePassword(@RequestParam("password") String password,@RequestParam("identifyCode")String identifyCode){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        if(password.length()<4||password.length()>32){
            return Result.error(CodeMsg.PASSWORD_LENGTH_ERROR);
        }

        Object editPassCode = SessionUtil.get(SessionConstant.SESSION_HOME_USER_EDIT_PASS_CODE);
        if(editPassCode == null){
            return Result.error(CodeMsg.EMAIL_CODE_NOT_SEND);
        }

        String editCodeString = editPassCode.toString();

        String email = (String) SessionUtil.get("email");
        Date sendTime = (Date) SessionUtil.get("sendTime");

        if(sendTime.getTime() + (120*1000) < new Date().getTime()){
            SessionUtil.setRegisterSession(SessionConstant.SESSION_HOME_USER_EDIT_PASS_CODE);
            return Result.error(CodeMsg.CODE_TIME_OVER_ERROR);
        }

        if(!identifyCode.equalsIgnoreCase(editCodeString)){
            return Result.error(CodeMsg.CODE_ERROR);
        }

        HomeUser find = homeUserService.find(loginedHomeUser.getId());

        if(!find.getEmail().trim().equals(email)){
            return Result.error(CodeMsg.EMAIL_ERROR);
        }

        find.setPassword(password);
        if(homeUserService.save(find) == null){
            return Result.error(CodeMsg.HOME_USER_EDIT_HEAD_PIC_ERROR);
        }
        SessionUtil.set(SessionConstant.SESSION_HOME_USER_LOGIN_KEY,find);

        return Result.success(true);
    }

    /**
     * 修改支付密码页面
     * @return
     */
    @GetMapping("/updatePayPassword")
    public String updatePayPassword(){
        return "home/home_user/update_pay_password";
    }

    /**
     * 支付密码修改提交
     * @param password
     * @param payPassword
     * @return
     */
    @PostMapping("/updatePayPassword")
    @ResponseBody
    public Result<Boolean> updatePayPassword(@RequestParam("password") String password,@RequestParam("payPassword")String payPassword){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return Result.error(CodeMsg.USER_SESSION_EXPIRED);
        }
        if(StringUtils.isEmpty(password)){
            return Result.error(CodeMsg.PASSWORD_NULL_ERROR);
        }
        if(payPassword.length() != 6){
            return Result.error(CodeMsg.PAY_PASSWORD_LENGTH_ERROR);
        }

        HomeUser find = homeUserService.find(loginedHomeUser.getId());
        if(!find.getPassword().equals(password)){
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }

        find.setPayPassword(payPassword);
        if(homeUserService.save(find) == null){
            return Result.error(CodeMsg.HOME_USER_EDIT_HEAD_PIC_ERROR);
        }
        SessionUtil.set(SessionConstant.SESSION_HOME_USER_LOGIN_KEY,find);

        return Result.success(true);
    }

    /**
     * 银行卡管理页面
     * @param model
     * @return
     */
    @GetMapping("/bankCard")
    public String bankCard(Model model, PageBean<UserBankCard> pageBean){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return "redirect:login";
        }
        model.addAttribute("pageBean",userBankCardService.findList(loginedHomeUser.getId(),pageBean));
        return "home/home_user/bankCard";
    }

    /**
     * 充值管理页面
     * @param model
     * @param pageBean
     * @return
     */
    @GetMapping("/alipay")
    public String alipay(Model model, PageBean<UserAlipay> pageBean){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return "redirect:login";
        }
        model.addAttribute("pageBean",userAlipayService.findList(loginedHomeUser.getId(),pageBean));
        return "home/home_user/alipay";
    }

    /**
     * 提现管理页面
     * @param model
     * @param pageBean
     * @return
     */
    @GetMapping("/withdrawalRecord")
    public String withdrawalRecord(Model model, PageBean<UserWithdrawalRecord> pageBean){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(loginedHomeUser == null){
            return "redirect:login";
        }
        model.addAttribute("bankCardList",userBankCardService.finByHomeUserId(loginedHomeUser.getId()));
        model.addAttribute("pageBean",userWithdrawalRecordService.findList(loginedHomeUser.getId(),pageBean));
        return "home/home_user/withdrawal_record";
    }

    /**
     * 确认支付密码
     * @param payPassword
     * @return
     */
    @RequestMapping(value = "/pay_password_ensure",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> payPasswordEnsure(String payPassword){
        HomeUser loginedHomeUser = SessionUtil.getLoginedHomeUser();
        if(!payPassword.equals(loginedHomeUser.getPayPassword())){
            return Result.error(CodeMsg.COMMON_PAY_PASSWORD_ERROR);
        }

        return Result.success(true);
    }

}
