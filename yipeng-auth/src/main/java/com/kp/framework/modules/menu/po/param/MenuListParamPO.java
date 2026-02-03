package com.kp.framework.modules.menu.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单信息列表查询入参。
 * @author lipeng
 * 2025-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "MenuListParamPO对象", description = "菜单信息列表查询入参")
public class MenuListParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目Id")
    @TableField("project_id")
    @KPNotNull(errMeg = "请选择要查询的项目")
    private String projectId;

    @Schema(description = "菜单名称")
    @TableField("menu_name")
    private String menuName;

    @Schema(description = "菜单类型 M目录 C菜单 B按钮 I接口")
    @TableField("menu_type")
    private String menuType;

    @Schema(description = "权限标识")
    @TableField("perms")
    private String perms;

    @Schema(description = "是否显示 0否 1是")
    @TableField("visible")
    private Integer visible;

    @Schema(description = "是否启用 0否 1是")
    @TableField("is_enable")
    private Integer isEnable;

    @Schema(description = "是否树形结构 1 树形 2 列表", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer isTree = 2;

    @Schema(description = "排序规则 如 id desc, name asc")
    @TableField(exist = false)
    private String orderBy;
}
