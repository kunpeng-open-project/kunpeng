package com.kp.framework.modules.monthly.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author lipeng
 * @Description 月度计划列表查询入参
 * @Date 2025-07-25 15:09:06
**/
@Data
@Slf4j
@Accessors(chain = true)
@ApiModel(value = "MonthlyReportListParamPO对象", description = "月度计划列表查询入参")
public class MonthlyReportListParamPO extends PageBO {

//    @ApiModelProperty("年")
//    @TableField("year")
//    private Integer year;
//
//    @ApiModelProperty("月")
//    @TableField("month")
//    private Integer month;

    @ApiModelProperty(value = "计划时间")
    private List<String> planTimes;

    @ApiModelProperty("项目名称")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty("任务名称")
    @TableField("task_name")
    private String taskName;

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

    @ApiModelProperty("状态 1 草稿 2 提交审核 3 审核成功-待拆分 4 审核驳回 5 已拆分-进行中 6 已完成 7 逾期")
    @TableField("status")
    private Integer status;


}
