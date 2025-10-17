package com.kp.framework.modules.monthly.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.annotation.verify.KPLength;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author lipeng
 * @Description 月度计划审核入参
 * @Date 2025/9/5
 * @return
 **/
@Data
@Accessors(chain = true)
@ApiModel(value = "MonthlyReportReviewParamPO", description = "MonthlyReportReviewParamPO")
public class MonthlyReportReviewParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "请输入月度计划Id集合", required = true)
    @KPNotNull(errMeg = "请输入月度计划Id集合")
    private List<String> ids;

    @ApiModelProperty("审核意见")
    @TableField("review_comments")
    @KPMaxLength(max = 255, errMeg = "审核意见不能超过255个字符")
    private String reviewComments;

    @ApiModelProperty(value = "审核状态 1 同意 0 拒绝", example = "1")
    @KPNotNull(errMeg = "请输入审核结果")
    @KPLength(min = 0, max = 1, errMeg = "只能选择同意或者拒绝")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "优先级 1紧急 2高 3中 4低 5规划调研", example = "0", required = true)
    @TableField("priority")
    @KPNotNull(errMeg = "请选择优先级 1紧急 2高 3中 4低 5规划调研")
    private Integer priority;
}
