package com.shaunghu_hrms.shuanghu.controller;

import com.shaunghu_hrms.shuanghu.mapper.EmpPunishmentMapper;
import com.shaunghu_hrms.shuanghu.mapper.EmpResignationMapper;
import com.shaunghu_hrms.shuanghu.model.EmpPunishment;
import com.shaunghu_hrms.shuanghu.model.EmpResignation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RenShiController {

    @Autowired
    private EmpPunishmentMapper punishMapper;

    @Autowired
    private EmpResignationMapper resigMapper;

    // 1. 处分记录接口
    @GetMapping("/punishments")
    public List<EmpPunishment> listPunishments() {
        // 直接查表，实体类里有 emp_id，前端直接显示
        return punishMapper.selectList(null);
    }

    // 2. 离职记录接口
    @GetMapping("/resignations")
    public List<EmpResignation> listResignations() {
        return resigMapper.selectList(null);
    }
}