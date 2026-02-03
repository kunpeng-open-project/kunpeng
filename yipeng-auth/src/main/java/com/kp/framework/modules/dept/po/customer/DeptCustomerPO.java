package com.kp.framework.modules.dept.po.customer;

import com.kp.framework.modules.dept.po.DeptPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DeptCustomerPO", description = "DeptCustomerPO")
public class DeptCustomerPO extends DeptPO {

    @Schema(description = "子集")
    private List<DeptCustomerPO> children;
}
