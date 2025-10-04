package com.kp.framework.modules.monthly.po.customer;

import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.modules.monthly.po.MonthlyReportPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "MonthlyReportReviewListCustomerPO", description = "MonthlyReportReviewListCustomerPO")
public class MonthlyReportReviewListCustomerPO extends MonthlyReportPO {

    @ApiModelProperty("用户ids")
    private String userIds;

    @ApiModelProperty(value = "计划时间")
    private String planTime;

//    @ApiModelProperty(value = "完成时间", required = true)
//    private List<LocalDate> completeDate;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "岗位名称")
    private String postName;

    @ApiModelProperty(value = "用户信息")
    private List<JSONObject> userList;
}
