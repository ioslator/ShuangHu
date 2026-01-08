package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.model.SysUser;
import com.shaunghu_hrms.shuanghu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口：增加了角色一致性校验
     */
    @PostMapping("/login")
    public Result<SysUser> login(@RequestBody Map<String, Object> params, HttpSession session, HttpServletRequest request) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");
        // 获取前端选择的角色 (前端传过来可能是 String 也可能是 Integer，安全起见先转 String 再转 Integer)
        Object roleObj = params.get("role");

        if (username == null || password == null || roleObj == null || "".equals(roleObj.toString())) {
            return Result.error("请填写完整的登录信息（包括登录身份）");
        }

        Integer selectedRole;
        try {
            selectedRole = Integer.parseInt(roleObj.toString());
        } catch (NumberFormatException e) {
            return Result.error("登录身份格式错误");
        }

        // 1. 调用 Service 进行基础登录 (校验用户名和密码)
        SysUser user = userService.login(username, password);

        if (user != null) {
            // ================== 核心修改：严格校验登录身份 ==================
            // 数据库中的角色 (user_role) 必须与前端选择的角色 (selectedRole) 一致
            if (!user.getUser_role().equals(selectedRole)) {
                return Result.error("登录失败：该账号不是所选身份，请重新选择");
            }
            // ==============================================================

            // 2. 防止会话固定攻击
            session.invalidate();
            HttpSession newSession = request.getSession();
            newSession.setAttribute("currentUser", user);

            return Result.success("登录成功", user);
        }

        return Result.error("登录失败，用户名或密码错误");
    }

    /**
     * 注册接口
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody SysUser user) {
        // 调用 Service 处理注册逻辑（加密、排重）
        if (userService.register(user)) {
            return Result.success("注册成功", null);
        }
        return Result.error("注册失败，用户名可能已存在");
    }

    @GetMapping("/logout")
    public Result<String> logout(HttpSession session) {
        session.removeAttribute("currentUser");
        session.invalidate();
        return Result.success("退出成功", null);
    }
}