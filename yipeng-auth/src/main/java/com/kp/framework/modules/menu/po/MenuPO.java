package com.kp.framework.modules.menu.po;

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
 * 菜单信息表。
 * @author lipeng
 * 2025-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_menu")
@Schema(name = "MenuPO对象", description = "菜单信息表")
public class MenuPO extends ParentBO<MenuPO> {

    @Schema(description = "菜单Id")
    @TableId(value = "menu_id", type = IdType.ASSIGN_UUID)
    private String menuId;

    @Schema(description = "父菜单ID")
    @TableField("parent_id")
    private String parentId;

    @Schema(description = "项目Id")
    @TableField("project_id")
    private String projectId;

    @Schema(description = "祖级列表")
    @TableField("ancestors")
    private String ancestors;

    @Schema(description = "菜单名称")
    @TableField("menu_name")
    private String menuName;

    @Schema(description = "路由名称")
    @TableField("route_name")
    private String routeName;

    @Schema(description = "路由地址")
    @TableField("route_path")
    private String routePath;

    @Schema(description = "路由组件路径")
    @TableField("route_component")
    private String routeComponent;

    @Schema(description = "显示顺序")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "链接类型 1 内部 2 外链内嵌 3 外链")
    @TableField("frame_status")
    private Integer frameStatus;

    @Schema(description = "菜单类型 M目录 C菜单 B按钮 I接口")
    @TableField("menu_type")
    private String menuType;

    @Schema(description = "权限标识")
    @TableField("perms")
    private String perms;

    @Schema(description = "菜单图标")
    @TableField("icon")
    private String icon;

    @Schema(description = "是否显示 0否 1是")
    @TableField("visible")
    private Integer visible;

    @Schema(description = "是否启用 0否 1是")
    @TableField("is_enable")
    private Integer isEnable;

    @Schema(description = "是否缓存 0否 1是")
    @TableField("is_cache")
    private Integer isCache;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
