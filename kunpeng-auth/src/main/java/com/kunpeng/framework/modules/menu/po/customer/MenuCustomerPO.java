package com.kunpeng.framework.modules.menu.po.customer;

import com.kunpeng.framework.modules.menu.po.MenuPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value="MenuCustomerPO", description="MenuCustomerPO")
public class MenuCustomerPO extends MenuPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "子集")
    private List<MenuCustomerPO> children;
}
