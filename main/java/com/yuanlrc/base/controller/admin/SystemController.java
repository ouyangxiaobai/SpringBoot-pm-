package com.yuanlrc.base.controller.admin;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.yuanlrc.base.annotion.ValidateEntity;
import com.yuanlrc.base.bean.*;
import com.yuanlrc.base.config.AppConfig;
import com.yuanlrc.base.entity.admin.OrderAuth;
import com.yuanlrc.base.entity.home.Organization;
import com.yuanlrc.base.service.admin.OrderAuthService;
import com.yuanlrc.base.service.home.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuanlrc.base.constant.SessionConstant;
import com.yuanlrc.base.entity.admin.OperaterLog;
import com.yuanlrc.base.entity.admin.Role;
import com.yuanlrc.base.entity.admin.User;
import com.yuanlrc.base.listener.SessionListener;
import com.yuanlrc.base.service.admin.DatabaseBakService;
import com.yuanlrc.base.service.admin.OperaterLogService;
import com.yuanlrc.base.service.admin.UserService;
import com.yuanlrc.base.util.SessionUtil;
import com.yuanlrc.base.util.StringUtil;
import com.yuanlrc.base.util.ValidateEntityUtil;

/**
 * 系统控制器
 * @author Administrator
 *
 */
@RequestMapping("/system")
@Controller
public class SystemController {

	
	
	@Autowired
	private OperaterLogService operaterLogService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private DatabaseBakService databaseBakService;
	@Autowired
	private OrderAuthService orderAuthService;
	@Autowired
	private OrganizationService organizationService;
	@Value("${show.tips.text}")
	private String showTipsText;
	@Value("${show.tips.url.text}")
	private String showTipsUrlText;
	@Value("${show.tips.btn.text}")
	private String showTipsBtnText;
	@Value("${show.tips.url}")
	private String showTipsUtl;
	private Logger log = LoggerFactory.getLogger(SystemController.class);
	
