package com.shaunghu_hrms.shuanghu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaunghu_hrms.shuanghu.model.EmpLeave;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmpLeaveMapper extends BaseMapper<EmpLeave> {

    // BaseMapper 已经自动包含了：
    // insert (提交申请)
    // updateById (审批修改状态)
    // selectList (查询列表)
    // selectById (查询详情)
    // deleteById (删除)

    // 如果未来你需要“查询带有员工名字的请假列表”（因为 emp_leave 表里只有 emp_id，没有名字），
    // 你可能需要这样一个关联查询的方法：
    @Select("SELECT l.*, e.emp_name, d.dept_name " +
            "FROM emp_leave l " +
            "LEFT JOIN employee e ON l.emp_id = e.emp_id " +
            "LEFT JOIN dept d ON e.dept_id = d.dept_id " +
            "ORDER BY l.leave_date DESC")
    List<Map<String, Object>> selectLeaveWithEmpInfo();
}