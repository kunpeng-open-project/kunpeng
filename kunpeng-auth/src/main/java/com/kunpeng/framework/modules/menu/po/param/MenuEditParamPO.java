package com.kunpeng.framework.modules.menu.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kunpeng.framework.annotation.verify.KPMaxLength;
import com.kunpeng.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 菜单信息编辑入参
 * @Date 2025-04-11
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "MenuEditParamPO对象", description = "菜单信息编辑入参")
public class MenuEditParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单Id", example = "菜单Id", required = true)
    @TableId(value = "menu_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入菜单Id")
    @KPMaxLength(max = 36, errMeg = "菜单Id不能超过36个字符")
    private String menuId;

    @ApiModelProperty(value = "父菜单ID", example = "父菜单ID")
    @TableField("parent_id")
    @KPMaxLength(max = 36, errMeg = "父菜单ID不能超过36个字符")
    private String parentId;

    @ApiModelProperty(value = "项目Id", example = "项目Id", required = true)
    @TableField("project_id")
    @KPNotNull(errMeg = "请选择项目Id")
    @KPMaxLength(max = 36, errMeg = "项目Id不能超过36个字符")
    private String projectId;

    @ApiModelProperty(value = "菜单名称", example = "菜单名称", required = true)
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty(value = "路由名称", example = "路由名称")
    @TableField("route_name")
    @KPMaxLength(max = 36, errMeg = "路由名称不能超过36个字符")
    private String routeName;

    @ApiModelProperty(value = "路由地址", example = "路由地址")
    @TableField("route_path")
    @KPMaxLength(max = 68, errMeg = "路由地址不能超过68个字符")
    private String routePath;

    @ApiModelProperty(value = "路由组件路径", example = "路由组件路径")
    @TableField("route_component")
    @KPMaxLength(max = 256, errMeg = "路由组件路径不能超过256个字符")
    private String routeComponent;

    @ApiModelProperty(value = "链接类型 1 内部 2 外链内嵌 3 外链", example = "0")
    @TableField("frame_status")
    private Integer frameStatus;

    @ApiModelProperty(value = "菜单类型 M目录 C菜单 B按钮 I接口", example = "菜单类型 M目录 C菜单 B按钮 I接口", required = true)
    @TableField("menu_type")
    @KPNotNull(errMeg = "请选择菜单类型 M目录 C菜单 B按钮 I接口")
    @KPMaxLength(max = 1, errMeg = "菜单类型 M目录 C菜单 B按钮 I接口不能超过1个字符")
    private String menuType;

    @ApiModelProperty(value = "权限标识", example = "权限标识")
    @TableField("perms")
    @KPMaxLength(max = 100, errMeg = "权限标识不能超过100个字符")
    private String perms;

    @ApiModelProperty(value = "菜单图标", example = "菜单图标")
    @TableField("icon")
    @KPMaxLength(max = 100, errMeg = "菜单图标不能超过100个字符")
    private String icon;

    @ApiModelProperty(value = "是否显示 0否 1是", example = "0")
    @TableField("visible")
    private Integer visible;

    @ApiModelProperty(value = "是否启用 0否 1是", example = "0")
    @TableField("is_enable")
    private Integer isEnable;

    @ApiModelProperty(value = "是否缓存 0否 1是", example = "0")
    @TableField("is_cache")
    private Integer isCache;

    @ApiModelProperty(value = "备注", example = "备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
