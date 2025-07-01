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
 * @Description 角色项目关联表
 * @Date 2025-04-07
**/
@Data
@Accessors(chain = true)
@TableName("auth_role_project_relevance")
@ApiModel(value = "RoleProjectRelevancePO对象", description = "角色项目关联表")
public class RoleProjectRelevancePO extends ParentBO {

    @ApiModelProperty("角色项目Id")
    @TableId(value = "arpr_id", type = IdType.ASSIGN_UUID)
    private String arprId;

    @ApiModelProperty("项目Id")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("角色Id")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
