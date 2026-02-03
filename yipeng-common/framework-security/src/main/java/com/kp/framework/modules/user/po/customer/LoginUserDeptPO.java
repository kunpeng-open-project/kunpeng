package com.kp.framework.modules.user.po.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.modules.dept.po.AuthDeptPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author lipeng
 * @since 2024-05-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDeptPO extends AuthDeptPO {

    @Schema(description = "是否负责人 0否 1是", example = "0")
    @TableField("principal")
    private Integer principal;

}
