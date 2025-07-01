package com.kunpeng.framework.modules.menu.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunpeng.framework.common.parent.ParentSecurityBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 菜单信息表
 * </p>
 *
 * @author lipeng
 * @since 2024-04-19
 */
@Data
@TableName("auth_menu")
public class AuthMenuPO extends ParentSecurityBO<AuthMenuPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("菜单Id")
    @TableId(value = "menu_id", type = IdType.ASSIGN_UUID)
    private String menuId;

    @ApiModelProperty("父菜单ID")
    @TableField("parent_id")
    private String parentId;

    @ApiModelProperty("项目Id")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("祖级列表")
    @TableField("ancestors")
    private String ancestors;

    @ApiModelProperty("菜单名称")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty("路由名称")
    @TableField("route_name")
    private String routeName;

    @ApiModelProperty("路由地址")
    @TableField("route_path")
    private String routePath;

    @ApiModelProperty("路由组件路径")
    @TableField("route_component")
    private String routeComponent;

    @ApiModelProperty("显示顺序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("链接类型 1 内部 2 外链内嵌 3 外链")
    @TableField("frame_status")
    private Integer frameStatus;

    @ApiModelProperty("菜单类型 M目录 C菜单 B按钮 I接口")
    @TableField("menu_type")
    private String menuType;

    @ApiModelProperty("权限标识")
    @TableField("perms")
    private String perms;

    @ApiModelProperty("菜单图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("是否显示 0否 1是")
    @TableField("visible")
    private Integer visible;

    @ApiModelProperty("是否启用 0否 1是")
    @TableField("is_enable")
    private Integer isEnable;

    @ApiModelProperty("是否缓存 0否 1是")
    @TableField("is_cache")
    private Integer isCache;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

}
