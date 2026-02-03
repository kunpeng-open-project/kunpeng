package com.kp.framework.modules.week.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 周计划表。
 * @author lipeng
 * 2025-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("week_weekly_palan")
@Schema(name = "WeeklyPalanPO对象", description = "周计划表")
public class WeeklyPalanPO extends ParentBO<WeeklyPalanPO> {

    @Schema(description = "周计划id")
    @TableId(value = "weekly_id", type = IdType.ASSIGN_UUID)
    private String weeklyId;

    @Schema(description = "月度计划Id")
    @TableField("monthly_id")
    private String monthlyId;

    @Schema(description = "月计划时间")
    @TableField("monthly_plan_date")
    private LocalDate monthlyPlanDate;

    @Schema(description = "标题名称")
    @TableField("task_title")
    private String taskTitle;

    @Schema(description = "描述")
    @TableField("task_describe")
    private String taskDescribe;

    @Schema(description = "负责人id")
    @TableField("task_user_id")
    private String taskUserId;

    @Schema(description = "周数")
    @TableField("task_week")
    private String taskWeek;

    @Schema(description = "周开始日期")
    @TableField("task_start_date")
    private LocalDate taskStartDate;

    @Schema(description = "周结束日期")
    @TableField("task_end_date")
    private LocalDate taskEndDate;

    @Schema(description = "优先级 1紧急 2高 3中 4低")
    @TableField("task_priority")
    private Integer taskPriority;

    @Schema(description = "状态：1未开始 2进行中 3已完成 4废弃")
    @TableField("task_status")
    private Integer taskStatus;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
