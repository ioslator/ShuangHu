package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.model.SysUser;
import com.shaunghu_hrms.shuanghu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest; // 1. 导入 HttpServletRequest
import jakarta.servlet.http.HttpSession;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService; // 2. 改用 UserService (包含加密逻辑)

    @PostMapping("/login")
    // 3. 增加 HttpServletRequest 参数，用于操作 Session
    public Result<SysUser> login(@RequestBody Map<String, Object> params, HttpSession session, HttpServletRequest request) {
        String username = (String) params.get("username");
        String password = (String) params.get("password");

        if (username == null || password == null) {
            return Result.error("用户名或密码不能为空");
        }

        // 4. 调用 Service 进行登录 (自动处理 MD5 比对)
        SysUser user = userService.login(username, password);

        if (user != null) {
            // ================= 安全加固重点 =================
            // 防止会话固定攻击 (Session Fixation)
            // 登录成功后，销毁旧 Session，创建新 Session
            session.invalidate();
            HttpSession newSession = request.getSession();
            // ==============================================

            newSession.setAttribute("currentUser", user);

            // 注意：因为 SysUser 中加了 @JSONField(serialize = false)，
            // 这里的 user 对象返回给前端时，password 字段会自动消失，非常安全。
            return Result.success("登录成功", user);
        }

        return Result.error("登录失败，用户名或密码错误");
    }

    @PostMapping("/register")
    public Result<String> register(@RequestBody SysUser user) {
        // 简单的注册接口透传，具体逻辑都在 Service 里（包含加密）
        if (userService.register(user)) {
            return Result.success("注册成功", null);
        }
        return Result.error("注册失败，用户名可能已存在");
    }

    @GetMapping("/logout")
    public Result<String> logout(HttpSession session) {
        session.removeAttribute("currentUser");
        session.invalidate(); // 退出时彻底销毁 Session
        return Result.success("退出成功", null);
    }
}