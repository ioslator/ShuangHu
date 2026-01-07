package com.shaunghu_hrms.shuanghu.config;

import com.shaunghu_hrms.shuanghu.model.SysUser;
import org.springframework.web.servlet.HandlerInterceptor;

// ⚠️ 修正点：将 javax 改为 jakarta
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        SysUser user = (SysUser) session.getAttribute("currentUser");

        if (user == null) {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401, \"msg\":\"未登录或登录已过期，请重新登录\", \"data\":null}");
            return false;
        }
        return true;
    }
}