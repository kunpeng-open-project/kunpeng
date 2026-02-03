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
 * 角色权限关联表
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_role_permission")
public class AuthRolePermissionPO extends ParentSecurityBO<AuthRolePermissionPO> {

    @Schema(description = "角色权限Id")
    @TableId(value = "arp_id", type = IdType.ASSIGN_UUID)
    private String arpId;

    @Schema(description = "角色Id")
    @TableField("role_id")
    private String roleId;

    @Schema(description = "项目Id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "部门Id")
    @TableField("dept_id")
    private String deptId;

    @Schema(description = "用户Id")
    @TableField("user_id")
    private String userId;

    @Schema(description = "权限范围 1 仅本人数据权限 2 自定义用户数据 3 本部门权限 4 本部门及以下权限 5自定义数据权限 6 全部数据权限")
    @TableField("permission_type")
    private Integer permissionType;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