	/**
	 * 登录页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(Model model)
	{
		model.addAttribute("loginTypes", LoginType.values());
		return "admin/system/login";
	}
	
	/**
	 * 用户登录提交表单处理方法
	 * @param request
	 * @param cpacha
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> login(HttpServletRequest request,String username,String password,String cpacha, Integer type){
		if(username == null){
			return Result.error(CodeMsg.ADMIN_USERNAME_EMPTY);
		}

		if(password == null){
			return Result.error(CodeMsg.ADMIN_PASSWORD_EMPTY);
		}

		//表示实体信息合法，开始验证验证码是否为空
		if(StringUtils.isEmpty(cpacha)){
			return Result.error(CodeMsg.CPACHA_EMPTY);
		}
		//说明验证码不为空，从session里获取验证码
		Object attribute = request.getSession().getAttribute("admin_login");
		if(attribute == null){
			return Result.error(CodeMsg.SESSION_EXPIRED);
		}

		//表示session未失效，进一步判断用户填写的验证码是否正确
		if(!cpacha.equalsIgnoreCase(attribute.toString())){
			return Result.error(CodeMsg.CPACHA_ERROR);
		}


		if(type == LoginType.ADMINISTRATOR.getCode()){
			//表示验证码正确，开始查询数据库，检验密码是否正确
			User user = userService.findByUsername(username);
			//判断是否为空
			if(user == null){
				return Result.error(CodeMsg.ADMIN_USERNAME_NO_EXIST);
			}
			//表示用户存在，进一步对比密码是否正确
			if(!user.getPassword().equals(password)){
				return Result.error(CodeMsg.ADMIN_PASSWORD_ERROR);
			}
			//表示密码正确，接下来判断用户状态是否可用
			if(user.getStatus() == User.ADMIN_USER_STATUS_UNABLE){
				return Result.error(CodeMsg.ADMIN_USER_UNABLE);
			}
			//检查用户所属角色状态是否可用
			if(user.getRole() == null || user.getRole().getStatus() == Role.ADMIN_ROLE_STATUS_UNABLE){
				return Result.error(CodeMsg.ADMIN_USER_ROLE_UNABLE);
			}
			//检查用户所属角色的权限是否存在
			if(user.getRole().getAuthorities() == null || user.getRole().getAuthorities().size() == 0){
				return Result.error(CodeMsg.ADMIN_USER_ROLE_AUTHORITES_EMPTY);
			}
			//检查一切符合，可以登录，将用户信息存放至session
			request.getSession().setAttribute(SessionConstant.SESSION_USER_LOGIN_KEY, user);
			request.getSession().setAttribute("type",type);
			//销毁session中的验证码
			request.getSession().setAttribute("admin_login", null);
			//将登陆记录写入日志库
			operaterLogService.add("用户【"+username+"】于【" + StringUtil.getFormatterDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "】登录系统！");
			log.info("用户成功登录，user = " + user);
		}
		else if(type == LoginType.ORGANIZATION.getCode()){
			//表示验证码正确，开始查询数据库，检验密码是否正确
			Organization byMobile = organizationService.findByPhone(username);
			//判断是否为空
			if(byMobile == null){
				return Result.error(CodeMsg.ADMIN_USERNAME_NO_EXIST);
			}

			//表示用户存在，进一步对比密码是否正确
			if(!byMobile.getPassword().equals(password)){
				return Result.error(CodeMsg.ADMIN_PASSWORD_ERROR);
			}

			//检查用户所属角色状态是否可用
			if(byMobile.getRole() == null || byMobile.getRole().getStatus() == Role.ADMIN_ROLE_STATUS_UNABLE){
				return Result.error(CodeMsg.ADMIN_USER_ROLE_UNABLE);
			}

			//检查用户所属角色的权限是否存在
			if(byMobile.getRole().getAuthorities() == null || byMobile.getRole().getAuthorities().size() == 0){
				return Result.error(CodeMsg.ADMIN_USER_ROLE_AUTHORITES_EMPTY);
			}

			//检查一切符合，可以登录，将用户信息存放至session
			request.getSession().setAttribute(SessionConstant.SESSION_USER_ORGANIZATION, byMobile);
			request.getSession().setAttribute("type",type);

			//销毁session中的验证码
			request.getSession().setAttribute("admin_login", null);

			//将登陆记录写入日志库
			operaterLogService.add("机构【"+byMobile.getName()+"】于【" + StringUtil.getFormatterDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "】登录系统！");
			log.info("机构成功登录，judge = " + byMobile);
		}

		return Result.success(true);
	}
	
	/**
	 * 登录成功后的系统主页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(Model model){
		model.addAttribute("operatorLogs", operaterLogService.findLastestLog(10));
		model.addAttribute("userTotal", userService.total());
		model.addAttribute("operatorLogTotal", operaterLogService.total());
		model.addAttribute("databaseBackupTotal", databaseBakService.total());
		model.addAttribute("onlineUserTotal", SessionListener.onlineUserCount);
		model.addAttribute("showTipsText", showTipsText);
		model.addAttribute("showTipsUrlText", showTipsUrlText);
		model.addAttribute("showTipsUtl", showTipsUtl);
		model.addAttribute("showTipsBtnText", showTipsBtnText);
		return "admin/system/index";
	}
	
	/**
	 * 注销登录
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(){
		Integer type = (Integer) SessionUtil.get("type");
		if(type == LoginType.ADMINISTRATOR.getCode()){
			User loginedUser = SessionUtil.getLoginedUser();
			if(loginedUser != null){
				SessionUtil.set(SessionConstant.SESSION_USER_LOGIN_KEY, null);
			}
		}else if(type == LoginType.ORGANIZATION.getCode()){
			Organization loginedJudge = SessionUtil.getLoginedOrganization();
			if(loginedJudge != null){
				SessionUtil.set(SessionConstant.SESSION_USER_ORGANIZATION,null);
			}
		}
		return "redirect:login";
	}
	
	/**
	 * 无权限提示页面
	 * @return
	 */
	@RequestMapping(value="/no_right")
	public String noRight(){
		return "admin/system/no_right";
	}
	
	/**
	 * 修改用户个人信息
	 * @return
	 */
	@RequestMapping(value="/update_userinfo",method=RequestMethod.GET)
	public String updateUserInfo(){
		return "admin/system/update_userinfo";
	}
	
	/**
	 * 修改个人信息保存
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/update_userinfo",method=RequestMethod.POST)
	public String updateUserInfo(User user){
		User loginedUser = SessionUtil.getLoginedUser();
		loginedUser.setEmail(user.getEmail());
		loginedUser.setMobile(user.getMobile());
		loginedUser.setHeadPic(user.getHeadPic());
		//首先保存到数据库
		userService.save(loginedUser);
		//更新session里的值
		SessionUtil.set(SessionConstant.SESSION_USER_LOGIN_KEY, loginedUser);
		return "redirect:update_userinfo";
	}
	
	/**
	 * 修改密码页面
	 * @return
	 */
	@RequestMapping(value="/update_pwd",method=RequestMethod.GET)
	public String updatePwd(){
		return "admin/system/update_pwd";
	}
	
