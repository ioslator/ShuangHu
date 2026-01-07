package com.shaunghu_hrms.shuanghu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaunghu_hrms.shuanghu.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select; // 导入注解
import java.util.List; // 导入List

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    // 自定义关联查询：查询用户表并关联员工表获取姓名
    @Select("SELECT u.*, e.emp_name " +
            "FROM sys_user u " +
            "LEFT JOIN employee e ON u.emp_id = e.emp_id")
    List<SysUser> selectListWithEmpName();

}