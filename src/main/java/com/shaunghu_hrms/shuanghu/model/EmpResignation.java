package com.shaunghu_hrms.shuanghu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("emp_resignation") // 确保数据库表名是这个
public class EmpResignation {

    @TableId(type = IdType.AUTO)
    private Integer resig_id;

    private Integer emp_id;
    private String type;
    private String reason;
    private String apply_date;
    private String approve_status;

    // Getters and Setters
    public Integer getResig_id() { return resig_id; }
    public void setResig_id(Integer resig_id) { this.resig_id = resig_id; }
    public Integer getEmp_id() { return emp_id; }
    public void setEmp_id(Integer emp_id) { this.emp_id = emp_id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public String getApply_date() { return apply_date; }
    public void setApply_date(String apply_date) { this.apply_date = apply_date; }
    public String getApprove_status() { return approve_status; }
    public void setApprove_status(String approve_status) { this.approve_status = approve_status; }
}