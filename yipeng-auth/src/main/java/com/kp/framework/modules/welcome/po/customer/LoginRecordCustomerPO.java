package com.kp.framework.modules.welcome.po.customer;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@ApiModel(value="LoginRecordCustomerPO", description="LoginRecordCustomerPO")
public class LoginRecordCustomerPO {

    @ApiModelProperty(value = "登录记录")
    private String body;

    @ApiModelProperty(value = "登录时间")
    private LocalDateTime loginDate;
}
