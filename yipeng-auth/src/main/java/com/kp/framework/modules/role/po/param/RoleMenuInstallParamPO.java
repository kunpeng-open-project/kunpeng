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
@Schema(name = "RoleMenuInstallParamPO", description = "RoleMenuInstallParamPO")
public class RoleMenuInstallParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "e3ae1261c42dcb0e195fb9b9d9298bfe")
    @KPNotNull(errMeg = "请选择角色Id")
    private String roleId;

    @Schema(description = "项目Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "af0ccec3d65f7571d75a0a4fdf597407")
    @KPNotNull(errMeg = "请选择项目Id")
    private String projectId;

    @Schema(description = "选中的菜单集合", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> menuIds;
}
