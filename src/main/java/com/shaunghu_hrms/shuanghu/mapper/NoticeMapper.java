package com.shaunghu_hrms.shuanghu.mapper;

import com.shaunghu_hrms.shuanghu.model.Notice;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface NoticeMapper {
    // 1. 查询所有
    @Select("SELECT * FROM notice ORDER BY create_time DESC")
    List<Notice> findAll();

    // 2. 查询详情 (注意 #{notice_id})
    @Select("SELECT * FROM notice WHERE notice_id = #{id}")
    Notice findById(Integer id);

    // 3. 插入 (关键修改：必须用下划线字段名)
    @Insert("INSERT INTO notice(notice_title, notice_content, publish_dept, create_time, status) " +
            "VALUES(#{notice_title}, #{notice_content}, #{publish_dept}, NOW(), 1)")
    int insert(Notice notice);

    // 4. 更新 (关键修改：必须用下划线字段名)
    @Update("UPDATE notice SET notice_title=#{notice_title}, notice_content=#{notice_content}, publish_dept=#{publish_dept} " +
            "WHERE notice_id=#{notice_id}")
    int update(Notice notice);

    // 5. 删除
    @Delete("DELETE FROM notice WHERE notice_id = #{id}")
    int delete(Integer id);
}