package com.kp.framework.modules.user.po.customer;

import com.kp.framework.modules.user.po.UserPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="UserListCustomerPO", description="UserListCustomerPO")
public class UserListCustomerPO extends UserPO {

    @ApiModelProperty(value = "部门名称")
    private String deptNames;

    @ApiModelProperty(value = "岗位名称")
    private String postNames;

    @ApiModelProperty(value = "角色名称")
    private String roleNames;
}
