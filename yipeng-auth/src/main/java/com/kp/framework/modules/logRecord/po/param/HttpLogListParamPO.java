package com.kp.framework.modules.logRecord.po.param;

import com.baomidou.mybatisplus.annotation.TableField;
import com.kp.framework.annotation.verify.KPNotNull;
import com.kp.framework.entity.bo.PageBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 系统外部接口调用记录列表查询入参。
 * @author lipeng
 * 2025-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name = "HttpLogListParamPO对象", description = "系统外部接口调用记录列表查询入参")
public class HttpLogListParamPO extends PageBO {

    @Schema(description = "项目名称")
    @TableField("project_name")
    private String projectName;

    @Schema(description = "请求uri")
    @TableField("uri")
    private String uri;

    @Schema(description = "接口名称")
    @TableField("name")
    private String name;

    @Schema(description = "入参")
    @TableField("parameters")
    private String parameters;

    @Schema(description = "出参")
    @TableField("result")
    private String result;

    @Schema(description = "客户端IP")
    @TableField("clinet_ip")
    private String clinetIp;

    @Schema(description = "接口调用时间")
    @TableField("call_time")
    private LocalDate callTime;

    @Schema(description = "操作人id 或 项目id")
    @TableField("identification")
    private String identification;

    @Schema(description = "操作人姓名或项目名称")
    @TableField("identification_name")
    private String identificationName;

    @Schema(description = "操作人手机号")
    @TableField("phone")
    private String phone;

    @Schema(description = "操作人工号")
    @TableField("serial")
    private String serial;

    @Schema(description = "请求状态 200 成功 500 失败")
    @TableField("status")
    private Integer status;

    @Schema(description = "返回内容")
    @TableField("message")
    private String message;

    @Schema(description = "日志级别 1 最近日志 2 历史日志", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @KPNotNull(errMeg = "请选择日志级别")
    private Integer level;
}
