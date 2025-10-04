package com.kp.framework.modules.report.po.customer;

import com.kp.framework.modules.monthly.po.customer.MonthlyReportStatisticsCustomerPO;
import com.kp.framework.modules.week.po.customer.WeellyTaskSummaryCustomerPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
@ApiModel(value = "StatisticalCustomerPO", description = "StatisticalCustomerPO")
public class StatisticalCustomerPO {

    @ApiModelProperty("月计划统计")
    private MonthlyReportStatisticsCustomerPO monthly;

    @ApiModelProperty("周任务统计")
    private WeellyTaskSummaryCustomerPO week;
}
