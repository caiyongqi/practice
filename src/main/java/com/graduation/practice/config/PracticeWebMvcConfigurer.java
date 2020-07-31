package com.graduation.practice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 静态资源映射
@Configuration
public class PracticeWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/photo/**").addResourceLocations("file:F:/IDEA_projects/practice/src/main/resources/static/photo/");
    }
}
