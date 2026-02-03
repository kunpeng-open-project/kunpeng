package com.kp.framework.modules.monthly.po.customer;

import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import com.kp.framework.modules.monthly.po.MonthlyReportUserPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "MonthlyReportDetailsCustomerPO", description = "MonthlyReportDetailsCustomerPO")
public class MonthlyReportDetailsCustomerPO extends MonthlyReportPO {

    @Schema(description = "月度计划责任人集合")
    private List<MonthlyReportUserPO> monthlyReportUserList;

    @Schema(description = "月度计划责任人Id集合")
    private List<String> userIds;

    @Schema(description = "月度计划责任人姓名集合")
    private List<String> userNames;

    @Schema(description = "计划时间")
    private String planTime;

    @Schema(description = "完成时间")
    private List<LocalDate> completeDate;

    @Schema(description = "岗位名称")
    private String postName;

    @Schema(description = "部门名称")
    private String deptName;
}
