package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.mapper.DeptMapper;
import com.shaunghu_hrms.shuanghu.model.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dept")
@CrossOrigin
public class DeptController {

    @Autowired
    private DeptMapper deptMapper;

    @GetMapping("/list")
    public Result<List<Dept>> list() {
        return Result.success("查询成功", deptMapper.findAll());
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Dept dept) {
        // 使用 getDept_name()
        if(dept.getDept_name() == null || dept.getDept_name().isEmpty()) {
            return Result.error("部门名称不能为空");
        }
        deptMapper.insert(dept);
        return Result.success("添加成功", null);
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody Dept dept) {
        deptMapper.update(dept);
        return Result.success("修改成功", null);
    }

    @GetMapping("/delete")
    public Result<String> delete(@RequestParam Integer id) {
        deptMapper.delete(id);
        return Result.success("删除成功", null);
    }
}