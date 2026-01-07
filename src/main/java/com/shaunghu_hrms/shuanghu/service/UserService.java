package com.shaunghu_hrms.shuanghu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.mapper.SysUserMapper;
import com.shaunghu_hrms.shuanghu.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils; // 1. 导入加密工具
import java.nio.charset.StandardCharsets;

@Service
public class UserService {

    @Autowired
    private SysUserMapper userMapper;

    // 2. 辅助方法：生成 MD5 摘要
    private String encryptPassword(String rawPassword) {
        if (rawPassword == null) return null;
        return DigestUtils.md5DigestAsHex(rawPassword.getBytes(StandardCharsets.UTF_8));
    }

    // 登录逻辑：使用加密密码比对
    public SysUser login(String username, String password) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        // 核心修改：数据库存的是密文，所以查询时也要用密文
        queryWrapper.eq("password", encryptPassword(password));
        return userMapper.selectOne(queryWrapper);
    }

    // 注册逻辑：存入加密后的密码
    public boolean register(SysUser user) {
        QueryWrapper<SysUser> checkQuery = new QueryWrapper<>();
        checkQuery.eq("username", user.getUsername());

        if(userMapper.selectCount(checkQuery) > 0) {
            return false;
        }

        if (user.getUser_status() == null) {
            user.setUser_status(1);
        }

        // 核心修改：入库前加密
        user.setPassword(encryptPassword(user.getPassword()));

        return userMapper.insert(user) > 0;
    }

    // 修改密码逻辑：比对旧密文，保存新密文
    public Result<String> changePassword(String username, String oldPwd, String newPwd) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        SysUser user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            return Result.error("用户不存在");
        }

        // 核心修改：比对加密后的旧密码
        if (!user.getPassword().equals(encryptPassword(oldPwd))) {
            return Result.error("原密码错误，请重试");
        }

        // 核心修改：保存加密后的新密码
        user.setPassword(encryptPassword(newPwd));
        int rows = userMapper.updateById(user);

        if (rows > 0) {
            return Result.success("密码修改成功", null);
        } else {
            return Result.error("系统错误，修改失败");
        }
    }
}