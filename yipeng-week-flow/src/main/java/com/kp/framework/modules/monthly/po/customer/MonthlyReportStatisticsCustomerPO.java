package com.kp.framework.modules.monthly.po.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;


/**
 * 月度计划统计结果DTO。
 * @author lipeng
 * 2025/9/15
 */
@Data
@Accessors(chain = true)
@Schema(name = "MonthlyReportStatisticsCustomerPO", description = "MonthlyReportStatisticsCustomerPO")
public class MonthlyReportStatisticsCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "全部计划数量")
    private Integer totalCount;

    @Schema(description = "待审核计划数量")
    private Integer pendingReviewCount;

    @Schema(description = "已通过计划数量-待处理")
    private Integer notStartedCount;

    @Schema(description = "已驳回计划数量")
    private Integer rejectedCount;

    @Schema(description = "进行中计划数量")
    private Integer inProgressCount;

    @Schema(description = "已完成计划数量")
    private Integer completedCount;

    @Schema(description = "逾期计划数量")
    private Integer overdueCount;

    @Schema(description = "有效任务数")
    private Integer validTaskCount;
}
