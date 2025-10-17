package com.kp.framework.modules.monthly.po.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @Author lipeng
 * @Description 月计划拆分数度统计
 * @Date 2025/9/23
 * @return
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "WeeklyPalanCustomerCustomerPO", description = "WeeklyPalanCustomerCustomerPO")
public class WeeklyPalanSplitCustomerPO {

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("拆分个数")
    private Integer taskCount;

}
