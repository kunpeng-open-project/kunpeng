package com.kunpeng.framework.modules.user.po.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kunpeng.framework.modules.dept.po.AuthDeptPO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDeptPO extends AuthDeptPO {

    private static final long serialVersionUID = 1L;

//    @ApiModelProperty(value = "部门Id")
//    private String deptId;
//
//    @ApiModelProperty(value = "部门名称")
//    private String deptName;

    @ApiModelProperty(value = "是否负责人 0否 1是", example = "0")
    @TableField("principal")
    private Integer principal;

}
