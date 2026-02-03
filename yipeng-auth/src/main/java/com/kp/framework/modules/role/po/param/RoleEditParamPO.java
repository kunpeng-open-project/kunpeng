package com.kp.framework.modules.role.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPLength;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 角色信息编辑入参。
 * @author lipeng
 * 2025-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "RoleEditParamPO对象", description = "角色信息编辑入参")
public class RoleEditParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色Id", example = "角色Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "role_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入角色Id")
    @KPMaxLength(max = 36, errMeg = "角色Id不能超过36个字符")
    private String roleId;

    @Schema(description = "角色名称", example = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("role_name")
    @KPNotNull(errMeg = "请输入角色名称")
    @KPLength(min = 2, max = 68, errMeg = "角色名称须2~68个字符")
    private String roleName;

    @Schema(description = "角色编号", example = "角色编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("role_code")
    @KPNotNull(errMeg = "请输入角色编号")
    @KPLength(min = 2, max = 100, errMeg = "角色编号须2~100个字符")
    private String roleCode;

    @Schema(description = "角色状态 0停用 1正常", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("status")
    @KPNotNull(errMeg = "请选中角色状态 0停用 1正常")
    private Integer status;

    @Schema(description = "备注", example = "备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;

    @Schema(description = "所属项目Id集合", requiredMode = Schema.RequiredMode.REQUIRED, example = "[\"bfff793893f9b3f08d736389529a1115\"]")
    @KPNotNull(errMeg = "请选择项目")
    private List<String> projectIds;
}
