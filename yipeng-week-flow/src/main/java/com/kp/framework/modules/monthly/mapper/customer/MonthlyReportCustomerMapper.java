package com.kp.framework.modules.monthly.mapper.customer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kp.framework.modules.monthly.mapper.MonthlyReportMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportStatisticsCustomerPO;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;

/**
 * @Author lipeng
 * @Description 月度计划表 Mapper 接口
 * @Date 2025-09-15
**/
@Mapper
@Primary
@CacheNamespaceRef(name = "com.kp.framework.modules.monthly.mapper.MonthlyReportMapper")
public interface MonthlyReportCustomerMapper extends MonthlyReportMapper {


    /**
     * @Author lipeng
     * @Description 统计月度计划数量
     * @Date 2025/9/15
     * @param queryWrapper
     * @return com.kp.framework.modules.monthly.po.customer.MonthlyReportStatisticsCustomerPO
     **/
    MonthlyReportStatisticsCustomerPO countByReviewMonthStatus(@Param(Constants.WRAPPER) Wrapper<MonthlyReportPO> queryWrapper);
}
