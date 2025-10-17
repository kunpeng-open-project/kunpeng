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

/**
 * @Author lipeng
 * @Description 月度计划责任人信息编辑入参
 * @Date 2025-07-25
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "MonthlyReportUserEditParamPO对象", description = "月度计划责任人信息编辑入参")
public class MonthlyReportUserEditParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "月度计划责任人Id", example = "月度计划责任人Id", required = true)
    @TableId(value = "mru_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入月度计划责任人Id")
    @KPMaxLength(max = 36, errMeg = "月度计划责任人Id不能超过36个字符")
    private String mruId;

    @ApiModelProperty(value = "用户id", example = "用户id", required = true)
    @TableField("user_id")
    @KPNotNull(errMeg = "请输入用户id")
    @KPMaxLength(max = 32, errMeg = "用户id不能超过32个字符")
    private String userId;

    @ApiModelProperty(value = "用户姓名", example = "用户姓名", required = true)
    @TableField("user_name")
    @KPNotNull(errMeg = "请输入用户姓名")
    @KPMaxLength(max = 64, errMeg = "用户姓名不能超过64个字符")
    private String userName;

    @ApiModelProperty(value = "备注", example = "备注", required = true)
    @TableField("remark")
    @KPNotNull(errMeg = "请输入备注")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
