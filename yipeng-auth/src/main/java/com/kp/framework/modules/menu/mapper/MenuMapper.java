package com.kp.framework.modules.menu.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.menu.po.MenuPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单信息表 Mapper 接口。
 * @author lipeng
 * 2025-04-11
 */
@Mapper
public interface MenuMapper extends ParentMapper<MenuPO> {

}
