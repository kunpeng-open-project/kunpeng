package com.kp.framework.modules.logRecord.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kp.framework.entity.bo.ParentBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 系统内部接口调用记录-历史表。
 * @author lipeng
 * 2025-05-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("auth_interface_log_history")
@Schema(name = "InterfaceLogHistoryPO对象", description = "系统内部接口调用记录-历史表")
public class InterfaceLogHistoryPO extends ParentBO<InterfaceLogHistoryPO> {

    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    @Schema(description = "项目名称")
    @TableField("project_name")
    private String projectName;

    @Schema(description = "请求url")
    @TableField("url")
    private String url;

    @Schema(description = "请求uri")
    @TableField("uri")
    private String uri;

    @Schema(description = "接口名称")
    @TableField("name")
    private String name;

    @Schema(description = "请求方式")
    @TableField("method")
    private String method;

    @Schema(description = "入参")
    @TableField("parameters")
    private String parameters;

    @Schema(description = "出参")
    @TableField("result")
    private String result;

    @Schema(description = "访问时间 单位毫秒")
    @TableField("dispose_time")
    private Long disposeTime;

    @Schema(description = "访问时间 解释说明")
    @TableField("dispose_time_explain")
    private String disposeTimeExplain;

    @Schema(description = "客户端IP")
    @TableField("clinet_ip")
    private String clinetIp;

    @Schema(description = "接口调用时间")
    @TableField("call_time")
    private LocalDateTime callTime;

    @Schema(description = "来源")
    @TableField("plat_form")
    private String platForm;

    @Schema(description = "操作人id 或 项目id")
    @TableField("identification")
    private String identification;

    @Schema(description = "操作人手机号")
    @TableField("phone")
    private String phone;

    @Schema(description = "操作人姓名或 项目名称")
    @TableField("identification_name")
    private String identificationName;

    @Schema(description = "操作人工号")
    @TableField("serial")
    private String serial;

    @Schema(description = "请求状态 200 成功 500 失败")
    @TableField("status")
    private Integer status;

    @Schema(description = "返回内容")
    @TableField("message")
    private String message;
}
