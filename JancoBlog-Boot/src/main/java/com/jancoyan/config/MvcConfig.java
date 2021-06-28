/**
 * @Author: Yan Jingcun
 * @Date: 2021/6/28
 * @Description:
 * @Version: 1.0
 */

package com.jancoyan.config;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index.html");
        registry.addViewController("/index.html").setViewName("index");
    }
}
