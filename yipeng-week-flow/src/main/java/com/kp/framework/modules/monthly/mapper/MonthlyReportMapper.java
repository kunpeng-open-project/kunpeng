package com.kp.framework.modules.monthly.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 月度计划表 Mapper 接口。
 * @author lipeng
 * 2025-09-15
 */
@Mapper
public interface MonthlyReportMapper extends ParentMapper<MonthlyReportPO> {

}
