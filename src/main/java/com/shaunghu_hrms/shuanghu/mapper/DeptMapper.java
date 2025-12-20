package com.shaunghu_hrms.shuanghu.mapper;

import com.shaunghu_hrms.shuanghu.model.Dept;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface DeptMapper {
    // 查询所有部门
    @Select("SELECT * FROM dept")
    List<Dept> findAll();

    // 插入部门 (使用 #{dept_name} 等)
    @Insert("INSERT INTO dept(dept_name, dept_leader, dept_phone, dept_status) " +
            "VALUES(#{dept_name}, #{dept_leader}, #{dept_phone}, 1)")
    int insert(Dept dept);

    // 更新部门 (使用 #{dept_id} 等)
    @Update("UPDATE dept SET dept_name=#{dept_name}, dept_leader=#{dept_leader}, dept_phone=#{dept_phone} " +
            "WHERE dept_id=#{dept_id}")
    int update(Dept dept);

    // 删除部门
    @Delete("DELETE FROM dept WHERE dept_id = #{id}")
    int delete(Integer id);
}