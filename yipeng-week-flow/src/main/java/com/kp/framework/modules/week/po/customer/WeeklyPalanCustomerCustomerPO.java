package com.kp.framework.modules.week.po.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 月计划拆分完成度统计。
 * @author lipeng
 * 2025/9/23
 */
@Data
@Accessors(chain = true)
@Schema(name = "WeeklyPalanCustomerCustomerPO", description = "WeeklyPalanCustomerCustomerPO")
public class WeeklyPalanCustomerCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "周数")
    private String taskWeek;

    @Schema(description = "总任务数")
    private Integer totalTaskCount;

    @Schema(description = "已完成任务数")
    private Integer completedTaskCount;
}

