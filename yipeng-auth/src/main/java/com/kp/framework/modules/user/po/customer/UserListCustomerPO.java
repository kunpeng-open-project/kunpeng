package com.kp.framework.modules.user.po.customer;

import com.kp.framework.modules.user.po.UserPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "UserListCustomerPO", description = "UserListCustomerPO")
public class UserListCustomerPO extends UserPO {

    @Schema(description = "部门名称")
    private String deptNames;

    @Schema(description = "岗位名称")
    private String postNames;

    @Schema(description = "角色名称")
    private String roleNames;
}
