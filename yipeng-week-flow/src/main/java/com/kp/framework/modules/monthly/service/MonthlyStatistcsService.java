package com.kp.framework.modules.monthly.service;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.kp.framework.enums.DeleteFalgEnum;
import com.kp.framework.modules.monthly.mapper.MonthlyReportMapper;
import com.kp.framework.modules.monthly.mapper.customer.MonthlyReportCustomerMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import com.kp.framework.modules.monthly.po.customer.MonthlyReportStatisticsCustomerPO;
import com.kp.framework.modules.monthly.po.customer.WeeklyPalanSplitCustomerPO;
import com.kp.framework.modules.monthly.po.param.MonthlyReportListParamPO;
import com.kp.framework.modules.user.po.customer.LoginUserBO;
import com.kp.framework.modules.week.enums.WeeklyPalanStatusEnum;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import com.kp.framework.utils.kptool.KPLocalDateUtil;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


/**
 * 月计划统计相关接口。
 * @author lipeng
 * 2025/9/29
 */
@Service
public class MonthlyStatistcsService {

    @Autowired
    private MonthlyReportCustomerMapper monthlyReportCustomerMapper;

    @Autowired
    private MonthlyReportMapper monthlyReportMapper;

    /**
     * 查询月度计划统计信息。
     * @author lipeng
     * 2025/9/15
     * @param monthlyReportListParamPO 查询参数
     * @return MonthlyReportStatisticsCustomerPO
     */
    public MonthlyReportStatisticsCustomerPO queryReviewStatistics(MonthlyReportListParamPO monthlyReportListParamPO) {
        LambdaQueryWrapper<MonthlyReportPO> queryWrapper = Wrappers.lambdaQuery(MonthlyReportPO.class)
                .between(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getPlanTimes()), MonthlyReportPO::getPlanDate, monthlyReportListParamPO.getPlanTimes() == null ? LocalDate.now() : KPLocalDateUtil.getEffectiveDate(monthlyReportListParamPO.getPlanTimes().get(0)), monthlyReportListParamPO.getPlanTimes() == null ? LocalDate.now() : KPLocalDateUtil.getEffectiveDate(monthlyReportListParamPO.getPlanTimes().get(1)))
                .like(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getProjectName()), MonthlyReportPO::getProjectName, monthlyReportListParamPO.getProjectName())
                .like(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getTaskName()), MonthlyReportPO::getTaskName, monthlyReportListParamPO.getTaskName())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getPriority()), MonthlyReportPO::getPriority, monthlyReportListParamPO.getPriority())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getSource()), MonthlyReportPO::getSource, monthlyReportListParamPO.getSource())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getPostId()), MonthlyReportPO::getPostId, monthlyReportListParamPO.getPostId())
                .eq(KPStringUtil.isNotEmpty(monthlyReportListParamPO.getDeptId()), MonthlyReportPO::getDeptId, monthlyReportListParamPO.getDeptId())
                .eq(MonthlyReportPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code());

        return monthlyReportCustomerMapper.countByReviewMonthStatus(queryWrapper);
    }

    /**
     * 查询本人月计划拆分数。
     * @author lipeng
     * 2025/9/23
     * @param parameter 查询参数
     * @return java.util.List<WeeklyPalanSplitCustomerPO>
     */
    public List<WeeklyPalanSplitCustomerPO> queryWeeklyPalanSplit(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("planTime"), "请选择计划时间");
        KPVerifyUtil.notNull(parameter.getJSONArray("monthlyIds"), "请输入要统计的计划集合");

        MPJLambdaWrapper<MonthlyReportPO> wrapper = new MPJLambdaWrapper<MonthlyReportPO>("mr")
                .select("mr.task_name as taskName")
                .select("count(wp.weekly_id) as taskCount")
                .leftJoin(WeeklyPalanPO.class, "wp", on -> on
                        .eq(WeeklyPalanPO::getMonthlyId, MonthlyReportPO::getMonthlyId)
                        .eq(WeeklyPalanPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code())
                )
                .disableSubLogicDel()
                .in(MonthlyReportPO::getMonthlyId, parameter.getJSONArray("monthlyIds"))
                .eq(MonthlyReportPO::getPlanDate, KPLocalDateUtil.parse(parameter.getString("planTime") + "-01", KPLocalDateUtil.DATE_PATTERN))
                .eq(WeeklyPalanPO::getTaskUserId, LoginUserBO.getLoginUserNotEmpty().getIdentification())
                .ne(WeeklyPalanPO::getTaskStatus, WeeklyPalanStatusEnum.DISCARD.code())
                .groupBy("mr.task_name");
        return monthlyReportMapper.selectJoinList(WeeklyPalanSplitCustomerPO.class, wrapper);
    }

    /**
     * 查询月度计划看板统计信息。
     * @author lipeng
     * 2025/9/29
     * @param parameter 查询参数
     * @return framework.modules.monthly.po.customer.MonthlyReportStatisticsCustomerPO
     */
    public MonthlyReportStatisticsCustomerPO queryStatisticsByBoard(JSONObject parameter) {
        KPVerifyUtil.notNull(parameter.getString("planTime"), "请选择计划时间");

        LambdaQueryWrapper<MonthlyReportPO> queryWrapper = Wrappers.lambdaQuery(MonthlyReportPO.class)
                .eq(MonthlyReportPO::getPlanDate, KPLocalDateUtil.parse(parameter.getString("planTime") + "-01", KPLocalDateUtil.DATE_PATTERN))
                .eq(MonthlyReportPO::getDeleteFlag, DeleteFalgEnum.NORMAL.code());

        return monthlyReportCustomerMapper.countByReviewMonthStatus(queryWrapper);
    }
}
