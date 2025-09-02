package com.kp.framework.modules.role.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.annotation.verify.KPLength;
import com.kp.framework.annotation.verify.KPMaxLength;
import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author lipeng
 * @Description 角色信息编辑入参
 * @Date 2025-03-31
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "RoleEditParamPO对象", description = "角色信息编辑入参")
public class RoleEditParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色Id", example = "角色Id", required = true)
    @TableId(value = "role_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入角色Id")
    @KPMaxLength(max = 36, errMeg = "角色Id不能超过36个字符")
    private String roleId;

    @ApiModelProperty(value = "角色名称", example = "角色名称", required = true)
    @TableField("role_name")
    @KPNotNull(errMeg = "请输入角色名称")
    @KPLength(min = 2, max = 68, errMeg = "角色名称须2~68个字符")
    private String roleName;

    @ApiModelProperty(value = "角色编号", example = "角色编号", required = true)
    @TableField("role_code")
    @KPNotNull(errMeg = "请输入角色编号")
    @KPLength(min = 2, max = 100, errMeg = "角色编号须2~100个字符")
    private String roleCode;

    @ApiModelProperty(value = "角色状态 0停用 1正常", example = "0", required = true)
    @TableField("status")
    @KPNotNull(errMeg = "请选中角色状态 0停用 1正常")
    private Integer status;

    @ApiModelProperty(value = "备注", example = "备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;

    @ApiModelProperty(value = "所属项目Id集合", required = true, example = "[\"bfff793893f9b3f08d736389529a1115\"]")
    @KPNotNull(errMeg = "请选择项目")
    private List<String> projectIds;
}
