package com.kp.framework.modules.role.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.role.po.RoleMenuPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色菜单关联表 Mapper 接口。
 * @author lipeng
 * 2025-04-20
 */
@Mapper
public interface RoleMenuMapper extends ParentMapper<RoleMenuPO> {

}
