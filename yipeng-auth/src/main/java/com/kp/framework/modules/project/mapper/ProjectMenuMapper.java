package com.kp.framework.modules.project.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.project.po.ProjectMenuPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目菜单关联表 Mapper 接口。
 * @author lipeng
 * 2025-05-16
 */
@Mapper
public interface ProjectMenuMapper extends ParentMapper<ProjectMenuPO> {

}
