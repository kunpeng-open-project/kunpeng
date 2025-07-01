package com.kunpeng.framework.modules.user.po;

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
 * @Description 用户角色关联表
 * @Date 2025-04-21
**/
@Data
@Accessors(chain = true)
@TableName("auth_user_role")
@ApiModel(value = "UserRolePO对象", description = "用户角色关联表")
public class UserRolePO extends ParentBO {

    @ApiModelProperty("用户角色Id")
    @TableId(value = "aur_id", type = IdType.ASSIGN_UUID)
    private String aurId;

    @ApiModelProperty("用户Id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("角色Id")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
