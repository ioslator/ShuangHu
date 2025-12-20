package com.shaunghu_hrms.shuanghu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaunghu_hrms.shuanghu.model.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    // 1. 员工列表搜索 (之前写的，保持不变)
    @Select("<script>" +
            "SELECT e.*, d.dept_name, t.title_name " +
            "FROM employee e " +
            "LEFT JOIN dept d ON e.dept_id = d.dept_id " +
            "LEFT JOIN title t ON e.title_id = t.title_id " +
            "WHERE e.emp_status = 1 " +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND (e.emp_name LIKE CONCAT('%',#{keyword},'%') OR e.emp_no LIKE CONCAT('%',#{keyword},'%'))" +
            "</if>" +
            "ORDER BY e.create_time DESC" +
            "</script>")
    List<Map<String, Object>> searchEmployees(@Param("keyword") String keyword);

    // 2. ✅ 新增：个人信息查询 (修复 findProfileByUsername 报错)
    // 逻辑：通过 sys_user 表的 username 找到 emp_id，再查 employee 表
    @Select("SELECT e.*, d.dept_name, t.title_name " +
            "FROM employee e " +
            "INNER JOIN sys_user u ON e.emp_id = u.emp_id " +
            "LEFT JOIN dept d ON e.dept_id = d.dept_id " +
            "LEFT JOIN title t ON e.title_id = t.title_id " +
            "WHERE u.username = #{username}")
    Map<String, Object> findProfileByUsername(@Param("username") String username);

    // 3. ✅ 新增：更新联系方式 (修复 updateContact 报错)
    // 注意：你的 Employee 实体类里好像没有 address 字段，所以这里暂时只更新 phone 和 email
    // 如果你数据库表里确实有 address 字段，可以把 ", address = #{address}" 加到 SQL 里
    @Update("UPDATE employee SET emp_phone = #{phone}, emp_email = #{email} WHERE emp_no = #{empNo}")
    void updateContact(@Param("empNo") String empNo,
                       @Param("phone") String phone,
                       @Param("email") String email,
                       @Param("address") String address);
}