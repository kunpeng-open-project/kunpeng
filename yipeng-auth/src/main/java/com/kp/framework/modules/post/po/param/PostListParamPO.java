package com.kp.framework.modules.post.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 岗位信息列表查询入参
 * @Date 2025-03-31
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "PostListParamPO对象", description = "岗位信息列表查询入参")
public class PostListParamPO extends PageBO {

    @ApiModelProperty("岗位编码")
    @TableField("post_code")
    private String postCode;

    @ApiModelProperty("岗位名称")
    @TableField("post_name")
    private String postName;

    @ApiModelProperty("状态 0停用 1正常")
    @TableField("status")
    private Integer status;
}
