package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.common.Result;
import com.shaunghu_hrms.shuanghu.model.Employee;
import com.shaunghu_hrms.shuanghu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// === 新增的导入语句 ===
import jakarta.servlet.http.HttpServletResponse; // 必须导入，用于处理响应流
import java.io.PrintWriter;      // 用于写入 CSV 内容
import java.net.URLEncoder;      // 用于文件名编码，防止中文乱码
import java.text.SimpleDateFormat; // 用于格式化日期
// =====================

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin // 允许跨域
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 搜索接口
    @PostMapping("/search")
    public Result<List<Map<String, Object>>> search(@RequestParam(required = false) String keyword) {
        List<Map<String, Object>> list = employeeService.search(keyword);
        return Result.success("查询成功", list);
    }

    // 获取单个员工（用于编辑回显）
    @GetMapping("/employee/get/{id}")
    public Result<Employee> getEmployee(@PathVariable Integer id) {
        Employee employee = employeeService.getById(id);
        return Result.success("获取成功", employee);
    }

    // 更新员工
    @PostMapping("/employee/update")
    public Result<String> updateEmployee(@RequestBody Employee employee) {
        boolean success = employeeService.updateEmployee(employee);
        return success ? Result.success("更新成功", null) : Result.error("更新失败");
    }

    // ✅ 新增：导出员工数据接口
    @GetMapping("/employee/export")
    public void exportEmployees(HttpServletResponse response) {
        try {
            // 1. 获取所有在职员工数据 (复用 search 方法，传 null 即查全部)
            List<Map<String, Object>> list = employeeService.search(null);

            // 2. 设置响应头
            response.setContentType("text/csv;charset=UTF-8");
            // 对文件名进行 URL 编码，防止中文乱码
            String fileName = URLEncoder.encode("员工档案表.csv", "UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

            // 3. 写入 CSV 内容
            PrintWriter writer = response.getWriter();
            // 写入 BOM 头 (0xFEFF)，让 Excel 能正确识别 UTF-8 编码
            writer.write('\ufeff');

            // 写入表头
            writer.println("工号,姓名,性别,部门,职位,手机号,邮箱,入职日期");

            // 4. 遍历数据并写入
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Map<String, Object> map : list) {
                // 安全获取数据的辅助方法，防止 null 报错
                String empNo = getStr(map, "emp_no");
                String name = getStr(map, "emp_name");
                String gender = getStr(map, "emp_gender");
                String dept = getStr(map, "dept_name");
                String title = getStr(map, "title_name");
                String phone = getStr(map, "emp_phone");
                String email = getStr(map, "emp_email");

                // 处理日期
                Object hireDateObj = map.get("hire_date");
                String hireDate = (hireDateObj != null) ? sdf.format(hireDateObj) : "";

                // 写入一行 (注意：如果字段内容本身包含逗号，CSV格式会乱，这里假设内容简单)
                writer.printf("%s,%s,%s,%s,%s,%s,%s,%s%n",
                        empNo, name, gender, dept, title, phone, email, hireDate);
            }

            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
            // 实际项目中建议记录日志
        }
    }

    // 辅助方法：处理 Map 中的 null 值，避免空指针异常
    private String getStr(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val == null ? "" : val.toString();
    }
}