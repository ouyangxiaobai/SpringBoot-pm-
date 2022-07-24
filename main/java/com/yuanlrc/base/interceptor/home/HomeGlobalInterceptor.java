package com.yuanlrc.base.interceptor.home;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前台全局拦截器
 * @author Administrator
 *
 */
@Component
public class HomeGlobalInterceptor implements HandlerInterceptor{

	
	@Override
	public boolean  preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		
		
		return true;
	}
}
