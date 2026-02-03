package com.kp.framework.modules.welcome.po.customer;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "LoginNumberCustomerPO", description = "LoginNumberCustomerPO")
public class LoginNumberCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "当日登录用户数")
    private Integer todayLoginNumber;

    @Schema(description = "当日活跃用户数")
    private Integer activeLoginNumber;
}
