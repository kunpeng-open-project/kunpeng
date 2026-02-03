package com.kp.framework.modules.user.po.customer;

import com.kp.framework.modules.user.po.UserPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户信息表。
 * @author lipeng
 * 2024-04-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "QueryUserCustomerPO", description = "QueryUserCustomerPO")
public class QueryUserCustomerPO extends UserPO {

    @Schema(description = "部门名称")
    private String deptName;
}
