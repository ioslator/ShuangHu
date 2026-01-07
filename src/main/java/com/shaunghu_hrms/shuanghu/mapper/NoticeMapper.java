package com.shaunghu_hrms.shuanghu.mapper;

import com.shaunghu_hrms.shuanghu.model.Notice;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface NoticeMapper {

    // ✅ 1. 搜索功能 (核心修改)
    // 使用 <script> 标签实现动态 SQL：如果有 keyword 就模糊查，没有就查所有
    @Select("<script>" +
            "SELECT * FROM notice " +
            "WHERE 1=1 " +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND notice_title LIKE CONCAT('%', #{keyword}, '%') " +
            "</if>" +
            "ORDER BY create_time DESC" +
            "</script>")
    List<Notice> searchNotices(@Param("keyword") String keyword);

    // 2. 查询详情 (被 Controller 的 /get 接口调用)
    @Select("SELECT * FROM notice WHERE notice_id = #{id}")
    Notice findById(Integer id);

    // 3. 插入
    @Insert("INSERT INTO notice(notice_title, notice_content, publish_dept, create_time, status) " +
            "VALUES(#{notice_title}, #{notice_content}, #{publish_dept}, NOW(), 1)")
    int insert(Notice notice);

    // 4. 更新
    @Update("UPDATE notice SET notice_title=#{notice_title}, notice_content=#{notice_content}, publish_dept=#{publish_dept} " +
            "WHERE notice_id=#{notice_id}")
    int update(Notice notice);

    // 5. 删除
    @Delete("DELETE FROM notice WHERE notice_id = #{id}")
    int delete(Integer id);
}