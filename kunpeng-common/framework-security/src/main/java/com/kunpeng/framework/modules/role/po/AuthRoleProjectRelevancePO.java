package com.kunpeng.framework.modules.role.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunpeng.framework.common.parent.ParentSecurityBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * <p>
 * 角色项目关联表
 * </p>
 *
 * @author lipeng
 * @since 2024-04-26
 */
@Data
@TableName("auth_role_project_relevance")
public class AuthRoleProjectRelevancePO extends ParentSecurityBO<AuthRoleProjectRelevancePO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("角色项目Id")
    @TableId(value = "arpr_id", type = IdType.ASSIGN_UUID)
    private String arprId;

    @ApiModelProperty("项目Id")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("角色Id")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
