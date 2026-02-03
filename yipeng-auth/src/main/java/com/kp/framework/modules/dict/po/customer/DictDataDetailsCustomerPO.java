package com.kp.framework.modules.dict.po.customer;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.modules.dict.po.DictDataPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DictDataDetailsCustomerPO", description = "DictDataDetailsCustomerPO")
public class DictDataDetailsCustomerPO extends DictDataPO {

    @Schema(description = "字典名称")
    @TableField("dict_name")
    private String dictName;

    @Schema(description = "字典类型")
    @TableField("dict_type")
    private String dictType;
}
