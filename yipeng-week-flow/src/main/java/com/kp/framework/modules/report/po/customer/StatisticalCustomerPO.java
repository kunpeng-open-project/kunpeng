package com.kp.framework.modules.report.po.customer;

import com.kp.framework.modules.monthly.po.customer.MonthlyReportStatisticsCustomerPO;
import com.kp.framework.modules.week.po.customer.WeellyTaskSummaryCustomerPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;


@Data
@Accessors(chain = true)
@Schema(name = "StatisticalCustomerPO")
public class StatisticalCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "月计划统计")
    private MonthlyReportStatisticsCustomerPO monthly;

    @Schema(description = "周任务统计")
    private WeellyTaskSummaryCustomerPO week;
}
