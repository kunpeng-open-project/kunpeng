package com.kp.framework.modules.dept.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPLength;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 部门信息编辑入参
 * @Date 2025-04-08
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "DeptEditParamPO对象", description = "部门信息编辑入参")
public class DeptEditParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门Id", example = "部门Id", required = true)
    @TableId(value = "dept_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入部门Id")
    @KPMaxLength(max = 36, errMeg = "部门Id不能超过36个字符")
    private String deptId;

    @ApiModelProperty(value = "父部门id", example = "父部门id")
    @TableField("parent_id")
    @KPMaxLength(max = 36, errMeg = "父部门id不能超过36个字符")
    private String parentId;

    @ApiModelProperty(value = "部门名称", example = "部门名称", required = true)
    @TableField("dept_name")
    @KPNotNull(errMeg = "请输入部门名称")
    @KPLength(min = 2, max = 68, errMeg = "部门名称须2~68个字符")
    private String deptName;

    @ApiModelProperty(value = "部门状态 0停用 1正常", example = "0", required = true)
    @TableField("status")
    @KPNotNull(errMeg = "请选择部门状态")
    private Integer status;

    @ApiModelProperty(value = "三方系统主键", example = "三方系统主键")
    @TableField("trilateral_id")
    @KPMaxLength(max = 128, errMeg = "三方系统主键不能超过128个字符")
    private String trilateralId;

    @ApiModelProperty(value = "数据来源", example = "数据来源", required = true)
    @TableField("source")
    @KPNotNull(errMeg = "请输入数据来源")
    @KPMaxLength(max = 64, errMeg = "数据来源不能超过64个字符")
    private String source;

    @ApiModelProperty(value = "备注", example = "备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
