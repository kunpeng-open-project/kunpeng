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
 * @Description 用户岗位关联表
 * @Date 2025-04-21
**/
@Data
@Accessors(chain = true)
@TableName("auth_user_post")
@ApiModel(value = "UserPostPO对象", description = "用户岗位关联表")
public class UserPostPO extends ParentBO {

    @ApiModelProperty("用户岗位Id")
    @TableId(value = "aup_id", type = IdType.ASSIGN_UUID)
    private String aupId;

    @ApiModelProperty("用户Id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("岗位Id")
    @TableField("post_id")
    private String postId;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
