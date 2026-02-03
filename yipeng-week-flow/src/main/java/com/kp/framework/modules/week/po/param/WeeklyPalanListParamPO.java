package com.kp.framework.modules.week.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 周计划列表查询入参。
 * @author lipeng
 * 2025-09-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "WeeklyPalanListParamPO对象", description = "周计划列表查询入参")
public class WeeklyPalanListParamPO extends PageBO {

    @Schema(description = "月度计划Id")
    @TableField("monthly_id")
    private String monthlyId;

    @Schema(description = "月计划时间, 格式 YYYY-MM")
    private String planTime;

    @Schema(description = "标题名称")
    @TableField("task_title")
    private String taskTitle;

    @Schema(description = "负责人id")
    @TableField("task_user_id")
    private String taskUserId;

    @Schema(description = "周数")
    @TableField("task_week")
    private String taskWeek;

//     @Schema(description ="周开始日期")
//    @TableField("task_start_date")
//    private LocalDate taskStartDate;
//
//     @Schema(description ="周结束日期")
//    @TableField("task_end_date")
//    private LocalDate taskEndDate;

    @Schema(description = "优先级 1紧急 2高 3中 4低")
    @TableField("task_priority")
    private Integer taskPriority;

    @Schema(description = "状态：1未开始 2进行中 3已完成 4废弃")
    @TableField("task_status")
    private Integer taskStatus;
}
