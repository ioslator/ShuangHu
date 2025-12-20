package com.shaunghu_hrms.shuanghu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaunghu_hrms.shuanghu.mapper.SysUserMapper;
import com.shaunghu_hrms.shuanghu.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private SysUserMapper userMapper;

    // 登录逻辑：改用 MyBatis-Plus 的查询构造器
    public SysUser login(String username, String password) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", password);
        // 使用 selectOne 替代原来的自定义 login 方法
        return userMapper.selectOne(queryWrapper);
    }

    // 注册逻辑：改用 MyBatis-Plus 的 insert 方法
    public boolean register(SysUser user) {
        // 1. 检查用户名是否已存在
        QueryWrapper<SysUser> checkQuery = new QueryWrapper<>();
        checkQuery.eq("username", user.getUsername());

        // selectCount 替代原来的 checkUsername
        if(userMapper.selectCount(checkQuery) > 0) {
            return false;
        }

        // 2. 设置默认状态 (原 SQL 里默认是 1)
        if (user.getUser_status() == null) {
            user.setUser_status(1);
        }

        // 3. 插入数据 (insert 替代原来的 register 方法)
        return userMapper.insert(user) > 0;
    }
}