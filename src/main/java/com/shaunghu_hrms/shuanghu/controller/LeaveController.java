package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.model.EmpLeave;
import com.shaunghu_hrms.shuanghu.mapper.EmpLeaveMapper; // 假设你有这个Mapper
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    @Autowired
    private EmpLeaveMapper empLeaveMapper; // 直接注入Mapper方便演示，正规项目请用Service

    // ✅ 1. 员工提交请假申请
    @PostMapping("/apply")
    public Result<String> apply(@RequestBody EmpLeave leave) {
        // 设置默认值
        leave.setStatus(0); // 默认为 0 (待审批)
        leave.setOperator_id(null); // 申请时还没人操作

        int rows = empLeaveMapper.insert(leave);
        if (rows > 0) {
            return Result.success("申请提交成功，请等待审批");
        }
        return Result.error("提交失败");
    }

    // ✅ 2. 管理员获取所有请假记录
    @GetMapping("/list")
    public Result<List<EmpLeave>> list() {
        // 查询所有记录，按时间倒序
        // 如果是用 MyBatis-Plus:
        // List<EmpLeave> list = empLeaveMapper.selectList(new QueryWrapper<EmpLeave>().orderByDesc("leave_id"));
        List<EmpLeave> list = empLeaveMapper.selectList(null);
        return Result.success(list);
    }

    // ✅ 3. 管理员审批 (通过/驳回)
    // 前端传参: { "leave_id": 101, "status": 1, "operator_id": 999 }
    @PostMapping("/audit")
    public Result<String> audit(@RequestBody EmpLeave leave) {
        if (leave.getLeave_id() == null || leave.getStatus() == null) {
            return Result.error("参数不完整");
        }

        // 只更新状态和操作人
        int rows = empLeaveMapper.updateById(leave);

        if (rows > 0) {
            String msg = (leave.getStatus() == 1) ? "已通过" : "已驳回";
            return Result.success(msg);
        }
        return Result.error("操作失败");
    }
}