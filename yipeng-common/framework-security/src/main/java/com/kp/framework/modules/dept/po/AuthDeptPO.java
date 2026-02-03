package com.kp.framework.modules.dept.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.common.parent.ParentSecurityBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门信息表。
 * @author lipeng
 * 2024-09-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_dept")
public class AuthDeptPO extends ParentSecurityBO<AuthDeptPO> {

    @Schema(description = "部门Id")
    @TableId(value = "dept_id", type = IdType.ASSIGN_UUID)
    private String deptId;

    @Schema(description = "父部门id")
    @TableField("parent_id")
    private String parentId;

    @Schema(description = "根部门id")
    @TableField("top_dept_id")
    private String topDeptId;

    @Schema(description = "部门名称")
    @TableField("dept_name")
    private String deptName;

    @Schema(description = "祖级列表")
    @TableField("ancestors")
    private String ancestors;

    @Schema(description = "部门层级")
    @TableField("hierarchy")
    private Integer hierarchy;

    @Schema(description = "部门状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @Schema(description = "显示顺序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "三方系统主键")
    @TableField("trilateral_id")
    private String trilateralId;

    @Schema(description = "数据来源")
    @TableField("source")
    private String source;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
