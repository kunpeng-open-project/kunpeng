package com.kp.framework.modules.monthly.po.customer;

import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "MonthlyReportListCustomerPO", description = "MonthlyReportListCustomerPO")
public class MonthlyReportListCustomerPO extends MonthlyReportPO {

    @ApiModelProperty("用户姓名")
    private String userNamss;

    @ApiModelProperty(value = "计划时间")
    private String planTime;

    @ApiModelProperty(value = "完成时间", required = true)
    private List<LocalDate> completeDate;
}
