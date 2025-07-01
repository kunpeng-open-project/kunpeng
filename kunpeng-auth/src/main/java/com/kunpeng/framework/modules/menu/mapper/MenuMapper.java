package com.kunpeng.framework.modules.menu.mapper;

import com.kunpeng.framework.mapper.ParentMapper;
import com.kunpeng.framework.modules.menu.po.MenuPO;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.caches.ehcache.LoggingEhcache;

/**
 * @Author lipeng
 * @Description 菜单信息表 Mapper 接口
 * @Date 2025-04-11
**/
@Mapper
@CacheNamespace(implementation = LoggingEhcache.class)
public interface MenuMapper extends ParentMapper<MenuPO> {

}
