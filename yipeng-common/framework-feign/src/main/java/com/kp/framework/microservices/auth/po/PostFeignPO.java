package com.kp.framework.microservices.auth.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 岗位信息表
 * @Date 2025-04-07
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "PostPO对象", description = "岗位信息表")
public class PostFeignPO extends ParentBO {

    @ApiModelProperty("岗位Id")
    @TableId(value = "post_id", type = IdType.ASSIGN_UUID)
    private String postId;

    @ApiModelProperty("岗位编码")
    @TableField("post_code")
    private String postCode;

    @ApiModelProperty("岗位名称")
    @TableField("post_name")
    private String postName;

    @ApiModelProperty("状态 0停用 1正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
