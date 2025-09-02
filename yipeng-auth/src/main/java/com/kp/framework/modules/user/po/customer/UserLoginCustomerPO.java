package com.kp.framework.modules.user.po.customer;

import com.kp.framework.modules.user.po.UserPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author lipeng
 * @since 2024-04-18
 */
@Data
@Accessors(chain = true)
@ApiModel(value="UserLoginCustomerPO", description="UserLoginCustomerPO")
public class UserLoginCustomerPO extends UserPO {

    public UserLoginCustomerPO() {}
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "token")
    private String accessToken;

    @ApiModelProperty(value = "roles")
    private List<String> roles;

    @ApiModelProperty(value = "permissions")
    private List<String> permissions;

//    @ApiModelProperty(value = "用于调用刷新accessToken的接口时所需的token")
//    private String refreshToken;


}
