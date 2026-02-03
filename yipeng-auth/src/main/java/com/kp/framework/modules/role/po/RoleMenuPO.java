package com.kp.framework.modules.role.po;

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
 * 角色菜单关联表。
 * @author lipeng
 * 2025-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_role_menu")
@Schema(name = "RoleMenuPO对象", description = "角色菜单关联表")
public class RoleMenuPO extends ParentBO<RoleMenuPO> {

    @Schema(description = "角色菜单Id")
    @TableId(value = "arm_id", type = IdType.ASSIGN_UUID)
    private String armId;

    @Schema(description = "菜单Id")
    @TableField("menu_id")
    private String menuId;

    @Schema(description = "角色Id")
    @TableField("role_id")
    private String roleId;

    @Schema(description = "项目Id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
