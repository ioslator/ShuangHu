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

    // ✅【关键修复】添加缺失的 search 方法
    // 这个方法调用 Mapper 层的自定义查询
    public List<Map<String, Object>> search(String keyword) {
        return employeeMapper.searchEmployees(keyword);
    }

    // ✅ 之前添加的：根据ID查询（用于编辑回显）
    public Employee getById(Integer id) {
        return employeeMapper.selectById(id);
    }

    // ✅ 之前添加的：更新员工信息
    public boolean updateEmployee(Employee employee) {
        employee.setUpdate_time(new Date());
        return employeeMapper.updateById(employee) > 0;
    }

    // 原有的添加方法
    public String addEmployee(Employee employee) {
        if (employee.getEmp_name() == null || employee.getEmp_no() == null) {
            return "姓名和工号不能为空";
        }
        if(employee.getCreate_time() == null) employee.setCreate_time(new Date());
        if(employee.getUpdate_time() == null) employee.setUpdate_time(new Date());
        if(employee.getEmp_status() == null) employee.setEmp_status(1);

        int rows = employeeMapper.insert(employee);
        return rows > 0 ? "success" : "插入失败";
    }


}