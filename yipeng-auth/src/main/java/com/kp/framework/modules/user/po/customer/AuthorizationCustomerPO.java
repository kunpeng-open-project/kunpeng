
package com.kp.framework.modules.user.po.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 用户信息表。
 * @author lipeng
 * 2024-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "AuthorizationCustomerPO", description = "AuthorizationCustomerPO")
public class AuthorizationCustomerPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "token")
    private String accessToken;

    @Schema(description = "过期时间， 单位 秒")
    private Integer invalidTime;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "项目编号")
    private String projectCode;

    @Schema(description = "剩余授权次数")
    private Integer remainingNum;

    @Schema(description = "权限")
    private List<String> permissions;

}
