package com.shaunghu_hrms.shuanghu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.config.SessionManager;
import com.shaunghu_hrms.shuanghu.mapper.SysUserMapper; // 引入 Mapper
import com.shaunghu_hrms.shuanghu.model.SysUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {

    // ⚠️ 改动点：直接注入 Mapper，不走 Service，防止你的 Service 没有对应方法
    @Autowired
    private SysUserMapper sysUserMapper;

    // --- 登录接口 ---
    @PostMapping("/login")
    public Result<SysUser> login(@RequestBody Map<String, Object> params, HttpSession session) {
        // 1. 获取参数
        String username = (String) params.get("username");
        String password = (String) params.get("password");

        // 2. 转换角色 (防止前端传空或其他类型)
        Integer role = null;
        try {
            Object roleObj = params.get("role");
            if (roleObj != null) {
                role = Integer.parseInt(roleObj.toString());
            }
        } catch (Exception e) {
            // 忽略错误，role 保持为 null
        }

        if (role == null) {
            return Result.error("请选择登录身份");
        }

        // 3. 第一步：只根据“用户名”去查人
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.eq("username", username);

        // 使用 Mapper 直接查询，这步绝对不会报错
        SysUser user = sysUserMapper.selectOne(query);

        // 4. 第二步：判断账号是否存在
        if (user == null) {
            return Result.error("账号不存在"); // 弹窗提示1
        }

        // 5. 第三步：判断密码是否正确
        if (!user.getPassword().equals(password)) {
            return Result.error("密码错误"); // 弹窗提示2
        }

        // 6. 第四步：判断角色是否匹配 (数据库里的 1,2,3 和 前端选的 role 比对)
        if (!user.getUser_role().equals(role)) {
            return Result.error("您的身份角色不匹配"); // 弹窗提示3
        }

        // 7. 全部通过
        user.setPassword(null); // 抹除密码，安全返回
        
        // 保存用户信息到session
        SessionManager.saveUserToSession(session, user);
        
        return Result.success("登录成功", user);
    }

    // --- 注册接口 ---
    @PostMapping("/register")
    public Result<String> register(@RequestBody SysUser user) {
        // 检查用户名是否重复
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.eq("username", user.getUsername());
        if (sysUserMapper.selectCount(query) > 0) {
            return Result.error("该用户名已被注册");
        }

        // 默认值设置
        if(user.getUser_role() == null) user.setUser_role(3); // 默认普通员工
        if(user.getUser_status() == null) user.setUser_status(1); // 默认启用

        int rows = sysUserMapper.insert(user); // 使用 Mapper 插入
        if (rows > 0) {
            return Result.success("注册成功", null);
        }
        return Result.error("注册失败");
    }
    
    // --- 登出接口 ---
    @PostMapping("/logout")
    public Result<String> logout(HttpSession session) {
        // 清除用户会话信息
        SessionManager.removeUserFromSession(session);
        return Result.success("登出成功", null);
    }
}