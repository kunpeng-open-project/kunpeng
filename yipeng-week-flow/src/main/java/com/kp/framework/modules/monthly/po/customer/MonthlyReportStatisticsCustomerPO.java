package com.kp.framework.modules.monthly.po.customer;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author lipeng
 * @Description 月度计划统计结果DTO
 * @Date 2025/9/15
 * @return
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "MonthlyReportStatisticsCustomerPO", description = "MonthlyReportStatisticsCustomerPO")
public class MonthlyReportStatisticsCustomerPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("全部计划数量")
    private Integer totalCount;

    @ApiModelProperty("待审核计划数量")
    private Integer pendingReviewCount;

    @ApiModelProperty("已通过计划数量-待处理")
    private Integer notStartedCount;

    @ApiModelProperty("已驳回计划数量")
    private Integer rejectedCount;

    @ApiModelProperty("进行中计划数量")
    private Integer inProgressCount;

    @ApiModelProperty("已完成计划数量")
    private Integer completedCount;

    @ApiModelProperty("逾期计划数量")
    private Integer overdueCount;

    @ApiModelProperty("有效任务数")
    private Integer validTaskCount;
}
