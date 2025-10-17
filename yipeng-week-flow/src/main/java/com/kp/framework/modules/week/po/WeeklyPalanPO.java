package com.kp.framework.modules.week.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * @Author lipeng
 * @Description 周计划表
 * @Date 2025-09-21
**/
@Data
@Accessors(chain = true)
@TableName("week_weekly_palan")
@ApiModel(value = "WeeklyPalanPO对象", description = "周计划表")
public class WeeklyPalanPO extends ParentBO {

    @ApiModelProperty("周计划id")
    @TableId(value = "weekly_id", type = IdType.ASSIGN_UUID)
    private String weeklyId;

    @ApiModelProperty("月度计划Id")
    @TableField("monthly_id")
    private String monthlyId;

    @ApiModelProperty("月计划时间")
    @TableField("monthly_plan_date")
    private LocalDate monthlyPlanDate;

    @ApiModelProperty("标题名称")
    @TableField("task_title")
    private String taskTitle;

    @ApiModelProperty("描述")
    @TableField("task_describe")
    private String taskDescribe;

    @ApiModelProperty("负责人id")
    @TableField("task_user_id")
    private String taskUserId;

    @ApiModelProperty("周数")
    @TableField("task_week")
    private String taskWeek;

    @ApiModelProperty("周开始日期")
    @TableField("task_start_date")
    private LocalDate taskStartDate;

    @ApiModelProperty("周结束日期")
    @TableField("task_end_date")
    private LocalDate taskEndDate;

    @ApiModelProperty("优先级 1紧急 2高 3中 4低")
    @TableField("task_priority")
    private Integer taskPriority;

    @ApiModelProperty("状态：1未开始 2进行中 3已完成 4废弃")
    @TableField("task_status")
    private Integer taskStatus;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
