package com.kp.framework.modules.role.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.role.po.RolePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色信息表 Mapper 接口。
 * @author lipeng
 * 2025-04-07
 */
@Mapper
public interface RoleMapper extends ParentMapper<RolePO> {

}
