/**
 * @Author: Yan Jingcun
 * @Date: 2021/6/29
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 视图解析器
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
    }

    // 登录拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/",
                        "/index.html", "/user/login", "/login.html", "/articleimages/**",
                        "/css/**", "/fonts/**", "/images/**", "/js/**", "/lib/**", "/public/**",
                        "/article/**", "/search.html", "/article.html", "/content/**",
                        "/comment/**", "/sort.html");
    }
}
