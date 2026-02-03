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
 * 角色信息表
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_role")
public class AuthRolePO extends ParentSecurityBO<AuthRolePO> {

    @Schema(description = "角色Id")
    @TableId(value = "role_id", type = IdType.ASSIGN_UUID)
    private String roleId;

    @Schema(description = "角色名称")
    @TableField("role_name")
    private String roleName;

    @Schema(description = "角色编号")
    @TableField("role_code")
    private String roleCode;

    @Schema(description = "角色状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

}
