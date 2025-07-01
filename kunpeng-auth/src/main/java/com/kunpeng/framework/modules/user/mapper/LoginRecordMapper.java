package com.kunpeng.framework.modules.user.mapper;

import com.kunpeng.framework.mapper.ParentMapper;
import com.kunpeng.framework.modules.user.po.LoginRecordPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lipeng
 * @Description 用户登录记录表 Mapper 接口
 * @Date 2025-06-10 17:25:05
**/
@Mapper
public interface LoginRecordMapper extends ParentMapper<LoginRecordPO> {

}
