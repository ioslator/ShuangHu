package com.shaunghu_hrms.shuanghu.config;

import com.shaunghu_hrms.shuanghu.model.SysUser;
import jakarta.servlet.http.HttpSession;

public class SessionManager {
    
    private static final String USER_SESSION_KEY = "user";
    
    /**
     * 保存用户信息到Session
     */
    public static void saveUserToSession(HttpSession session, SysUser user) {
        // 直接保存用户信息到session，移除密码
        SysUser userForSession = new SysUser();
        userForSession.setUser_id(user.getUser_id());
        userForSession.setUsername(user.getUsername());
        userForSession.setUser_role(user.getUser_role());
        userForSession.setUser_status(user.getUser_status());
        userForSession.setEmp_id(user.getEmp_id()); // 关联员工ID
        
        session.setAttribute(USER_SESSION_KEY, userForSession);
    }
    
    /**
     * 从Session获取用户信息
     */
    public static SysUser getUserFromSession(HttpSession session) {
        return (SysUser) session.getAttribute(USER_SESSION_KEY);
    }
    
    /**
     * 检查用户是否已登录
     */
    public static boolean isUserLoggedIn(HttpSession session) {
        return getUserFromSession(session) != null;
    }
    
    /**
     * 从Session中移除用户信息（登出）
     */
    public static void removeUserFromSession(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
    }
}