package com.kp.framework.modules.monthly.po;

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
 * @Description 月度计划表
 * @Date 2025-09-15 14:03:44
**/
@Data
@Accessors(chain = true)
@TableName("week_monthly_report")
@ApiModel(value = "MonthlyReportPO对象", description = "月度计划表")
public class MonthlyReportPO extends ParentBO {

    @ApiModelProperty("月度计划Id")
    @TableId(value = "monthly_id", type = IdType.ASSIGN_UUID)
    private String monthlyId;

    @ApiModelProperty("计划时间")
    @TableField("plan_date")
    private LocalDate planDate;

    @ApiModelProperty("项目名称")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty("任务名称")
    @TableField("task_name")
    private String taskName;

    @ApiModelProperty("任务描述")
    @TableField("task_describe")
    private String taskDescribe;

    @ApiModelProperty("优先级 1紧急 2高 3中 4低 5规划调研")
    @TableField("priority")
    private Integer priority;

    @ApiModelProperty("来源")
    @TableField("source")
    private String source;

    @ApiModelProperty("岗位Id")
    @TableField("post_id")
    private String postId;

    @ApiModelProperty("部门Id")
    @TableField("dept_id")
    private String deptId;

    @ApiModelProperty("开始时间")
    @TableField("start_date")
    private LocalDate startDate;

    @ApiModelProperty("结束时间")
    @TableField("end_date")
    private LocalDate endDate;

    @ApiModelProperty("状态 1 草稿 2 提交审核 3 审核成功-待拆分 4 审核驳回 5 已拆分-进行中 6 已完成 7 逾期")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("审核意见")
    @TableField("review_comments")
    private String reviewComments;

    @ApiModelProperty("完成进度 0-100")
    @TableField("progress")
    private Integer progress;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
