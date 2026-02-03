package com.kp.framework.modules.welcome.po.customer;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "LoginRecordCustomerPO", description = "LoginRecordCustomerPO")
public class LoginRecordCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "登录记录")
    private String body;

    @Schema(description = "登录时间")
    private LocalDateTime loginDate;
}
