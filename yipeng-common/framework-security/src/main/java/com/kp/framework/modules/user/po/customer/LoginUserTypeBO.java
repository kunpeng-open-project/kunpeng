package com.kp.framework.modules.user.po.customer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

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
public class LoginUserTypeBO  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "唯一标识， 1 普通登录标识用户名 2 授权登录标识 appid")
    private String identification;

    @ApiModelProperty(value = "校验方式 1 密码 2 appSecret")
    private String check;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty("项目编号")
    private String projectCode;

    @ApiModelProperty(value = "登录类型 1 普通账号登录 2 授权登录 3免密登录 4单点登录")
    private Integer loginType;

}
