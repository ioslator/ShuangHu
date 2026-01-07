package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.mapper.DeptMapper;
import com.shaunghu_hrms.shuanghu.mapper.EmployeeMapper;
import com.shaunghu_hrms.shuanghu.mapper.NoticeMapper;
import com.shaunghu_hrms.shuanghu.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin
public class DashboardController {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private NoticeMapper noticeMapper;

    @GetMapping("/stats")
    public Result<Map<String, Object>> getStats() {
        Map<String, Object> data = new HashMap<>();

        // 1. 真实员工总数
        data.put("totalEmployees", employeeMapper.selectList(null).size());

        // 2. 真实部门总数
        data.put("totalDepts", deptMapper.findAll().size());

        // 3. 真实公告
        List<Notice> allNotices = noticeMapper.searchNotices(null);
        data.put("totalNotices", allNotices.size());

        // 4. 最新5条公告
        List<Notice> recentNotices = allNotices.stream()
                .limit(5)
                .collect(Collectors.toList());
        data.put("recentNotices", recentNotices);

        // 5. 真实部门分布数据 (修改点)
        // 直接调用 Mapper 查出来的 List<Map<String, Object>>
        // 格式例如: [{dept_name="技术部", count=10}, ...]
        List<Map<String, Object>> deptStats = deptMapper.countEmpPerDept();
        data.put("deptStats", deptStats);

        return Result.success("获取成功", data);
    }
}