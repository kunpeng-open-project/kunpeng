package com.kp.framework.microservices.auth.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门信息表。
 * @author lipeng
 * 2025-04-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DeptPO对象", description = "部门信息表")
public class DeptFeignPO extends ParentBO<DeptFeignPO> {

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
