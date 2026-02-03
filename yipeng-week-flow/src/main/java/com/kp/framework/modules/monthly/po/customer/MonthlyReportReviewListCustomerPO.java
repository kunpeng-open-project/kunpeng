package com.kp.framework.modules.monthly.po.customer;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "MonthlyReportReviewListCustomerPO", description = "MonthlyReportReviewListCustomerPO")
public class MonthlyReportReviewListCustomerPO extends MonthlyReportPO {

    @Schema(description = "用户ids")
    private String userIds;

    @Schema(description = "计划时间")
    private String planTime;

//    @Schema(description =value = "完成时间", required = true)
//    private List<LocalDate> completeDate;

    @Schema(description = "部门名称")
    private String deptName;

    @Schema(description = "岗位名称")
    private String postName;

    @Schema(description = "用户信息")
    private List<JSONObject> userList;
}
