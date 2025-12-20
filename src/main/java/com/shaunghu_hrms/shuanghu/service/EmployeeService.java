package com.shaunghu_hrms.shuanghu.service;

import com.shaunghu_hrms.shuanghu.mapper.EmployeeMapper;
import com.shaunghu_hrms.shuanghu.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    // ✅ 修改点：调用自定义的连表查询方法
    public List<Map<String, Object>> search(String keyword) {
        return employeeMapper.searchEmployees(keyword);
    }

    public String addEmployee(Employee employee) {
        if (employee.getEmp_name() == null || employee.getEmp_no() == null) {
            return "姓名和工号不能为空";
        }
        // 补全默认数据
        if(employee.getCreate_time() == null) employee.setCreate_time(new Date());
        if(employee.getUpdate_time() == null) employee.setUpdate_time(new Date());
        if(employee.getEmp_status() == null) employee.setEmp_status(1);

        int rows = employeeMapper.insert(employee);
        return rows > 0 ? "success" : "插入失败";
    }
}