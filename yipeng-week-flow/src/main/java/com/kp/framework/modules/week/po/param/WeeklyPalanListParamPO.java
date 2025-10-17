package com.kp.framework.modules.week.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 周计划列表查询入参
 * @Date 2025-09-20
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "WeeklyPalanListParamPO对象", description = "周计划列表查询入参")
public class WeeklyPalanListParamPO extends PageBO {


    @ApiModelProperty("月度计划Id")
    @TableField("monthly_id")
    private String monthlyId;

    @ApiModelProperty("月计划时间, 格式 YYYY-MM")
    private String planTime;

    @ApiModelProperty("标题名称")
    @TableField("task_title")
    private String taskTitle;

    @ApiModelProperty("负责人id")
    @TableField("task_user_id")
    private String taskUserId;

    @ApiModelProperty("周数")
    @TableField("task_week")
    private String taskWeek;

//    @ApiModelProperty("周开始日期")
//    @TableField("task_start_date")
//    private LocalDate taskStartDate;
//
//    @ApiModelProperty("周结束日期")
//    @TableField("task_end_date")
//    private LocalDate taskEndDate;

    @ApiModelProperty("优先级 1紧急 2高 3中 4低")
    @TableField("task_priority")
    private Integer taskPriority;

    @ApiModelProperty("状态：1未开始 2进行中 3已完成 4废弃")
    @TableField("task_status")
    private Integer taskStatus;
}
