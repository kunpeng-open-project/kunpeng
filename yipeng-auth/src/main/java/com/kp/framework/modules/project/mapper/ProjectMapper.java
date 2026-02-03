package com.kp.framework.modules.project.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.project.po.ProjectPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 项目表 Mapper 接口。
 * @author lipeng
 * 2025-04-07
 */
@Mapper
public interface ProjectMapper extends ParentMapper<ProjectPO> {

}
