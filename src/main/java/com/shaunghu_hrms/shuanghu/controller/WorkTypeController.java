package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.mapper.WorkTypeMapper;
import com.shaunghu_hrms.shuanghu.model.WorkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/worktype")
@CrossOrigin
public class WorkTypeController {

    @Autowired
    private WorkTypeMapper workTypeMapper;

    @GetMapping("/list")
    public Result<List<WorkType>> list() {
        return Result.success("success", workTypeMapper.findAll());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody WorkType workType) {
        if (workType.getWork_type_name() == null) return Result.error("名称不能为空");
        workTypeMapper.insert(workType);
        return Result.success("添加成功", null);
    }

    @PostMapping("/status")
    public Result<String> updateStatus(@RequestParam Integer id, @RequestParam Integer status) {
        workTypeMapper.updateStatus(id, status);
        return Result.success("状态更新成功", null);
    }
}