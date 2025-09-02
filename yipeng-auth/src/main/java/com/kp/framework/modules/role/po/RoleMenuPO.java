package com.kp.framework.modules.role.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 角色菜单关联表
 * @Date 2025-04-20
**/
@Data
@Accessors(chain = true)
@TableName("auth_role_menu")
@ApiModel(value = "RoleMenuPO对象", description = "角色菜单关联表")
public class RoleMenuPO extends ParentBO {

    @ApiModelProperty("角色菜单Id")
    @TableId(value = "arm_id", type = IdType.ASSIGN_UUID)
    private String armId;

    @ApiModelProperty("菜单Id")
    @TableField("menu_id")
    private String menuId;

    @ApiModelProperty("角色Id")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty("项目Id")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
