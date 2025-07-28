package com.kunpeng.framework.modules.logRecord.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kunpeng.framework.annotation.verify.KPNotNull;
import com.kunpeng.framework.entity.bo.PageBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author lipeng
 * @Description 系统外部接口调用记录列表查询入参
 * @Date 2025-05-21
**/
@Data
@Accessors(chain = true)
@ApiModel(value = "HttpLogListParamPO对象", description = "系统外部接口调用记录列表查询入参")
public class HttpLogListParamPO extends PageBO {

    @ApiModelProperty("项目名称")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty("请求uri")
    @TableField("uri")
    private String uri;

    @ApiModelProperty("接口名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("入参")
    @TableField("parameters")
    private String parameters;

    @ApiModelProperty("出参")
    @TableField("result")
    private String result;

    @ApiModelProperty("客户端IP")
    @TableField("clinet_ip")
    private String clinetIp;

    @ApiModelProperty("接口调用时间")
    @TableField("call_time")
    private LocalDate callTime;

    @ApiModelProperty("操作人id 或 项目id")
    @TableField("identification")
    private String identification;

    @ApiModelProperty("操作人姓名或项目名称")
    @TableField("identification_name")
    private String identificationName;

    @ApiModelProperty("操作人手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("操作人工号")
    @TableField("serial")
    private String serial;

    @ApiModelProperty("请求状态 200 成功 500 失败")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("返回内容")
    @TableField("message")
    private String message;

    @ApiModelProperty(value = "日志级别 1 最近日志 2 历史日志", required = true, example = "1")
    @KPNotNull(errMeg = "请选择日志级别")
    private Integer level;
}
