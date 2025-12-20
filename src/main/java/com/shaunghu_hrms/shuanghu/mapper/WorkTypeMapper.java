package com.shaunghu_hrms.shuanghu.mapper;

import com.shaunghu_hrms.shuanghu.model.WorkType;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface WorkTypeMapper {
    @Select("SELECT * FROM work_type")
    List<WorkType> findAll();

    // 注意：这里的 #{} 里面必须和 Model 里的字段名完全一样
    @Insert("INSERT INTO work_type(work_type_name, work_type_desc, work_type_status) VALUES(#{work_type_name}, #{work_type_desc}, 1)")
    int insert(WorkType workType);

    @Update("UPDATE work_type SET work_type_status = #{status} WHERE work_type_id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);
}