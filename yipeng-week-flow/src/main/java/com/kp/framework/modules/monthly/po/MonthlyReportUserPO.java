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

/**
 * @Author lipeng
 * @Description 月度计划责任人信息表
 * @Date 2025-07-25 15:27:59
**/
@Data
@Accessors(chain = true)
@TableName("week_monthly_report_user")
@ApiModel(value = "MonthlyReportUserPO对象", description = "月度计划责任人信息表")
public class MonthlyReportUserPO extends ParentBO {

    @ApiModelProperty("月度计划责任人Id")
    @TableId(value = "mru_id", type = IdType.ASSIGN_UUID)
    private String mruId;

    @ApiModelProperty("月度计划Id")
    @TableField("monthly_id")
    private String monthlyId;

    @ApiModelProperty("用户id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("用户姓名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
