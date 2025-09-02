package com.kp.framework.modules.welcome.po.customer;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value="InterfaceCallStatisticsCustomerPO", description="InterfaceCallStatisticsCustomerPO")
public class InterfaceCallStatisticsCustomerPO {

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "时间")
    private List<String> callTime;

    @ApiModelProperty(value = "登录次数")
    private List<Integer> number;

    
}
