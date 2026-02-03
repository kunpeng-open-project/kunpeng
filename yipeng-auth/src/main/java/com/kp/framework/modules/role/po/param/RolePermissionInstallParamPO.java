package com.kp.framework.modules.role.po.param;

import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "RolePermissionInstallParamPO", description = "RolePermissionInstallParamPO")
public class RolePermissionInstallParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "4c2943e45aa513c079045020b0d1bd8e")
    @KPNotNull(errMeg = "请选择角色Id")
    private String roleId;

    @Schema(description = "项目Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "bfff793893f9b3f08d736389529a1115")
    @KPNotNull(errMeg = "请选择项目Id")
    private String projectId;

    @Schema(description = "权限范围 1 仅本人数据权限 2 自定义用户数据 3 本部门权限 4 本部门及以下权限 5自定义数据权限 6 全部数据权限")
    @KPNotNull(errMeg = "请选择权限范围")
    private Integer permissionType;

    @Schema(description = "选择的内容")
    private List<String> choiceValue;
}
