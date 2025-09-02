package com.kp.framework.modules.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 用户部门关联表
 * @Date 2025-04-08
**/
@Data
@Accessors(chain = true)
@TableName("auth_user_dept")
@ApiModel(value = "UserDeptPO对象", description = "用户部门关联表")
public class UserDeptPO extends ParentBO {

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
