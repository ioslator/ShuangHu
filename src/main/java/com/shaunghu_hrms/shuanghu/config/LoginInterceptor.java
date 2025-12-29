package com.shaunghu_hrms.shuanghu.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的URI
        String requestURI = request.getRequestURI();
        logger.info("Intercepting request to: {}", requestURI);
        
        // 定义不需要登录验证的路径
        if (isPublicPath(requestURI)) {
            logger.info("Allowing public path: {}", requestURI);
            return true; // 允许访问
        }
        
        // 检查session中是否有用户登录信息
        Object user = request.getSession().getAttribute("user");
        logger.info("Checking user session: {}", user);
        
        if (user == null) {
            // 如果没有登录，重定向到登录页面
            logger.info("User not logged in, redirecting to login page");
            response.sendRedirect("/DengLu/login.html");
            return false;
        }
        
        logger.info("User is logged in, allowing access");
        return true; // 已登录，允许访问
    }
    
    /**
     * 判断是否为公共路径（不需要登录验证）
     */
    private boolean isPublicPath(String uri) {
        // 登录和注册页面
        if (uri.startsWith("/DengLu/")) {
            return true;
        }
        
        // API登录和注册接口
        if (uri.equals("/api/login") || uri.equals("/api/register")) {
            return true;
        }
        
        // 静态资源
        if (uri.startsWith("/static/") || 
            uri.startsWith("/css/") || 
            uri.startsWith("/js/") || 
            uri.startsWith("/images/") || 
            uri.startsWith("/fonts/")) {
            return true;
        }
        
        // 根路径
        if (uri.equals("/") || uri.equals("/favicon.ico")) {
            return true;
        }
        
        return false;
    }
}