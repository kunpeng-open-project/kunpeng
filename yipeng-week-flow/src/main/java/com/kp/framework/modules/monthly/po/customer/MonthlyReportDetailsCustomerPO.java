package com.kp.framework.modules.monthly.po.customer;

import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import com.kp.framework.modules.monthly.po.MonthlyReportUserPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "MonthlyReportDetailsCustomerPO", description = "MonthlyReportDetailsCustomerPO")
public class MonthlyReportDetailsCustomerPO extends MonthlyReportPO {

    @ApiModelProperty(value = "月度计划责任人集合")
    private List<MonthlyReportUserPO> monthlyReportUserList;

    @ApiModelProperty(value = "月度计划责任人Id集合")
    private List<String> userIds;

    @ApiModelProperty(value = "月度计划责任人姓名集合")
    private List<String> userNames;

    @ApiModelProperty(value = "计划时间")
    private String planTime;

    @ApiModelProperty(value = "完成时间")
    private List<LocalDate> completeDate;

    @ApiModelProperty("岗位名称")
    private String postName;

    @ApiModelProperty("部门名称")
    private String deptName;
}
