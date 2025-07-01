package com.kunpeng.framework.modules.post.po.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.kunpeng.framework.annotation.verify.KPLength;
import com.kunpeng.framework.annotation.verify.KPMaxLength;
import com.kunpeng.framework.annotation.verify.KPNotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 岗位信息编辑入参
 * @Date 2025-03-31
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "PostEditParamPO对象", description = "岗位信息编辑入参")
public class PostEditParamPO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位Id", example = "岗位Id", required = true)
    @TableId(value = "post_id", type = IdType.ASSIGN_UUID)
    @KPNotNull(errMeg = "请输入岗位Id")
    @KPMaxLength(max = 36, errMeg = "岗位Id不能超过36个字符")
    private String postId;

    @ApiModelProperty(value = "岗位编码", example = "岗位编码", required = true)
    @TableField("post_code")
    @KPNotNull(errMeg = "请输入岗位编码")
    @KPLength(min = 2, max = 64, errMeg = "岗位编码须2~64个字符")
    private String postCode;

    @ApiModelProperty(value = "岗位名称", example = "岗位名称", required = true)
    @TableField("post_name")
    @KPNotNull(errMeg = "请输入岗位名称")
    @KPLength(min = 2, max = 64, errMeg = "岗位名称须2~64个字符")
    private String postName;

    @ApiModelProperty(value = "状态 0停用 1正常", example = "0", required = true)
    @TableField("status")
    @KPNotNull(errMeg = "请选中状态 0停用 1正常")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remark")
    @KPMaxLength(max = 255, errMeg = "备注不能超过255个字符")
    private String remark;
}