	/**
	 * 修改密码表单提交
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@RequestMapping(value="/update_pwd",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> updatePwd(@RequestParam(name="oldPwd",required=true)String oldPwd,
			@RequestParam(name="newPwd",required=true)String newPwd
			){
		Integer type = (Integer) SessionUtil.get("type");
		if(type == LoginType.ADMINISTRATOR.getCode()){
			User loginedUser = SessionUtil.getLoginedUser();
			if(!loginedUser.getPassword().equals(oldPwd)){
				return Result.error(CodeMsg.ADMIN_USER_UPDATE_PWD_ERROR);
			}
			if(StringUtils.isEmpty(newPwd)){
				return Result.error(CodeMsg.ADMIN_USER_UPDATE_PWD_EMPTY);
			}
			if(newPwd.length()<4){
				return Result.error(CodeMsg.PASSWORD_MIN_LENGTH_ERROR);
			}
			if(newPwd.length() > 18){
				return Result.error(CodeMsg.PASSWORD_MAX_LENGTH_ERROR);
			}
			loginedUser.setPassword(newPwd);
			//保存数据库
			userService.save(loginedUser);
			//更新session
			loginedUser.setPassword(newPwd);
			SessionUtil.set(SessionConstant.SESSION_USER_LOGIN_KEY, loginedUser);
		}else if(type == LoginType.ORGANIZATION.getCode()){
			Organization loginedJudge = SessionUtil.getLoginedOrganization();

			Organization find = organizationService.find(loginedJudge.getId());
			if(!find.getPassword().equals(oldPwd)){
				return Result.error(CodeMsg.ADMIN_USER_UPDATE_PWD_ERROR);
			}

			if(StringUtils.isEmpty(newPwd)){
				return Result.error(CodeMsg.ADMIN_USER_UPDATE_PWD_EMPTY);
			}

			find.setPassword(newPwd);

			CodeMsg codeMsg = ValidateEntityUtil.validate(find);
			if (codeMsg.getCode() != CodeMsg.SUCCESS.getCode())
				return Result.error(codeMsg);

			//保存数据库
			organizationService.save(find);

			//更新session
			loginedJudge.setPassword(newPwd);

			SessionUtil.set(SessionConstant.SESSION_USER_ORGANIZATION, loginedJudge);
		}


		return Result.success(true);
	}
	/**
	 * 验证订单
	 * @param orderSn
	 * @param phone
	 * @return
	 */
	@RequestMapping(value="/auth_order",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> authOrder(@RequestParam(name="orderSn",required=true)String orderSn,@RequestParam(name="phone",required=true)String phone){
		if(orderSn.length() < 18){
			return Result.error(CodeMsg.ORDER_SN_ERROR);
		}
		if(phone.length() < 11){
			return Result.error(CodeMsg.PHONE_ERROR);
		}
		if(!StringUtil.authOrder(orderSn, phone)){
			return Result.error(CodeMsg.ORDER_AUTH_ERROR);
		}
		OrderAuth orderAuth = new OrderAuth();
		orderAuth.setMac(StringUtil.getMac());
		orderAuth.setOrderSn(orderSn);
		orderAuth.setPhone(phone);
		orderAuthService.save(orderAuth);
		AppConfig.ORDER_AUTH = 1;
		return Result.success(true);
	}
	/**
	 * 日志管理列表
	 * @param model
	 * @param operaterLog
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value="/operator_log_list")
	public String operatorLogList(Model model,OperaterLog operaterLog,PageBean<OperaterLog> pageBean){
		model.addAttribute("pageBean", operaterLogService.findList(operaterLog, pageBean));
		model.addAttribute("operator", operaterLog.getOperator());
		model.addAttribute("title", "日志列表");
		return "admin/system/operator_log_list";
	}
	
	/**
	 * 删除操作日志，可删除多个
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/delete_operator_log",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> delete(String ids){
		if(!StringUtils.isEmpty(ids)){
			String[] splitIds = ids.split(",");
			for(String id : splitIds){
				operaterLogService.delete(Long.valueOf(id));
			}
		}
		return Result.success(true);
	}
	
	/**
	 * 清空整个日志
	 * @return
	 */
	@RequestMapping(value="/delete_all_operator_log",method=RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> deleteAll(){
		operaterLogService.deleteAll();
		return Result.success(true);
	}
}
