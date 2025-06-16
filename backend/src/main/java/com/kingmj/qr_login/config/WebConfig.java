package com.kingmj.qr_login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String[] DEFAULT_VUE_URLS = {"http://localhost:5173", "http://172.30.1.65:5173"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(DEFAULT_VUE_URLS)
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
