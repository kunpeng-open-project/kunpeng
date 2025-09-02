package com.kp.framework.modules.user.po.customer;

import com.kp.framework.modules.user.po.UserPO;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value="QueryUserCustomerPO", description="QueryUserCustomerPO")
public class QueryUserCustomerPO extends UserPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门名称")
    private String deptName;


}
