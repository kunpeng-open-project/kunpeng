package com.kp.framework.modules.monthly.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author lipeng
 * @Description 月度计划编辑入参
 * @Date 2025-07-25
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "MonthlyReportEditParamPO对象", description = "月度计划编辑入参")
public class MonthlyReportEditParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "月度计划Id", required = true)
    @KPNotNull(errMeg = "请输入月度计划Id")
    @TableId(value = "monthly_id", type = IdType.ASSIGN_UUID)
    private String monthlyId;

    @ApiModelProperty(value = "计划时间", example = "2025-07", required = true)
    @KPNotNull(errMeg = "请输入计划时间")
    private String planTime;

    @ApiModelProperty(value = "项目名称", example = "项目名称")
    @TableField("project_name")
    @KPMaxLength(max = 32, errMeg = "项目名称不能超过32个字符")
    private String projectName;

    @ApiModelProperty(value = "任务名称", example = "任务名称", required = true)
    @TableField("task_name")
    @KPNotNull(errMeg = "请输入任务名称")
    @KPMaxLength(max = 32, errMeg = "任务名称不能超过32个字符")
    private String taskName;

    @ApiModelProperty(value = "任务描述", example = "任务描述", required = true)
    @TableField("task_describe")
    @KPNotNull(errMeg = "请输入任务描述")
    @KPMaxLength(max = 255, errMeg = "任务描述不能超过255个字符")
    private String taskDescribe;

    @ApiModelProperty(value = "优先级 1紧急 2高 3中 4低 5规划调研", example = "0", required = true)
    @TableField("priority")
    @KPNotNull(errMeg = "请选择优先级 1紧急 2高 3中 4低 5规划调研")
    private Integer priority;

    @ApiModelProperty(value = "来源", example = "来源", required = true)
    @TableField("source")
    @KPNotNull(errMeg = "请选择来源")
    @KPMaxLength(max = 32, errMeg = "来源不能超过32个字符")
    private String source;

    @ApiModelProperty(value = "岗位Id", example = "岗位Id", required = true)
    @TableField("post_id")
    @KPNotNull(errMeg = "请输入岗位Id")
    @KPMaxLength(max = 36, errMeg = "岗位Id不能超过36个字符")
    private String postId;

    @ApiModelProperty(value = "部门Id", example = "部门Id", required = true)
    @TableField("dept_id")
    @KPNotNull(errMeg = "请输入部门Id")
    @KPMaxLength(max = 36, errMeg = "部门Id不能超过36个字符")
    private String deptId;

    @ApiModelProperty(value = "完成时间", required = true)
    @KPNotNull(errMeg = "请输入完成时间")
    private List<LocalDate> completeDate;

    @ApiModelProperty(value = "备注", example = "备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;

    @ApiModelProperty(value = "负责人")
    @KPNotNull(errMeg = "请输入负责人")
    private List<String> userIds;
}
