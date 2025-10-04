package com.kp.framework.modules.week.po.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @Author lipeng
 * @Description 查询周计划统计任务数
 * @Date 2025/9/26 11:27
 * @return
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "WeellyTaskSummaryCustomerPO", description = "WeellyTaskSummaryCustomerPO")
public class WeellyTaskSummaryCustomerPO {


    @ApiModelProperty("总任务数")
    private Integer totalTaskCount;

    @ApiModelProperty("未开始任务数")
    private Integer notStartedCount;

    @ApiModelProperty("进行中任务数")
    private Integer inProgressCount;

    @ApiModelProperty("已完成任务数")
    private Integer completedCount;

    @ApiModelProperty("废弃任务数")
    private Integer abandonedCount;
}
