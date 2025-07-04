package com.kunpeng.framework.modules.dict.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kunpeng.framework.entity.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author lipeng
 * @Description 字典数据列表查询入参
 * @Date 2025-07-03
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "DictDataListParamPO对象", description = "字典数据列表查询入参")
public class DictDataListParamPO extends PageBO {

    @ApiModelProperty("字典类型ID")
    @TableField("dict_type_id")
    private String dictTypeId;

    @ApiModelProperty("字典标签")
    @TableField("label")
    private String label;

    @ApiModelProperty("状态 0 停用 1 正常")
    @TableField("status")
    private Integer status;
}
