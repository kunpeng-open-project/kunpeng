package com.kunpeng.framework.modules.dict.po;

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
 * @Description 字典类型表
 * @Date 2025-07-03
**/
@Data
@Accessors(chain = true)
@TableName("auth_dict_type")
@ApiModel(value = "DictTypePO对象", description = "字典类型表")
public class DictTypePO extends ParentBO {

    @ApiModelProperty("字典类型ID")
    @TableId(value = "dict_type_id", type = IdType.ASSIGN_UUID)
    private String dictTypeId;

    @ApiModelProperty("字典名称")
    @TableField("dict_name")
    private String dictName;

    @ApiModelProperty("字典类型")
    @TableField("dict_type")
    private String dictType;

    @ApiModelProperty("状态 0 停用 1 正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
