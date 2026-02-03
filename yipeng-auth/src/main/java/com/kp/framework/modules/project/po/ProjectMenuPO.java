package com.kp.framework.modules.project.po;

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
 * 项目菜单关联表。
 * @author lipeng
 * 2025-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_project_menu")
@Schema(name = "ProjectMenuPO", description = "项目菜单关联表")
public class ProjectMenuPO extends ParentBO<ProjectMenuPO> {

    @Schema(description = "项目菜单Id")
    @TableId(value = "apm_id", type = IdType.ASSIGN_UUID)
    private String apmId;

    @Schema(description = "菜单Id")
    @TableField("menu_id")
    private String menuId;

    @Schema(description = "项目Id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "权限项目Id")
    @TableField("purview_project_id")
    private String purviewProjectId;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
