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
 * 角色项目关联表。
 * @author lipeng
 * 2025-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_role_project_relevance")
@Schema(name = "RoleProjectRelevancePO对象", description = "角色项目关联表")
public class RoleProjectRelevancePO extends ParentBO<RoleProjectRelevancePO> {

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
