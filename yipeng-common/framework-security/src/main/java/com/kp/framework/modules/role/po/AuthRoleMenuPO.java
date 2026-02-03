package com.kp.framework.modules.role.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.common.parent.ParentSecurityBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色菜单关联表
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_role_menu")
public class AuthRoleMenuPO extends ParentSecurityBO<AuthRoleMenuPO> {

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
