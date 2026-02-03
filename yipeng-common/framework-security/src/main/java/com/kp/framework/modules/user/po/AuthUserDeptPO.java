package com.kp.framework.modules.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.common.parent.ParentSecurityBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户部门关联表
 * </p>
 *
 * @author lipeng
 * @since 2024-06-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("auth_user_dept")
public class AuthUserDeptPO extends ParentSecurityBO<AuthUserDeptPO> {

    @Schema(description = "用户部门Id")
    @TableId(value = "aud_id", type = IdType.ASSIGN_UUID)
    private String audId;

    @Schema(description = "用户Id")
    @TableField("user_id")
    private String userId;

    @Schema(description = "部门Id")
    @TableField("dept_id")
    private String deptId;

    @Schema(description = "是否负责人 0否 1是")
    @TableField("principal")
    private Integer principal;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
