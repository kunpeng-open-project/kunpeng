package com.kunpeng.framework.modules.logRecord.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kunpeng.framework.entity.bo.ParentBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author lipeng
 * @Description 系统外部接口调用记录-历史表
 * @Date 2025-05-28 14:44:21
**/
@Data
@Accessors(chain = true)
@TableName("auth_http_log_history")
@ApiModel(value = "HttpLogHistoryPO对象", description = "系统外部接口调用记录-历史表")
public class HttpLogHistoryPO extends ParentBO {

    @TableId(value = "uuid", type = IdType.ASSIGN_UUID)
    private String uuid;

    @ApiModelProperty("项目名称")
    @TableField("project_name")
    private String projectName;

    @ApiModelProperty("请求url")
    @TableField("url")
    private String url;

    @ApiModelProperty("请求uri")
    @TableField("uri")
    private String uri;

    @ApiModelProperty("接口名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("请求方式")
    @TableField("method")
    private String method;

    @ApiModelProperty("入参")
    @TableField("parameters")
    private String parameters;

    @ApiModelProperty("出参")
    @TableField("result")
    private String result;

    @ApiModelProperty("访问时间 单位毫秒")
    @TableField("dispose_time")
    private Long disposeTime;

    @ApiModelProperty("访问时间 解释说明")
    @TableField("dispose_time_explain")
    private String disposeTimeExplain;

    @ApiModelProperty("客户端IP")
    @TableField("clinet_ip")
    private String clinetIp;

    @ApiModelProperty("接口调用时间")
    @TableField("call_time")
    private LocalDateTime callTime;

    @ApiModelProperty("来源")
    @TableField("plat_form")
    private String platForm;

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
}
