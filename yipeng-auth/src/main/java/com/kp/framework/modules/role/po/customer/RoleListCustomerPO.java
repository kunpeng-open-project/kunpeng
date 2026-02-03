package com.kp.framework.modules.role.po.customer;

import com.kp.framework.modules.role.po.RolePO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "RoleListCustomerPO", description = "RoleListCustomerPO")
public class RoleListCustomerPO extends RolePO {

    @Schema(description = "项目名称")
    private String projectName;
}
