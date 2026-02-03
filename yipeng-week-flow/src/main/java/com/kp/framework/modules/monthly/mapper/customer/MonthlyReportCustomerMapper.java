package com.kp.framework.modules.monthly.mapper.customer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kp.framework.modules.monthly.mapper.MonthlyReportMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportStatisticsCustomerPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;


/**
 * 月度计划表 Mapper 接口。
 * @author lipeng
 * 2025-09-15
 */
@Mapper
@Primary
public interface MonthlyReportCustomerMapper extends MonthlyReportMapper {

    /**
     * 统计月度计划数量。
     * @author lipeng
     * 2025/9/15
     * @param queryWrapper 查询条件
     * @return framework.modules.monthly.po.customer.MonthlyReportStatisticsCustomerPO
     */
    MonthlyReportStatisticsCustomerPO countByReviewMonthStatus(@Param(Constants.WRAPPER) Wrapper<MonthlyReportPO> queryWrapper);
}
