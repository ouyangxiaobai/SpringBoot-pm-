package com.yuanlrc.base.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 系统运行时的常量
 * @author Administrator
 *
 */
public class RuntimeConstant {

	//后台登录拦截器无需拦截的url
	public static List<String> loginExcludePathPatterns = Arrays.asList(
			"/home/**",
			"/system/login",
			"/system/auth_order",
			"/admin/css/**",
			"/admin/fonts/**",
			"/admin/js/**",
			"/admin/images/**",
			"/error",
			"/upload/**",
			"/photo/view",
			"/cpacha/generate_cpacha",
			"/admin/Distpicker/**",
			"/send_email/**",
			"/admin/organization/modification_password",
			"/send_email/generate_code",
			"/home/layui/**",
			"/admin/organization/register",
			"/send_email/generate_code",
			"/admin/datepicker/**",
			"/home/layui/**",
			"/admin/kindeditor/**",
			"/admin/organization/pay_password_verify",
			"/admin/bidding/frame",
			"/common/**",
			"/admin/select2/**",
			"/admin/org_alipay/alipay_notify"
	);

	//后台权限拦截器无需拦截的url
	public static List<String> authorityExcludePathPatterns = Arrays.asList(
			"/home/**",
			"/system/login",
			"/system/auth_order",
			"/system/index",
			"/system/no_right",
			"/admin/css/**",
			"/admin/fonts/**",
			"/admin/js/**",
			"/admin/images/**",
			"/error",
			"/cpacha/generate_cpacha",
			"/system/logout",
			"/system/update_userinfo",
			"/system/update_pwd",
			"/upload/**",
			"/photo/view",
			"/admin/Distpicker/**",
			"/send_email/**",
			"/admin/organization/modification_password",
			"/send_email/generate_code",
			"/home/layui/**",
			"/admin/organization/register",
			"/admin/organization/pay_password_verify",
			"/admin/datepicker/**",
			"/admin/kindeditor/**",
			"/admin/bidding/frame",
			"/common/**",
			"/admin/select2/**",
			"/admin/org_alipay/alipay_notify"
	);

	//前台登录拦截器无需拦截的url
	public static List<String> homeLoginExcludePathPatterns = Arrays.asList(
			"/admin/**",
			"/home/index/**",
			"/home/css/**",
			"/home/fonts/**",
			"/home/js/**",
			"/home/images/**",
			"/system/**",
			"/error",
			"/photo/view",
			"/cpacha/generate_cpacha",
			"/upload/**",
			"/home/picture/**",
			"/home/user/register",
			"/home/user/loginByPass",
			"/send_email/**",
			"/home/user/loginByVal",
			"/home/user/login",
			"/home/layui/**",
			"/common/**",
			"/home/user_alipay/alipay_notify",
			"/home/mods/**",
			"/home/distpicker/**",
			"/home/bidding/**",
			"/home/project/",
			"/home/fonts/**",
			"/home/common_problem/list",
			"/home/org/list",
			"/home/org/detail",
			"/home/news/list",
			"/home/news/detail",
			"/home/inform/list",
			"/home/inform/detail",
			"/home/agency/list"
	);

	//前台全局拦截器无需拦截的url
	public static List<String> homeGlobalExcludePathPatterns = Arrays.asList(
			"/admin/**",
			"/home/index/**",
			"/home/css/**",
			"/home/fonts/**",
			"/home/js/**",
			"/home/images/**",
			"/system/**",
			"/error",
			"/photo/view",
			"/upload/**",
			"/cpacha/generate_cpacha",
			"/home/picture/**",
			"/send_email/**",
			"/common/**",
			"/home/user_alipay/alipay_notify",
			"/home/mods/**",
			"/home/distpicker/**",
			"/home/bidding/**",
			"/home/project/",
			"/home/fonts/**",
			"/home/common_problem/list",
			"/home/org/list",
			"/home/org/detail",
			"/home/news/list",
			"/home/news/detail",
			"/home/inform/list",
			"/home/inform/detail",
			"/home/agency/list"
	);
}
