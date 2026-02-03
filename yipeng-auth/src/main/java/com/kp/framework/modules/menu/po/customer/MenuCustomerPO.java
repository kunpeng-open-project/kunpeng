package com.kp.framework.modules.menu.po.customer;

import com.kp.framework.modules.menu.po.MenuPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "MenuCustomerPO", description = "MenuCustomerPO")
public class MenuCustomerPO extends MenuPO {

    @Schema(description = "子集")
    private List<MenuCustomerPO> children;
}
