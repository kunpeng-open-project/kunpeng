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
 * 角色项目关联表
 * </p>
 *
 * @author lipeng
 * @since 2024-04-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_role_project_relevance")
public class AuthRoleProjectRelevancePO extends ParentSecurityBO<AuthRoleProjectRelevancePO> {

    @Schema(description = "角色项目Id")
    @TableId(value = "arpr_id", type = IdType.ASSIGN_UUID)
    private String arprId;

    @Schema(description = "项目Id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "角色Id")
    @TableField("role_id")
    private String roleId;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
