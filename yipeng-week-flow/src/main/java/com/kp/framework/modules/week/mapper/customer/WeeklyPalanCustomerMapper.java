package com.kp.framework.modules.week.mapper.customer;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.kp.framework.modules.week.mapper.WeeklyPalanMapper;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import com.kp.framework.modules.week.po.customer.WeeklyPalanCustomerCustomerPO;
import com.kp.framework.modules.week.po.customer.WeellyTaskSummaryCustomerPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 * 周计划表 Mapper 接口
 * @author lipeng
 * 2025-09-21
 */
@Mapper
@Primary
public interface WeeklyPalanCustomerMapper extends WeeklyPalanMapper {

    /**
     * 查询计划拆分统计结果。
     * @author lipeng
     * 2025/9/23
     * @param queryWrapper 查询参数
     * @return java.util.List<framework.modules.week.po.customer.WeeklyPalanCustomerCustomerPO>
     */
    List<WeeklyPalanCustomerCustomerPO> queryWeeklyPalanCustomer(@Param(Constants.WRAPPER) Wrapper<WeeklyPalanPO> queryWrapper);

    /**
     * 查询周计划统计数。
     * @author lipeng
     * 2025/9/26
     * @param queryWrapper 查询参数
     * @return framework.modules.week.po.customer.WeellyTaskSummaryCustomerPO
     */
    WeellyTaskSummaryCustomerPO queryWeeklyNumber(@Param(Constants.WRAPPER) Wrapper<WeeklyPalanPO> queryWrapper);
}
