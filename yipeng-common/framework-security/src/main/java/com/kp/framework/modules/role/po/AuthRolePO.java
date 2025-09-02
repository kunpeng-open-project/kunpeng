package com.kp.framework.modules.role.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.common.parent.ParentSecurityBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Data
@TableName("auth_role")
public class AuthRolePO extends ParentSecurityBO<AuthRolePO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色Id")
    @TableId(value = "role_id", type = IdType.ASSIGN_UUID)
    private String roleId;

    @ApiModelProperty("角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty("角色编号")
    @TableField("role_code")
    private String roleCode;

    @ApiModelProperty("角色状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

}
