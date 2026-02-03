package com.kp.framework.modules.menu.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 菜单信息编辑入参。
 * @author lipeng
 * 2025-04-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "MenuEditParamPO对象", description = "菜单信息编辑入参")
public class MenuEditParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单Id", example = "菜单Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "menu_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入菜单Id")
    @KPMaxLength(max = 36, errMeg = "菜单Id不能超过36个字符")
    private String menuId;

    @Schema(description = "父菜单ID", example = "父菜单ID")
    @TableField("parent_id")
    @KPMaxLength(max = 36, errMeg = "父菜单ID不能超过36个字符")
    private String parentId;

    @Schema(description = "项目Id", example = "项目Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("project_id")
    @KPNotNull(errMeg = "请选择项目Id")
    @KPMaxLength(max = 36, errMeg = "项目Id不能超过36个字符")
    private String projectId;

    @Schema(description = "菜单名称", example = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("menu_name")
    private String menuName;

    @Schema(description = "路由名称", example = "路由名称")
    @TableField("route_name")
    @KPMaxLength(max = 36, errMeg = "路由名称不能超过36个字符")
    private String routeName;

    @Schema(description = "路由地址", example = "路由地址")
    @TableField("route_path")
    @KPMaxLength(max = 68, errMeg = "路由地址不能超过68个字符")
    private String routePath;

    @Schema(description = "路由组件路径", example = "路由组件路径")
    @TableField("route_component")
    @KPMaxLength(max = 256, errMeg = "路由组件路径不能超过256个字符")
    private String routeComponent;

    @Schema(description = "链接类型 1 内部 2 外链内嵌 3 外链", example = "0")
    @TableField("frame_status")
    private Integer frameStatus;

    @Schema(description = "菜单类型 M目录 C菜单 B按钮 I接口", example = "菜单类型 M目录 C菜单 B按钮 I接口", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("menu_type")
    @KPNotNull(errMeg = "请选择菜单类型 M目录 C菜单 B按钮 I接口")
    @KPMaxLength(max = 1, errMeg = "菜单类型 M目录 C菜单 B按钮 I接口不能超过1个字符")
    private String menuType;

    @Schema(description = "权限标识", example = "权限标识")
    @TableField("perms")
    @KPMaxLength(max = 100, errMeg = "权限标识不能超过100个字符")
    private String perms;

    @Schema(description = "菜单图标", example = "菜单图标")
    @TableField("icon")
    @KPMaxLength(max = 100, errMeg = "菜单图标不能超过100个字符")
    private String icon;

    @Schema(description = "是否显示 0否 1是", example = "0")
    @TableField("visible")
    private Integer visible;

    @Schema(description = "是否启用 0否 1是", example = "0")
    @TableField("is_enable")
    private Integer isEnable;

    @Schema(description = "是否缓存 0否 1是", example = "0")
    @TableField("is_cache")
    private Integer isCache;

    @Schema(description = "备注", example = "备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
