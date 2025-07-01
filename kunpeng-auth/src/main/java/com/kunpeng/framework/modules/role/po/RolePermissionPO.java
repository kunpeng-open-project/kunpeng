package com.kunpeng.framework.modules.role.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunpeng.framework.entity.bo.ParentBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 角色权限关联表
 * @Date 2025-05-13
**/
@Data
@Accessors(chain = true)
@TableName("auth_role_permission")
@ApiModel(value = "RolePermissionPO对象", description = "角色权限关联表")
public class RolePermissionPO extends ParentBO {

    @ApiModelProperty("角色权限Id")
    @TableId(value = "arp_id", type = IdType.ASSIGN_UUID)
    private String arpId;

    @ApiModelProperty("角色Id")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty("项目Id")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("部门Id")
    @TableField("dept_id")
    private String deptId;

    @ApiModelProperty("用户Id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("权限范围 1 仅本人数据权限 2 自定义用户数据 3 本部门权限 4 本部门及以下权限 5自定义数据权限 6 全部数据权限")
    @TableField("permission_type")
    private Integer permissionType;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
