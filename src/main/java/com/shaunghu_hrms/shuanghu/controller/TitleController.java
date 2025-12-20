package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.mapper.TitleMapper;
import com.shaunghu_hrms.shuanghu.model.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/titles")
public class TitleController {

    @Autowired
    private TitleMapper titleMapper;

    // 1. 获取列表接口
    // 前端 fetch('/api/titles') 会访问这里
    @GetMapping
    public List<Title> list() {
        return titleMapper.findAll();
    }

    // 2. 修改薪资接口
    // 前端 saveData() 会 POST 到这里
    @PostMapping
    public Map<String, Object> update(@RequestParam Integer id, @RequestParam String range) {
        int result = titleMapper.updateSalaryRange(id, range);

        Map<String, Object> res = new HashMap<>();
        if(result > 0) {
            res.put("success", true);
            res.put("message", "修改成功");
        } else {
            res.put("success", false);
            res.put("message", "修改失败");
        }
        return res;
    }
}