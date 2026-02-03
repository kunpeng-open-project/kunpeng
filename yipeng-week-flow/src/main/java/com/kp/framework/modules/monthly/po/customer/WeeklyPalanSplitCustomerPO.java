package com.kp.framework.modules.monthly.po.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 月计划拆分数度统计。
 * @author lipeng
 * 2025/9/23
 */
@Data
@Accessors(chain = true)
@Schema(name = "WeeklyPalanSplitCustomerPO", description = "WeeklyPalanCustomerCustomerPO")
public class WeeklyPalanSplitCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "任务名称")
    private String taskName;

    @Schema(description = "拆分个数")
    private Integer taskCount;

}
