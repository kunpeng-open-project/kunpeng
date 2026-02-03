package com.kp.framework.modules.week.po.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询周计划统计任务数。
 * @author lipeng
 * 2025/9/26
 */
@Data
@Accessors(chain = true)
@Schema(name = "WeellyTaskSummaryCustomerPO")
public class WeellyTaskSummaryCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "总任务数")
    private Integer totalTaskCount;

    @Schema(description = "未开始任务数")
    private Integer notStartedCount;

    @Schema(description = "进行中任务数")
    private Integer inProgressCount;

    @Schema(description = "已完成任务数")
    private Integer completedCount;

    @Schema(description = "废弃任务数")
    private Integer abandonedCount;
}
