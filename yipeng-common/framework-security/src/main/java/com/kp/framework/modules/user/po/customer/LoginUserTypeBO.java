package com.kp.framework.modules.user.po.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

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
public class LoginUserTypeBO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "唯一标识， 1 普通登录标识用户名 2 授权登录标识 appid")
    private String identification;

    @Schema(description = "校验方式 1 密码 2 appSecret")
    private String check;

    @Schema(description = "项目id")
    private String projectId;

    @Schema(description = "项目编号")
    private String projectCode;

    @Schema(description = "登录类型 1 普通账号登录 2 授权登录 3免密登录 4单点登录")
    private Integer loginType;

}
