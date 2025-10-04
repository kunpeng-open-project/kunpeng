package com.kp.framework.modules.week.po.customer;


import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "WeeklyPalanListCustomerPO", description = "WeeklyPalanListCustomerPO")
public class WeeklyPalanListCustomerPO extends WeeklyPalanPO {

    @ApiModelProperty("项目名称")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty("任务名称")
    @TableField("task_name")
    private String taskName;

    @ApiModelProperty("负责人真实姓名")
    private String realName;

    @ApiModelProperty("负责人头像")
    private String avatar;
}
