package com.shaunghu_hrms.shuanghu.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

@TableName("emp_leave")
public class EmpLeave {

    // ✅ 必须加上 @TableId，告诉程序这是主键
    @TableId(type = IdType.AUTO)
    private Integer leave_id;

    private Integer emp_id;
    private Integer leave_type;
    private String leave_reason;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leave_date;
    private Integer operator_id;

    // ✅ 之前加的状态字段
    private Integer status;

    // Getters and Setters ...
    public Integer getLeave_id() { return leave_id; }
    public void setLeave_id(Integer leave_id) { this.leave_id = leave_id; }

    public Integer getEmp_id() { return emp_id; }
    public void setEmp_id(Integer emp_id) { this.emp_id = emp_id; }

    public Integer getLeave_type() { return leave_type; }
    public void setLeave_type(Integer leave_type) { this.leave_type = leave_type; }

    public String getLeave_reason() { return leave_reason; }
    public void setLeave_reason(String leave_reason) { this.leave_reason = leave_reason; }

    public Date getLeave_date() { return leave_date; }
    public void setLeave_date(Date leave_date) { this.leave_date = leave_date; }

    public Integer getOperator_id() { return operator_id; }
    public void setOperator_id(Integer operator_id) { this.operator_id = operator_id; }

    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}