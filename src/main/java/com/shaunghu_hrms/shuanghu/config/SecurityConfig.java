package com.shaunghu_hrms.shuanghu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

/**
 * Spring Security配置类
 * 配置基本的安全策略，与自定义拦截器配合使用
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 禁用CSRF保护
            .csrf(AbstractHttpConfigurer::disable)
            // 配置授权规则 - 允许所有请求，由拦截器处理具体的安全逻辑
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll()
            )
            // 禁用默认的登录页面和HTTP基本认证
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable);

        return http.build();
    }
}