package com.kp.framework.modules.dict.po.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.modules.dict.po.DictDataPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "DictDataDetailsCustomerPO", description = "DictDataDetailsCustomerPO")
public class DictDataDetailsCustomerPO extends DictDataPO {

    @ApiModelProperty("字典名称")
    @TableField("dict_name")
    private String dictName;

    @ApiModelProperty("字典类型")
    @TableField("dict_type")
    private String dictType;
}
