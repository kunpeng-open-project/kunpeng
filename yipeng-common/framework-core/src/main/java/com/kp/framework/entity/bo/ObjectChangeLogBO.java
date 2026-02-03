package com.kp.framework.entity.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内容改变记录。
 * @author lipeng
 * 2024/2/23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(name = "ObjectChangeLogBO", description = "内容改变记录")
public class ObjectChangeLogBO extends ParentBO<ObjectChangeLogBO> {

    @Schema(description = "主键")
    private String uuid;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "操作类名和方法名")
    private String className;

    @Schema(description = "标识 唯一外键（业务通过这个字段关联）")
    private String identification;

    @Schema(description = "操作类型")
    private String operateType;

    @Schema(description = "业务类型")
    private String businessType;

    @Schema(description = "改变记录")
    private String changeBody;

    @Schema(description = "请求url")
    private String url;

    @Schema(description = "客户端IP")
    private String clinetIp;

    @Schema(description = "操作人手机号")
    private String phone;

    @Schema(description = "工号")
    private String serial;

    @Schema(description = "请求全部参数记录")
    private String parameter;
}
