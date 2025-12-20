package com.shaunghu_hrms.shuanghu.mapper;

import com.shaunghu_hrms.shuanghu.model.Dept;
import com.shaunghu_hrms.shuanghu.model.Title;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CommonMapper {
    @Select("SELECT * FROM dept WHERE dept_status = 1")
    List<Dept> getAllDepts();

    @Select("SELECT * FROM title")
    List<Title> getAllTitles();

    // 仪表盘统计数据
    @Select("SELECT count(*) FROM employee WHERE emp_status = 1")
    int countEmployees();

    @Select("SELECT count(*) FROM dept WHERE dept_status = 1")
    int countDepts();
}