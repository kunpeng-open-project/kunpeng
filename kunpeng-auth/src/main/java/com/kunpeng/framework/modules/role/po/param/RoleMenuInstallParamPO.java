package com.kunpeng.framework.modules.role.po.param;

import com.kunpeng.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value="RoleMenuInstallParamPO", description="RoleMenuInstallParamPO")
public class RoleMenuInstallParamPO {

    @ApiModelProperty(value = "角色Id", required = true, example = "e3ae1261c42dcb0e195fb9b9d9298bfe")
    @KPNotNull(errMeg = "请选择角色Id")
    private String roleId;

    @ApiModelProperty(value = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407")
    @KPNotNull(errMeg = "请选择项目Id")
    private String projectId;

    @ApiModelProperty(value = "选中的菜单集合", required = true)
    private List<String> menuIds;
}
