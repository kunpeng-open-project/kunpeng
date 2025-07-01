package com.kunpeng.framework.modules.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunpeng.framework.entity.bo.ParentBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 用户项目关联表
 * @Date 2025-04-21 16:18:35
**/
@Data
@Accessors(chain = true)
@TableName("auth_user_project")
@ApiModel(value = "UserProjectPO对象", description = "用户项目关联表")
public class UserProjectPO extends ParentBO {

    @ApiModelProperty("用户项目Id")
    @TableId(value = "aup_id", type = IdType.ASSIGN_UUID)
    private String aupId;

    @ApiModelProperty("用户Id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("项目Id")
    @TableField("project_id")
    private String projectId;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
