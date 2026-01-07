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

    // ✅ 1. 列表查询 (支持搜索)
    @GetMapping("/list")
    public Result<List<Notice>> list(@RequestParam(required = false) String keyword) {
        // 调用 Mapper 新加的 searchNotices 方法
        List<Notice> list = noticeMapper.searchNotices(keyword);
        return Result.success("查询成功", list);
    }

    // ✅ 2. 获取详情 (用于查看和编辑回显)
    // 前端 HTML 使用的是 /api/notice/get?id=...
    @GetMapping("/get")
    public Result<Notice> getById(@RequestParam Integer id) {
        // 修复报错：这里调用 findById 而不是 selectById
        Notice notice = noticeMapper.findById(id);
        if (notice != null) {
            return Result.success("获取成功", notice);
        }
        return Result.error("未找到公告");
    }

    // ✅ 3. 提交修改
    @PostMapping("/update")
    public Result<String> update(@RequestBody Notice notice) {
        if (notice.getNotice_id() == null) {
            return Result.error("ID不能为空");
        }
        int rows = noticeMapper.update(notice);
        return rows > 0 ? Result.success("修改成功", null) : Result.error("修改失败");
    }

    // 4. 发布公告 (保留)
    @PostMapping("/add")
    public Result<String> add(@RequestBody Notice notice) {
        if(notice.getNotice_title() == null) {
            return Result.error("标题不能为空");
        }
        noticeMapper.insert(notice);
        return Result.success("发布成功", null);
    }

    // 5. 删除公告 (保留)
    @PostMapping("/delete")
    public Result<String> delete(@RequestParam Integer id) {
        noticeMapper.delete(id);
        return Result.success("删除成功", null);
    }
}