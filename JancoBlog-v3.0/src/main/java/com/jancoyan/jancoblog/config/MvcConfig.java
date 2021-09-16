/**
 * @Author: Yan Jingcun
 * @Date: 2021/9/16
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.jancoblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AllowOriginIntercepter());
    }

}
