package com.kp.framework.modules.report.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.modules.monthly.enums.MonthlyReportStatusEnum;
import com.kp.framework.modules.monthly.mapper.MonthlyReportMapper;
import com.kp.framework.modules.monthly.mapper.customer.MonthlyReportCustomerMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportStatisticsCustomerPO;
import com.kp.framework.modules.monthly.po.customer.WeeklyPalanSplitCustomerPO;
import com.kp.framework.modules.report.po.customer.StatisticalCustomerPO;
import com.kp.framework.modules.week.enums.WeeklyPalanStatusEnum;
import com.kp.framework.modules.week.mapper.customer.WeeklyPalanCustomerMapper;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import com.kp.framework.modules.week.po.customer.WeeklyPalanCustomerCustomerPO;
import com.kp.framework.modules.week.po.customer.WeellyTaskSummaryCustomerPO;
import com.kp.framework.utils.kptool.KPLocalDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


@Service
public class WelcomeService {

    @Autowired
    private MonthlyReportCustomerMapper monthlyReportCustomerMapper;

    @Autowired
    private WeeklyPalanCustomerMapper weeklyPalanCustomerMapper;

    @Autowired
    private MonthlyReportMapper monthlyReportMapper;

    /**
     * @Author lipeng
     * @Description 查询首页看板统计信息
     * @Date 2025/10/2
     * @param
     * @return com.kp.framework.modules.report.po.customer.StatisticalCustomerPO
     **/
    public StatisticalCustomerPO queryStatistical() {
        MonthlyReportStatisticsCustomerPO monthlyReportStatisticsCustomerPO = monthlyReportCustomerMapper.countByReviewMonthStatus(Wrappers.lambdaQuery(MonthlyReportPO.class)
                .eq(MonthlyReportPO::getPlanDate, KPLocalDateUtil.getFirstDayOfMonth(LocalDate.now()))
                .eq(MonthlyReportPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code()));


        WeellyTaskSummaryCustomerPO weellyTaskSummaryCustomerPO = weeklyPalanCustomerMapper.queryWeeklyNumber(Wrappers.lambdaQuery(WeeklyPalanPO.class)
                .eq(WeeklyPalanPO::getMonthlyPlanDate, KPLocalDateUtil.getFirstDayOfMonth(LocalDate.now()))
                .eq(WeeklyPalanPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code()));

        return new StatisticalCustomerPO()
                .setMonthly(monthlyReportStatisticsCustomerPO)
                .setWeek(weellyTaskSummaryCustomerPO);
    }


    /**
     * @Author lipeng
     * @Description 查询首页月计划拆分完成度
     * @Date 2025/10/3
     * @param
     * @return java.util.List<com.kp.framework.modules.week.po.customer.WeeklyPalanCustomerCustomerPO>
     **/
    public List<WeeklyPalanCustomerCustomerPO> queryMonthlyCmpletion() {
        LambdaQueryWrapper<WeeklyPalanPO> wrappers = Wrappers.lambdaQuery(WeeklyPalanPO.class)
                .eq(WeeklyPalanPO::getMonthlyPlanDate, KPLocalDateUtil.getFirstDayOfMonth(LocalDate.now()))
                .ne(WeeklyPalanPO::getTaskStatus, WeeklyPalanStatusEnum.DISCARD.code())
                .eq(WeeklyPalanPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code());

        return weeklyPalanCustomerMapper.queryWeeklyPalanCustomer(wrappers);
    }


    /**
     * @Author lipeng
     * @Description 查询首页月计划拆分数
     * @Date 2025/10/3
     * @param
     * @return java.util.List<com.kp.framework.modules.monthly.po.customer.WeeklyPalanSplitCustomerPO>
     **/
    public List<WeeklyPalanSplitCustomerPO> queryMonthlySplit() {
        MPJLambdaWrapper<MonthlyReportPO> wrapper = new MPJLambdaWrapper<MonthlyReportPO>("mr")
                .select("mr.task_name as taskName")
                .select("count(wp.weekly_id) as taskCount")
                .leftJoin(WeeklyPalanPO.class, "wp", on -> on
                        .eq(WeeklyPalanPO::getMonthlyId, MonthlyReportPO::getMonthlyId)
                        .eq(WeeklyPalanPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                        .ne(WeeklyPalanPO::getTaskStatus, WeeklyPalanStatusEnum.DISCARD.code()) // 移到这里
                )
                .disableSubLogicDel()
                .eq(MonthlyReportPO::getPlanDate, KPLocalDateUtil.getFirstDayOfMonth(LocalDate.now()))
                .in(MonthlyReportPO::getStatus, Arrays.asList(
                        MonthlyReportStatusEnum.REVIEW_PASSED_WAITING_FOR_SPLIT.code(),
                        MonthlyReportStatusEnum.SPLITTED_IN_PROGRESS.code(),
                        MonthlyReportStatusEnum.COMPLETED.code(),
                        MonthlyReportStatusEnum.OVERDUE.code()
                ))
                .groupBy("mr.task_name");
        return monthlyReportMapper.selectJoinList(WeeklyPalanSplitCustomerPO.class, wrapper);
    }
}
