package org.fcs.core.config;

import jakarta.annotation.Resource;
import org.fcs.core.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author alleyf
 * @Date 2023/12/26 14:17
 * @Version 1.0
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;

    /**
     * 向拦截器注册表中添加拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/api/user/login", "/api/user/register");
    }


}
