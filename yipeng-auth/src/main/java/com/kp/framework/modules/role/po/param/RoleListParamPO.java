package com.kp.framework.modules.role.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色信息列表查询入参。
 * @author lipeng
 * 2025-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "RoleListParamPO对象", description = "角色信息列表查询入参")
public class RoleListParamPO extends PageBO {

    @Schema(description = "项目Id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "角色名称")
    @TableField("role_name")
    private String roleName;

    @Schema(description = "角色编号")
    @TableField("role_code")
    private String roleCode;

    @Schema(description = "角色状态 0停用 1正常")
    @TableField("status")
    private Integer status;
}
