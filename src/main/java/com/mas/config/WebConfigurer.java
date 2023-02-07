package com.mas.config;

import com.mas.annotation.Login;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(createLoginInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public LoginInterceptor createLoginInterceptor(){
        return new LoginInterceptor();
    }



}
