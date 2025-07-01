package com.kunpeng.framework.modules.dept.po.customer;

import com.kunpeng.framework.modules.dept.po.DeptPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@Accessors(chain = true)
@ApiModel(value="DeptCustomerPO", description="DeptCustomerPO")
public class DeptCustomerPO extends DeptPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "子集")
    private List<DeptCustomerPO> children;

}
