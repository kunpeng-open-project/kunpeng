package com.kp.framework.modules.week.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 周计划表 Mapper 接口。
 * @author lipeng
 * 2025-09-21
 */
@Mapper
public interface WeeklyPalanMapper extends ParentMapper<WeeklyPalanPO> {

}
