package com.kp.framework.modules.welcome.po.customer;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "InterfaceCallStatisticsCustomerPO", description = "InterfaceCallStatisticsCustomerPO")
public class InterfaceCallStatisticsCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "时间")
    private List<String> callTime;

    @Schema(description = "登录次数")
    private List<Integer> number;


}
