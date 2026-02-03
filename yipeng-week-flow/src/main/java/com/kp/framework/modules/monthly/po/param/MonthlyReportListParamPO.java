package com.kp.framework.modules.monthly.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * 月度计划列表查询入参。
 * @author lipeng
 * 2025-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
@Accessors(chain = true)
@Schema(name = "MonthlyReportListParamPO对象", description = "月度计划列表查询入参")
public class MonthlyReportListParamPO extends PageBO {

    @Schema(description = "计划时间")
    private List<String> planTimes;

    @Schema(description = "项目名称")
    @TableField("project_name")
    private String projectName;

    @Schema(description = "任务名称")
    @TableField("task_name")
    private String taskName;

    @Schema(description = "优先级 1紧急 2高 3中 4低 5规划调研")
    @TableField("priority")
    private Integer priority;

    @Schema(description = "来源")
    @TableField("source")
    private String source;

    @Schema(description = "岗位Id")
    @TableField("post_id")
    private String postId;

    @Schema(description = "部门Id")
    @TableField("dept_id")
    private String deptId;

    @Schema(description = "状态 1 草稿 2 提交审核 3 审核成功-待拆分 4 审核驳回 5 已拆分-进行中 6 已完成 7 逾期")
    @TableField("status")
    private Integer status;
}
