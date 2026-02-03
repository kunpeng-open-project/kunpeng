package com.kp.framework.modules.week.util;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kp.framework.modules.monthly.mapper.MonthlyReportMapper;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import com.kp.framework.modules.week.enums.WeeklyPalanStatusEnum;
import com.kp.framework.modules.week.mapper.WeeklyPalanMapper;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import com.kp.framework.utils.kptool.KPStringUtil;
import com.kp.framework.utils.kptool.KPThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class WeeklyUtil {

    @Autowired
    private MonthlyReportMapper monthlyReportMapper;

    @Autowired
    private WeeklyPalanMapper weeklyPalanMapper;

    /**
     * 计算月计划进度。
     * @author lipeng
     * 2025/9/28
     * @param monthlyId 月计划ID
     */
    @Async
    @Transactional
    public void calculateMonthlyPlanprogress(String monthlyId) {
        // 等待2秒，确保主方法事务已经提交 防止出现数据错乱
        KPThreadUtil.sleep(2000);

        //查询月计划
        MonthlyReportPO monthlyReportPO = monthlyReportMapper.selectById(monthlyId);
        if (monthlyReportPO == null) return;

        //查询拆分的周计划
        List<WeeklyPalanPO> weeklyPalanList = weeklyPalanMapper.selectList(Wrappers.lambdaQuery(WeeklyPalanPO.class)
                .eq(WeeklyPalanPO::getMonthlyId, monthlyId)
                .ne(WeeklyPalanPO::getTaskStatus, WeeklyPalanStatusEnum.DISCARD.code()));

        if (KPStringUtil.isEmpty(weeklyPalanList)) {
            monthlyReportMapper.updateById(new MonthlyReportPO()
                    .setMonthlyId(monthlyId)
                    .setProgress(0));
            return;
        }

        // 计算完成度
        int totalCount = weeklyPalanList.size();
        long completedCount = weeklyPalanList.stream()
                .filter(weeklyPalanPO -> weeklyPalanPO.getTaskStatus().equals(WeeklyPalanStatusEnum.COMPLETED.code()))
                .count();

        int progress = (int) ((double) completedCount / totalCount * 100);
        monthlyReportMapper.updateById(new MonthlyReportPO()
                .setMonthlyId(monthlyId)
                .setProgress(progress));
    }
}
