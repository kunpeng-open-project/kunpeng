package com.kp.framework.modules.monthly.mapper;

import com.kp.framework.mapper.ParentMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportUserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 月度计划责任人信息表 Mapper 接口。
 * @author lipeng
 * 2025-07-25
 */
@Mapper
public interface MonthlyReportUserMapper extends ParentMapper<MonthlyReportUserPO> {

}
