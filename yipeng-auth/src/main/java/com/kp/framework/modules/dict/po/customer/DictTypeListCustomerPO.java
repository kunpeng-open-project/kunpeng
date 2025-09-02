package com.kp.framework.modules.dict.po.customer;

import com.kp.framework.modules.dict.po.DictTypePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value = "DictTypeListCustomerPO", description = "DictTypeListCustomerPO")
public class DictTypeListCustomerPO extends DictTypePO {

    @ApiModelProperty(value = "项目名称集合")
    private String projectNames;
}
