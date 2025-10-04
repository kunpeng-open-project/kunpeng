package com.kp.framework.modules.week.po.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @Author lipeng
 * @Description 月计划拆分完成度统计
 * @Date 2025/9/23 14:54
 * @return
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "WeeklyPalanCustomerCustomerPO", description = "WeeklyPalanCustomerCustomerPO")
public class WeeklyPalanCustomerCustomerPO {

    @ApiModelProperty("周数")
    private String taskWeek;

    @ApiModelProperty("总任务数")
    private Integer totalTaskCount;

    @ApiModelProperty("已完成任务数")
    private Integer completedTaskCount;
}

