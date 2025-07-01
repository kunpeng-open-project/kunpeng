package com.kunpeng.framework.modules.user.po.param;

import com.kunpeng.framework.annotation.verify.KPNotNull;
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
 * @since 2024-05-06
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserDeptParamPO", description = "UserDeptParamPO")
public class UserDeptParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门Id", example = "01", required = true)
    @KPNotNull(errMeg = "部门Id不能为空")
    private String deptId;

    @ApiModelProperty(value = "是否负责人 0否 1是", example = "0", required = true)
    @KPNotNull(errMeg = "请选择是否是负责人")
    private Integer principal;

}
