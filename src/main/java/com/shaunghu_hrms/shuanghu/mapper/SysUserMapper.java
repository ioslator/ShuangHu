package com.shaunghu_hrms.shuanghu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaunghu_hrms.shuanghu.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    // 继承了 BaseMapper 后：
    // 1. selectOne() 会自动拥有
    // 2. insert() 会自动拥有
    // 3. selectCount() 会自动拥有
    // 你原来手写的那些 SQL 方法都不需要了，MyBatis-Plus 会自动帮你生成！
}