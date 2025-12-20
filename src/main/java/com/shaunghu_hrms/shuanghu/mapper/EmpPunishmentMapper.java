package com.shaunghu_hrms.shuanghu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaunghu_hrms.shuanghu.model.EmpPunishment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmpPunishmentMapper extends BaseMapper<EmpPunishment> {
    // 既然只显示 emp_id，这里什么都不用写，BaseMapper 自带了所有功能
}