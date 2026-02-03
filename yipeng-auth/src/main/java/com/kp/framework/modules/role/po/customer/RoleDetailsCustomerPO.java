package com.kp.framework.modules.role.po.customer;

import com.kp.framework.modules.role.po.RolePO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "RoleDetailsCustomerPO", description = "RoleDetailsCustomerPO")
public class RoleDetailsCustomerPO extends RolePO {

    @Schema(description = "所属项目Id集合")
    private List<String> projectIds;

    @Schema(description = "项目名称集合")
    private List<String> projectNameList;
}
