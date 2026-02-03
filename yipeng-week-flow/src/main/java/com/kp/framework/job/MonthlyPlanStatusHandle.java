package com.kp.framework.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.kp.framework.modules.monthly.enums.MonthlyReportStatusEnum;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import com.kp.framework.modules.monthly.service.MonthlyReportService;
import com.kp.framework.utils.kptool.KPJsonUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理预期的计划。
 * @author lipeng
 * 2025/10/14
 */
@Slf4j
@Component
public class MonthlyPlanStatusHandle {

    @Autowired
    private MonthlyReportService monthlyReportService;

    @XxlJob("monthlyPlanStatus")
    public void handle() {
        List<MonthlyReportPO> monthlyReportPOList = monthlyReportService.getBaseMapper().selectList(Wrappers.lambdaQuery(MonthlyReportPO.class)
                .lt(MonthlyReportPO::getEndDate, LocalDate.now())
                .in(MonthlyReportPO::getStatus, Arrays.asList(
                        MonthlyReportStatusEnum.REVIEW_PASSED_WAITING_FOR_SPLIT.code(),
                        MonthlyReportStatusEnum.SPLITTED_IN_PROGRESS.code()
                ))
        );

        log.info("[逾期计划]  已预期的计划：" + KPJsonUtil.toJsonString(monthlyReportPOList));

        List<MonthlyReportPO> updateList = new ArrayList<>();
        monthlyReportPOList.forEach(monthlyReportPO -> {
            updateList.add(new MonthlyReportPO()
                    .setMonthlyId(monthlyReportPO.getMonthlyId())
                    .setStatus(MonthlyReportStatusEnum.OVERDUE.code()));
        });
        monthlyReportService.updateBatchById(updateList);
        log.info("[逾期计划] 已处理的预期计划数 {}", updateList.size());
    }
}
