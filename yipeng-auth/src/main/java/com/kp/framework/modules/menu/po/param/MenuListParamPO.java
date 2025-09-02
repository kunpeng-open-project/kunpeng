package com.kp.framework.modules.menu.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 菜单信息列表查询入参
 * @Date 2025-04-11
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "MenuListParamPO对象", description = "菜单信息列表查询入参")
public class MenuListParamPO {

    @ApiModelProperty("项目Id")
    @TableField("project_id")
    @KPNotNull(errMeg = "请选择要查询的项目")
    private String projectId;

    @ApiModelProperty("菜单名称")
    @TableField("menu_name")
    private String menuName;

    @ApiModelProperty("菜单类型 M目录 C菜单 B按钮 I接口")
    @TableField("menu_type")
    private String menuType;

    @ApiModelProperty("权限标识")
    @TableField("perms")
    private String perms;

    @ApiModelProperty("是否显示 0否 1是")
    @TableField("visible")
    private Integer visible;

    @ApiModelProperty("是否启用 0否 1是")
    @TableField("is_enable")
    private Integer isEnable;

    @ApiModelProperty(value = "是否树形结构 1 树形 2 列表", required = true, example = "1")
    private Integer isTree = 2;

    @ApiModelProperty(value = "排序规则 如 id desc, name asc", required = false, example = "")
    @TableField(exist = false)
    private String orderBy;
}
