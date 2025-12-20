package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping("/info")
    public Result<Map<String, Object>> getInfo(@RequestParam String username) {
        Map<String, Object> info = employeeMapper.findProfileByUsername(username);
        if(info == null) return Result.error("未找到员工档案");
        return Result.success("获取成功", info);
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody Map<String, String> params) {
        String empNo = params.get("emp_no");
        String phone = params.get("emp_phone");
        String email = params.get("emp_email");
        String address = params.get("emp_address");

        employeeMapper.updateContact(empNo, phone, email, address);
        return Result.success("修改成功", null);
    }
}