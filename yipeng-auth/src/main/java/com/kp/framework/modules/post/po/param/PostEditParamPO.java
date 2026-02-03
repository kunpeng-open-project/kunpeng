package com.kp.framework.modules.post.po.param;

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

/**
 * 岗位信息编辑入参。
 * @author lipeng
 * 2025-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "PostEditParamPO对象", description = "岗位信息编辑入参")
public class PostEditParamPO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "岗位Id", example = "岗位Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableId(value = "post_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入岗位Id")
    @KPMaxLength(max = 36, errMeg = "岗位Id不能超过36个字符")
    private String postId;

    @Schema(description = "岗位编码", example = "岗位编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("post_code")
    @KPNotNull(errMeg = "请输入岗位编码")
    @KPLength(min = 2, max = 64, errMeg = "岗位编码须2~64个字符")
    private String postCode;

    @Schema(description = "岗位名称", example = "岗位名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("post_name")
    @KPNotNull(errMeg = "请输入岗位名称")
    @KPLength(min = 2, max = 64, errMeg = "岗位名称须2~64个字符")
    private String postName;

    @Schema(description = "状态 0停用 1正常", example = "0", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField("status")
    @KPNotNull(errMeg = "请选中状态 0停用 1正常")
    private Integer status;

    @Schema(description = "备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
