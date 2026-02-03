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
@Schema(name = "LoginRecordStatisticsCustomerPO", description = "LoginRecordStatisticsCustomerPO")
public class LoginRecordStatisticsCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "时间")
    private String createDate;

    @Schema(description = "登录次数")
    private Integer number;
}
