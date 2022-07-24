package com.yuanlrc.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.yuanlrc.base.entity.home.HomeUser;
import com.yuanlrc.base.entity.home.Organization;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yuanlrc.base.constant.SessionConstant;
import com.yuanlrc.base.entity.admin.User;

/**
 * session统一操作工具类
 * @author Administrator
 *
 */
public class SessionUtil {

	/**
	 * 获取请求request
	 * @return
	 */
	public static HttpServletRequest getRequest(){
		ServletRequestAttributes attributes =(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		return attributes == null ? null : attributes.getRequest();
	}
	
	/**
	 * 获取session
	 * @return
	 */
	public static HttpSession getSession(){
		HttpServletRequest request = getRequest();
		if(request != null){
			return request.getSession();
		}
		return null;
	}
	
	/**
	 * 获取指定键的值
	 * @param key
	 * @return
	 */
	public static Object get(String key){
		HttpSession session = getSession();
		if(session != null){
			return session.getAttribute(key);
		}
		return null;
	}
	
	/**
	 * 设置session值
	 * @param key
	 * @param object
	 */
	public static void set(String key,Object object){
		HttpSession session = getSession();
		if(session != null){
			session.setAttribute(key,object);
		}
	}
	
	/**
	 * 获取当前登录的用户
	 * @return
	 */
	public static User getLoginedUser(){
		HttpSession session = getSession();
		if(session != null){
			Object attribute = session.getAttribute(SessionConstant.SESSION_USER_LOGIN_KEY);
			return attribute == null ? null : (User)attribute;
		}
		return null;
	}

	/**
	 * 获取登录的企业用户
	 * @return
	 */
	public static Organization getLoginedOrganization()
	{
		HttpSession session = getSession();
		if(session != null){
			Object attribute = session.getAttribute(SessionConstant.SESSION_USER_ORGANIZATION);
			return attribute == null ? null : (Organization)attribute;
		}
		return null;
	}

	/**
	 * 获取登录的前台用户
	 * @return
	 */
	public static HomeUser getLoginedHomeUser()
	{
		HttpSession session = getSession();
		if(session != null){
			Object attribute = session.getAttribute(SessionConstant.SESSION_HOME_USER_LOGIN_KEY);
			return attribute == null ? null : (HomeUser)attribute;
		}
		return null;
	}

	/**
	 * 邮箱验证Session清空
	 */
	public static void setRegisterSession(String method)
	{
		set(method, null);
		set("email", null);
		set("datetime", null);
	}
}
