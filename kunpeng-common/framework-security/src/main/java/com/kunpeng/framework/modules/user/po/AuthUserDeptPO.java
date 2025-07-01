package com.kunpeng.framework.modules.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunpeng.framework.common.parent.ParentSecurityBO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 用户部门关联表
 * </p>
 *
 * @author lipeng
 * @since 2024-06-04
 */
@Data
@TableName("auth_user_dept")
public class AuthUserDeptPO extends ParentSecurityBO<AuthUserDeptPO> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户部门Id")
    @TableId(value = "aud_id", type = IdType.ASSIGN_UUID)
    private String audId;

    @ApiModelProperty("用户Id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("部门Id")
    @TableField("dept_id")
    private String deptId;

    @ApiModelProperty("是否负责人 0否 1是")
    @TableField("principal")
    private Integer principal;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
