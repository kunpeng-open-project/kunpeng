package com.kunpeng.framework.modules.dict.po.customer;

import com.kunpeng.framework.modules.dict.po.DictTypePO;
import com.kunpeng.framework.modules.project.po.ProjectPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "DictTypeDetailsCustomerPO", description = "DictTypeDetailsCustomerPO")
public class DictTypeDetailsCustomerPO extends DictTypePO {

    @ApiModelProperty(value = "所属项目集合")
    private List<ProjectPO> projectList;

    @ApiModelProperty(value = "项目id集合")
    private List<String> projectIds;

    @ApiModelProperty(value = "项目名称集合")
    private List<String> projectNames;
}
