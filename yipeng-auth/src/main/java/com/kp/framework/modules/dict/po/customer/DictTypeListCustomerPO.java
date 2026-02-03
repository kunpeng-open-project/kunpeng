package com.kp.framework.modules.dict.po.customer;

import com.kp.framework.modules.dict.po.DictTypePO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DictTypeListCustomerPO", description = "DictTypeListCustomerPO")
public class DictTypeListCustomerPO extends DictTypePO {

    @Schema(description = "项目名称集合")
    private String projectNames;
}
