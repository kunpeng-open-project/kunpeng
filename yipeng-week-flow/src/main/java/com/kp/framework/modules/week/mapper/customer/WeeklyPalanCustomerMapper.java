package com.kp.framework.modules.week.mapper.customer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kp.framework.modules.week.mapper.WeeklyPalanMapper;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import com.kp.framework.modules.week.po.customer.WeeklyPalanCustomerCustomerPO;
import com.kp.framework.modules.week.po.customer.WeellyTaskSummaryCustomerPO;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * @Author lipeng
 * @Description 周计划表 Mapper 接口
 * @Date 2025-09-21 00:04:02
**/
@Mapper
@Primary
@CacheNamespaceRef(name = "com.kp.framework.modules.week.mapper.WeeklyPalanMapper")
public interface WeeklyPalanCustomerMapper extends WeeklyPalanMapper {


    /**
     * @Author lipeng
     * @Description 查询计划拆分统计结果
     * @Date 2025/9/23
     * @param queryWrapper
     * @return java.util.Map<java.lang.String,java.util.List<com.kp.framework.modules.week.po.WeeklyPalanPO>>
     **/
    List<WeeklyPalanCustomerCustomerPO> queryWeeklyPalanCustomer(@Param(Constants.WRAPPER) Wrapper<WeeklyPalanPO> queryWrapper);

    /**
     * @Author lipeng
     * @Description 查询周计划统计数
     * @Date 2025/9/26
     * @param queryWrapper
     * @return java.util.List<com.kp.framework.modules.week.po.customer.WeellyTaskSummaryCustomerPO>
     **/
    WeellyTaskSummaryCustomerPO queryWeeklyNumber(@Param(Constants.WRAPPER) Wrapper<WeeklyPalanPO> queryWrapper);
}
