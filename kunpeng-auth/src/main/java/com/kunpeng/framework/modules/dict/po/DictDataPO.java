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
 * @Description 字典数据表
 * @Date 2025-07-30 10:18:43
**/
@Data
@Accessors(chain = true)
@TableName("auth_dict_data")
@ApiModel(value = "DictDataPO对象", description = "字典数据表")
public class DictDataPO extends ParentBO {

    @ApiModelProperty("字典编码ID")
    @TableId(value = "dict_data_id", type = IdType.ASSIGN_UUID)
    private String dictDataId;

    @ApiModelProperty("字典类型ID")
    @TableField("dict_type_id")
    private String dictTypeId;

    @ApiModelProperty("字典排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("字典标签")
    @TableField("label")
    private String label;

    @ApiModelProperty("字典键值")
    @TableField("value")
    private String value;

    @ApiModelProperty("是否默认选中 1是 0否")
    @TableField("selected")
    private Integer selected;

    @ApiModelProperty("状态 0 停用 1 正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
