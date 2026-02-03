package com.kp.framework.microservices.auth.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 岗位信息表。
 * @author lipeng
 * 2025-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "PostPO对象", description = "岗位信息表")
public class PostFeignPO extends ParentBO<PostFeignPO> {

    @Schema(description = "岗位Id")
    @TableId(value = "post_id", type = IdType.ASSIGN_UUID)
    private String postId;

    @Schema(description = "岗位编码")
    @TableField("post_code")
    private String postCode;

    @Schema(description = "岗位名称")
    @TableField("post_name")
    private String postName;

    @Schema(description = "状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
