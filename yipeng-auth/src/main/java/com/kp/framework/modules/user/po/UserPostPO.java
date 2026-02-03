package com.kp.framework.modules.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户岗位关联表。
 * @author lipeng
 * 2025-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_user_post")
@Schema(name = "UserPostPO", description = "用户岗位关联表")
public class UserPostPO extends ParentBO<UserPostPO> {

    @Schema(description = "用户岗位Id")
    @TableId(value = "aup_id", type = IdType.ASSIGN_UUID)
    private String aupId;

    @Schema(description = "用户Id")
    @TableField("user_id")
    private String userId;

    @Schema(description = "岗位Id")
    @TableField("post_id")
    private String postId;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
