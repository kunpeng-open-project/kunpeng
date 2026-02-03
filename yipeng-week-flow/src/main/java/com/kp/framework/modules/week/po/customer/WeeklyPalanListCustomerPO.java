package com.kp.framework.modules.week.po.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.modules.week.po.WeeklyPalanPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "WeeklyPalanListCustomerPO", description = "WeeklyPalanListCustomerPO")
public class WeeklyPalanListCustomerPO extends WeeklyPalanPO {

    @Schema(description = "项目名称")
    @TableField("project_name")
    private String projectName;

    @Schema(description = "任务名称")
    @TableField("task_name")
    private String taskName;

    @Schema(description = "负责人真实姓名")
    private String realName;

    @Schema(description = "负责人头像")
    private String avatar;
}
