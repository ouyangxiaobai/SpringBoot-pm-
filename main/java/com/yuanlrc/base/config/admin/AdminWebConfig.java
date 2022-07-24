package com.yuanlrc.base.config.admin;
/**
 * 用来配置拦截器的配置类
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yuanlrc.base.constant.RuntimeConstant;
import com.yuanlrc.base.interceptor.admin.AdminAuthorityInterceptor;
import com.yuanlrc.base.interceptor.admin.AdminLoginInterceptor;
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;

    @Autowired
    private AdminAuthorityInterceptor adminAuthorityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoginInterceptor).addPathPatterns("/**").excludePathPatterns(RuntimeConstant.loginExcludePathPatterns);
        registry.addInterceptor(adminAuthorityInterceptor).addPathPatterns("/**").excludePathPatterns(RuntimeConstant.authorityExcludePathPatterns);
    }

}
