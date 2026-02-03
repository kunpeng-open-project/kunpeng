package com.kp.framework.modules.user.po.customer;

import com.kp.framework.modules.user.po.UserPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 用户信息表。
 * @author lipeng
 * 2024-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Accessors(chain = true)
@Schema(name = "UserLoginCustomerPO", description = "UserLoginCustomerPO")
public class UserLoginCustomerPO extends UserPO {

    @Schema(description = "token")
    private String accessToken;

    @Schema(description = "roles")
    private List<String> roles;

    @Schema(description = "permissions")
    private List<String> permissions;

//    @Schema(description  = "用于调用刷新accessToken的接口时所需的token")
//    private String refreshToken;
}
