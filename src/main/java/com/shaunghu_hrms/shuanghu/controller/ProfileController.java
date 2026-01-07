package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.mapper.EmployeeMapper;
import com.shaunghu_hrms.shuanghu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
// ✅ 新增：导入文件上传必须的包
import org.springframework.web.multipart.MultipartFile;

// ✅ 新增：导入 IO 流和工具类包
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin
public class ProfileController {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private UserService userService;
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
        String avatar = params.get("avatar"); // 获取头像URL

        // 这里的参数列表要和 Mapper 里的 updateContact 一致
        employeeMapper.updateContact(empNo, phone, email, avatar);
        return Result.success("修改成功", null);
    }

    @PostMapping("/upload_avatar")
    public Result<String> uploadAvatar(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请选择图片");
        }

        try {
            // 1. 准备保存路径 (你的指定路径 + user_avatars 子目录)
            String uploadDir = "F:/IDEA/IdeaProject/ShuangHu/user_avatars/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs(); // 如果目录不存在，自动创建
            }

            // 2. 生成唯一文件名 (防止重名覆盖)
            String originalFilename = file.getOriginalFilename();
            // 防止文件没有后缀名的情况
            String suffix = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            } else {
                suffix = ".jpg"; // 默认后缀
            }
            String newFileName = UUID.randomUUID().toString() + suffix;

            // 3. 保存文件到硬盘
            File dest = new File(uploadDir + newFileName);
            file.transferTo(dest);

            // 4. 返回可访问的 URL (对应 WebConfig 中的映射)
            String fileUrl = "/profile_photos/" + newFileName;
            return Result.success("上传成功", fileUrl);

        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传失败：" + e.getMessage());
        }
    }

    @PostMapping("/change_password")
    public Result<String> changePassword(@RequestBody Map<String, String> params) {
        String username = params.get("username");
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");

        if (username == null || oldPwd == null || newPwd == null) {
            return Result.error("参数不完整");
        }

        // 调用 Service 进行处理
        return userService.changePassword(username, oldPwd, newPwd);
    }
}