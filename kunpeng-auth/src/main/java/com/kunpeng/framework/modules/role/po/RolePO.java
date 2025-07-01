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
 * @Description 角色信息表
 * @Date 2025-04-07
**/
@Data
@Accessors(chain = true)
@TableName("auth_role")
@ApiModel(value = "RolePO对象", description = "角色信息表")
public class RolePO extends ParentBO {

    @ApiModelProperty("角色Id")
    @TableId(value = "role_id", type = IdType.ASSIGN_UUID)
    private String roleId;

    @ApiModelProperty("角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty("角色编号")
    @TableField("role_code")
    private String roleCode;

    @ApiModelProperty("角色状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
