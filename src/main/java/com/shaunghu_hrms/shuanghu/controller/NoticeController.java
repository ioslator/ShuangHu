package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.mapper.NoticeMapper;
import com.shaunghu_hrms.shuanghu.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeMapper noticeMapper;

    @GetMapping("/list")
    public Result<List<Notice>> list() {
        return Result.success("查询成功", noticeMapper.findAll());
    }

    @GetMapping("/detail")
    public Result<Notice> detail(@RequestParam Integer id) {
        return Result.success("查询成功", noticeMapper.findById(id));
    }

    @PostMapping("/add")
    public Result<String> add(@RequestBody Notice notice) {
        // 修改点：使用你新定义的下划线 Getter 方法
        if(notice.getNotice_title() == null || notice.getNotice_content() == null) {
            return Result.error("标题或内容不能为空");
        }
        noticeMapper.insert(notice);
        return Result.success("发布成功", null);
    }

    @PostMapping("/update")
    public Result<String> update(@RequestBody Notice notice) {
        noticeMapper.update(notice);
        return Result.success("修改成功", null);
    }

    @PostMapping("/delete")
    public Result<String> delete(@RequestParam Integer id) {
        noticeMapper.delete(id);
        return Result.success("删除成功", null);
    }
}