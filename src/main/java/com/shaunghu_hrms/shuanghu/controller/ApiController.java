package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.mapper.CommonMapper;
import com.shaunghu_hrms.shuanghu.model.Dept;
// import com.shaunghu_hrms.shuanghu.model.Title; // 这个引用也可以删掉了
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {

    @Autowired
    private CommonMapper commonMapper;

    @GetMapping("/departments")
    public Result<List<Dept>> getDepartments() {
        return Result.success("查询成功", commonMapper.getAllDepts());
    }

    // ❌❌❌【删除下面这段代码】❌❌❌
    // 因为新的 TitleController 已经接管了这个功能
    // @GetMapping("/titles")
    // public Result<List<Title>> getTitles() {
    //     return Result.success("查询成功", commonMapper.getAllTitles());
    // }

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> getDashboard() {
        Map<String, Object> map = new HashMap<>();
        map.put("totalEmployees", commonMapper.countEmployees());
        map.put("totalDepartments", commonMapper.countDepts());
        map.put("todayAttendance", 0);
        return Result.success("获取成功", map);
    }
}