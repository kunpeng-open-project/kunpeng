package com.kunpeng.framework.modules.role.po.customer;

import com.kunpeng.framework.modules.role.po.RolePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "RoleDetailsCustomerPO", description = "RoleDetailsCustomerPO")
public class RoleDetailsCustomerPO extends RolePO {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属项目Id集合")
    private List<String> projectIds;

    @ApiModelProperty(value = "项目名称集合")
    private List<String> projectNameList;
}
