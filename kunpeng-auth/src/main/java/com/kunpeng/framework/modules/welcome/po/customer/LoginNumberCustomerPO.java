package com.kunpeng.framework.modules.welcome.po.customer;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="LoginNumberCustomerPO", description="LoginNumberCustomerPO")
public class LoginNumberCustomerPO {

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "当日登录用户数")
    private Integer todayLoginNumber;

    @ApiModelProperty(value = "当日活跃用户数")
    private Integer activeLoginNumber;
}
