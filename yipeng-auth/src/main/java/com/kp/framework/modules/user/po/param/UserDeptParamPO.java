package com.kp.framework.modules.user.po.param;

import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户部门关联编辑入参。
 * @author lipeng
 * 2024-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserDeptParamPO", description = "用户部门关联表编辑入参")
public class UserDeptParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "部门Id", example = "01", requiredMode = Schema.RequiredMode.REQUIRED)
    @KPNotNull(errMeg = "部门Id不能为空")
    private String deptId;

    @Schema(description = "是否负责人 0否 1是", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @KPNotNull(errMeg = "请选择是否是负责人")
    private Integer principal;
}
