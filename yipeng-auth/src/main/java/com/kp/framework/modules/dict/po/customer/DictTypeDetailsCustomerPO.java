package com.kp.framework.modules.dict.po.customer;

import com.kp.framework.modules.dict.po.DictTypePO;
import com.kp.framework.modules.project.po.ProjectPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "DictTypeDetailsCustomerPO", description = "DictTypeDetailsCustomerPO")
public class DictTypeDetailsCustomerPO extends DictTypePO {

    @Schema(description = "所属项目集合")
    private List<ProjectPO> projectList;

    @Schema(description = "项目id集合")
    private List<String> projectIds;

    @Schema(description = "项目名称集合")
    private List<String> projectNames;
}
