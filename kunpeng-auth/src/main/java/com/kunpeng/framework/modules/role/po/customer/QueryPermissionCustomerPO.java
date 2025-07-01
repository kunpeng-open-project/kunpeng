package com.kunpeng.framework.modules.role.po.customer;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value="QueryPermissionCustomerPO", description="QueryPermissionCustomerPO")
public class QueryPermissionCustomerPO {

    @ApiModelProperty(value = "权限范围 1 仅本人数据权限 2 自定义用户数据 3 本部门权限 4 本部门及以下权限 5自定义数据权限 6 全部数据权限")
    private Integer permissionType;

    @ApiModelProperty(value = "选择的内容")
    private List<String> choiceValue;
}
