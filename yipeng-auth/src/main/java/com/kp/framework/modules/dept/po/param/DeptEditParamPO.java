package com.kp.framework.modules.dept.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPLength;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 部门信息编辑入参。
 * @author lipeng
 * 2025-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DeptEditParamPO对象", description = "部门信息编辑入参")
public class DeptEditParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "部门Id", example = "部门Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "dept_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入部门Id")
    @KPMaxLength(max = 36, errMeg = "部门Id不能超过36个字符")
    private String deptId;

    @Schema(description = "父部门id", example = "父部门id")
    @TableField("parent_id")
    @KPMaxLength(max = 36, errMeg = "父部门id不能超过36个字符")
    private String parentId;

    @Schema(description = "部门名称", example = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("dept_name")
    @KPNotNull(errMeg = "请输入部门名称")
    @KPLength(min = 2, max = 68, errMeg = "部门名称须2~68个字符")
    private String deptName;

    @Schema(description = "部门状态 0停用 1正常", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("status")
    @KPNotNull(errMeg = "请选择部门状态")
    private Integer status;

    @Schema(description = "三方系统主键", example = "三方系统主键")
    @TableField("trilateral_id")
    @KPMaxLength(max = 128, errMeg = "三方系统主键不能超过128个字符")
    private String trilateralId;

    @Schema(description = "数据来源", example = "数据来源", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("source")
    @KPNotNull(errMeg = "请输入数据来源")
    @KPMaxLength(max = 64, errMeg = "数据来源不能超过64个字符")
    private String source;

    @Schema(description = "备注", example = "备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
