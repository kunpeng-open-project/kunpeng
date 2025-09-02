package com.kp.framework.modules.project.po.param;

import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value="ProjectMenuInstallParamPO", description="ProjectMenuInstallParamPO")
public class ProjectMenuInstallParamPO {

    @ApiModelProperty(value = "项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407")
    @KPNotNull(errMeg = "请选择项目Id")
    private String projectId;

    @ApiModelProperty(value = "权限项目Id", required = true, example = "af0ccec3d65f7571d75a0a4fdf597407")
    @KPNotNull(errMeg = "请选择权限项目Id")
    private String purviewProjectId;

    @ApiModelProperty(value = "选中的菜单集合", required = true)
    private List<String> menuIds;
}
