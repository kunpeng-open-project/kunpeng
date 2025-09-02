package com.kp.framework.modules.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.common.parent.ParentSecurityBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户角色关联表
 * </p>
 *
 * @author lipeng
 * @since 2024-04-18
 */
@Data
@TableName("auth_user_role")
public class AuthUserRolePO extends ParentSecurityBO<AuthUserRolePO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户角色Id")
    @TableId(value = "aur_id", type = IdType.ASSIGN_UUID)
    private String aurId;

    @ApiModelProperty("用户Id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("角色Id")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
