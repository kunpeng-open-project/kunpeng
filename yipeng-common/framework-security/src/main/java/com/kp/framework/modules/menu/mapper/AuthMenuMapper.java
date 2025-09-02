package com.kp.framework.modules.menu.mapper;


import com.kp.framework.common.parent.ParentSecurityMapper;
import com.kp.framework.modules.menu.po.AuthMenuPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 菜单信息表 Mapper 接口
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Mapper
public interface AuthMenuMapper extends ParentSecurityMapper<AuthMenuPO> {

}
