package com.kunpeng.framework.modules.project.mapper;

import com.kunpeng.framework.common.parent.ParentSecurityMapper;
import com.kunpeng.framework.modules.project.po.AuthProjectMenuPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 项目菜单关联表 Mapper 接口
 * </p>
 *
 * @author lipeng
 * @since 2024-07-10
 */
@Mapper
public interface AuthProjectMenuMapper extends ParentSecurityMapper<AuthProjectMenuPO> {

}
