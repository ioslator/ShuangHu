package com.shaunghu_hrms.shuanghu.mapper;

import com.shaunghu_hrms.shuanghu.model.Title;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface TitleMapper {
    // 对应你的数据库表名 title
    @Select("SELECT * FROM title")
    List<Title> findAll();

    // 对应前端修改薪资范围的功能
    @Update("UPDATE title SET title_salary_range = #{range} WHERE title_id = #{id}")
    int updateSalaryRange(@Param("id") Integer id, @Param("range") String range);
}