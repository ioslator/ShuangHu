package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.model.Employee;
import com.shaunghu_hrms.shuanghu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api") // 这里匹配前端的 /api 前缀
@CrossOrigin
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // ✅ 匹配前端 fetch('/api/search?keyword=...')
    @PostMapping("/search")
    public Result<List<Map<String, Object>>> search(@RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = employeeService.search(keyword);
        return Result.success("查询成功", list);
    }

    // ✅ 匹配前端可能的添加请求 (employee_add.html)
    @PostMapping("/employee/add")
    public Result<String> add(@RequestBody Employee employee) {
        String msg = employeeService.addEmployee(employee);
        if ("success".equals(msg)) {
            return Result.success("添加成功", null);
        } else {
            return Result.error(msg);
        }
    }
}