package com.kp.framework.modules.role.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 角色信息列表查询入参
 * @Date 2025-03-31
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "RoleListParamPO对象", description = "角色信息列表查询入参")
public class RoleListParamPO extends PageBO {

    @ApiModelProperty(value = "项目Id")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("角色名称")
    @TableField("role_name")
    private String roleName;

    @ApiModelProperty("角色编号")
    @TableField("role_code")
    private String roleCode;

    @ApiModelProperty("角色状态 0停用 1正常")
    @TableField("status")
    private Integer status;
}
