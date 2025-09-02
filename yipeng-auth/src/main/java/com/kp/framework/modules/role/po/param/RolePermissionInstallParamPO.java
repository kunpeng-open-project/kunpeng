package com.kp.framework.modules.role.po.param;

import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
@ApiModel(value="RolePermissionInstallParamPO", description="RolePermissionInstallParamPO")
public class RolePermissionInstallParamPO {

    @ApiModelProperty(value = "角色Id", required = true, example = "4c2943e45aa513c079045020b0d1bd8e")
    @KPNotNull(errMeg = "请选择角色Id")
    private String roleId;

    @ApiModelProperty(value = "项目Id", required = true, example = "bfff793893f9b3f08d736389529a1115")
    @KPNotNull(errMeg = "请选择项目Id")
    private String projectId;

    @ApiModelProperty(value = "权限范围 1 仅本人数据权限 2 自定义用户数据 3 本部门权限 4 本部门及以下权限 5自定义数据权限 6 全部数据权限")
    @KPNotNull(errMeg = "请选择权限范围")
    private Integer permissionType;

    @ApiModelProperty(value = "选择的内容")
    private List<String> choiceValue;
}
