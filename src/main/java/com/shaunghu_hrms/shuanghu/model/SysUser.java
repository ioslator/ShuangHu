package com.shaunghu_hrms.shuanghu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("sys_user") // 确保对应数据库表名
public class SysUser {

    // ✅ 加上这个注解，告诉 MyBatis-Plus 这是主键
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer user_id;
    private String username;
    private String password;
    private Integer emp_id;
    private Integer user_role;
    private Integer user_status;

    // Getters and Setters
    public Integer getUser_id() { return user_id; }
    public void setUser_id(Integer user_id) { this.user_id = user_id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Integer getEmp_id() { return emp_id; }
    public void setEmp_id(Integer emp_id) { this.emp_id = emp_id; }

    public Integer getUser_role() { return user_role; }
    public void setUser_role(Integer user_role) { this.user_role = user_role; }

    public Integer getUser_status() { return user_status; }
    public void setUser_status(Integer user_status) { this.user_status = user_status; }
}