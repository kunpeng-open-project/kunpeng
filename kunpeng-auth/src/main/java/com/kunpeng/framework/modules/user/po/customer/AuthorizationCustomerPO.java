
package com.kunpeng.framework.modules.user.po.customer;

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
@ApiModel(value="AuthorizationCustomerPO", description="AuthorizationCustomerPO")
public class AuthorizationCustomerPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "token")
    private String accessToken;

    @ApiModelProperty(value = "过期时间， 单位 秒")
    private Integer invalidTime;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "项目编号")
    private String projectCode;

    @ApiModelProperty(value = "剩余授权次数")
    private Integer remainingNum;

    @ApiModelProperty(value = "权限")
    private List<String> permissions;

}
