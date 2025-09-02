package com.kp.framework.modules.dept.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.common.parent.ParentSecurityBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author lipeng
 * @Description 部门信息表
 * @Date 2024-09-11
 **/
@Data
@TableName("auth_dept")
public class AuthDeptPO extends ParentSecurityBO<AuthDeptPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("部门Id")
    @TableId(value = "dept_id", type = IdType.ASSIGN_UUID)
    private String deptId;

    @ApiModelProperty("父部门id")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty("根部门id")
    @TableField("top_dept_id")
    private String topDeptId;

    @ApiModelProperty("部门名称")
    @TableField("dept_name")
    private String deptName;

    @ApiModelProperty("祖级列表")
    @TableField("ancestors")
    private String ancestors;

    @ApiModelProperty("部门层级")
    @TableField("hierarchy")
    private Integer hierarchy;

    @ApiModelProperty("部门状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("显示顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("三方系统主键")
    @TableField("trilateral_id")
    private String trilateralId;

    @ApiModelProperty("数据来源")
    @TableField("source")
    private String source;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
