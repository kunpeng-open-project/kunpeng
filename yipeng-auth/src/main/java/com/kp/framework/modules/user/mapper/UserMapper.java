package com.kp.framework.modules.user.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.user.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息表 Mapper 接口。
 * @author lipeng
 * 2025-04-21
 */
@Mapper
public interface UserMapper extends ParentMapper<UserPO> {

}
