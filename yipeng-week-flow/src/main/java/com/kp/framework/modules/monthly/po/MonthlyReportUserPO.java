package com.kp.framework.modules.monthly.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 月度计划责任人信息表。
 * @author lipeng
 * 2025-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("week_monthly_report_user")
@Schema(name = "MonthlyReportUserPO对象", description = "月度计划责任人信息表")
public class MonthlyReportUserPO extends ParentBO<MonthlyReportUserPO> {

    @Schema(description = "月度计划责任人Id")
    @TableId(value = "mru_id", type = IdType.ASSIGN_UUID)
    private String mruId;

    @Schema(description = "月度计划Id")
    @TableField("monthly_id")
    private String monthlyId;

    @Schema(description = "用户id")
    @TableField("user_id")
    private String userId;

    @Schema(description = "用户姓名")
    @TableField("user_name")
    private String userName;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
