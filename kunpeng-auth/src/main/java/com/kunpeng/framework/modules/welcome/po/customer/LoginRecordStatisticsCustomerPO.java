package com.kunpeng.framework.modules.welcome.po.customer;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="LoginRecordStatisticsCustomerPO", description="LoginRecordStatisticsCustomerPO")
public class LoginRecordStatisticsCustomerPO {

    @ApiModelProperty(value = "时间")
    private String createDate;

    @ApiModelProperty(value = "登录次数")
    private Integer number;
}
