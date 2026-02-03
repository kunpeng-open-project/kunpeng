package com.kp.framework.modules.post.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 岗位信息列表查询入参。
 * @author lipeng
 * 2025-03-31
 */
@Data
//@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(name = "PostListParamPO对象", description = "岗位信息列表查询入参")
public class PostListParamPO extends PageBO {

    @Schema(description = "岗位编码")
    @TableField("post_code")
    private String postCode;

    @Schema(description = "岗位名称")
    @TableField("post_name")
    private String postName;

    @Schema(description = "状态 0停用 1正常")
    @TableField("status")
    private Integer status;
}
