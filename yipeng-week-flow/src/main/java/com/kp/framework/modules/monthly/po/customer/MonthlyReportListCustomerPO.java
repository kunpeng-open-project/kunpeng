package com.kp.framework.modules.monthly.po.customer;


import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "MonthlyReportListCustomerPO", description = "MonthlyReportListCustomerPO")
public class MonthlyReportListCustomerPO extends MonthlyReportPO {

    @Schema(description = "用户姓名")
    private String userNamss;

    @Schema(description = "计划时间")
    private String planTime;

    @Schema(description = "完成时间", required = true)
    private List<LocalDate> completeDate;
}
