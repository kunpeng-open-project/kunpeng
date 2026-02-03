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
 * 角色信息表。
 * @author lipeng
 * 2025-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_role")
@Schema(name = "RolePO对象", description = "角色信息表")
public class RolePO extends ParentBO<RolePO> {

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
