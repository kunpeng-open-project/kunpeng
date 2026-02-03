package com.kp.framework.modules.week.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 周计划编辑入参。
 * @author lipeng
 * 2025-09-20
 */
@Data
@Accessors(chain = true)
@Schema(name = "WeeklyPalanEditParamPO对象", description = "周计划编辑入参")
public class WeeklyPalanEditParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "周计划id", example = "周计划id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "weekly_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入周计划id")
    @KPMaxLength(max = 36, errMeg = "周计划id不能超过36个字符")
    private String weeklyId;

    @Schema(description = "月度计划Id", example = "月度计划Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("monthly_id")
    @KPNotNull(errMeg = "请输入月度计划Id")
    @KPMaxLength(max = 36, errMeg = "月度计划Id不能超过36个字符")
    private String monthlyId;

    @Schema(description = "标题名称", example = "标题名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("task_title")
    @KPNotNull(errMeg = "请输入标题名称")
    @KPMaxLength(max = 32, errMeg = "标题名称不能超过32个字符")
    private String taskTitle;

    @Schema(description = "描述", example = "描述", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("task_describe")
    @KPNotNull(errMeg = "请输入描述")
    @KPMaxLength(max = 255, errMeg = "描述不能超过255个字符")
    private String taskDescribe;

    @Schema(description = "周数）", example = "第一周", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("task_week")
    @KPNotNull(errMeg = "请选择周数")
    private String taskWeek;

    @Schema(description = "完成时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @KPNotNull(errMeg = "请输入完成时间")
    private List<LocalDate> completeDate;

//   @Schema(description = "周开始日期", example = "2025-09-20 22:11:23", requiredMode = Schema.RequiredMode.REQUIRED)
//    @TableField("task_start_date")
//    @KPNotNull(errMeg = "请输入周开始日期")
//    private LocalDate taskStartDate;
//
//   @Schema(description = "周结束日期", example = "2025-09-20 22:11:23", requiredMode = Schema.RequiredMode.REQUIRED)
//    @TableField("task_end_date")
//    @KPNotNull(errMeg = "请输入周结束日期")
//    private LocalDate taskEndDate;

    @Schema(description = "优先级 1紧急 2高 3中 4低", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("task_priority")
    @KPNotNull(errMeg = "请输入优先级 1紧急 2高 3中 4低")
    private Integer taskPriority;

    @Schema(description = "备注", example = "备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
