package com.shaunghu_hrms.shuanghu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.mapper.SysUserMapper;
import com.shaunghu_hrms.shuanghu.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserPermissionController {

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 获取所有用户列表
     * @return 用户列表
     */
    @GetMapping("/users")
    public Result<List<SysUser>> getAllUsers() {
        try {
            List<SysUser> users = userMapper.selectList(null);
            return Result.success("获取用户列表成功", users);
        } catch (Exception e) {
            return Result.error("获取用户列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/user/{id}")
    public Result<SysUser> getUserById(@PathVariable Integer id) {
        try {
            SysUser user = userMapper.selectById(id);
            if (user != null) {
                user.setPassword(null); // 不返回密码
                return Result.success("获取用户信息成功", user);
            } else {
                return Result.error("用户不存在");
            }
        } catch (Exception e) {
            return Result.error("获取用户信息失败: " + e.getMessage());
        }
    }

    /**
     * 更新用户权限信息（角色和状态）
     * @param user 用户信息
     * @return 操作结果
     */
    @PostMapping("/user/update")
    public Result<String> updateUser(@RequestBody SysUser user) {
        try {
            // 根据ID更新用户的角色和状态
            SysUser updateUser = new SysUser();
            updateUser.setUser_id(user.getUser_id());
            updateUser.setUser_role(user.getUser_role());
            updateUser.setUser_status(user.getUser_status());
            
            int result = userMapper.updateById(updateUser);
            if (result > 0) {
                return Result.success("用户信息更新成功");
            } else {
                return Result.error("用户信息更新失败");
            }
        } catch (Exception e) {
            return Result.error("更新用户信息失败: " + e.getMessage());
        }
    }

    /**
     * 添加新用户
     * @param user 用户信息
     * @return 操作结果
     */
    @PostMapping("/user/add")
    public Result<String> addUser(@RequestBody SysUser user) {
        try {
            // 检查用户名是否已存在
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username", user.getUsername());
            SysUser existingUser = userMapper.selectOne(queryWrapper);
            
            if (existingUser != null) {
                return Result.error("用户名已存在");
            }
            
            // 设置默认状态为启用(1)
            if (user.getUser_status() == null) {
                user.setUser_status(1);
            }
            
            // 插入新用户
            int result = userMapper.insert(user);
            if (result > 0) {
                return Result.success("用户创建成功");
            } else {
                return Result.error("用户创建失败");
            }
        } catch (Exception e) {
            return Result.error("创建用户失败: " + e.getMessage());
        }
    }
}