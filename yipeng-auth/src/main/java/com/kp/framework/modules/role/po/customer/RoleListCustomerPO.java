package com.kp.framework.modules.role.po.customer;

import com.kp.framework.modules.role.po.RolePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "RoleListCustomerPO", description = "RoleListCustomerPO")
public class RoleListCustomerPO extends RolePO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

//    @ApiModelProperty(value = "所属项目Id集合")
//    private List<String> projectIds;
}
