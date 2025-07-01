package com.kunpeng.framework.entity.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Author lipeng
 * @Description 内容改变记录
 * @Date 2024/2/23 16:46
 * @return
 **/
@Data
public class ObjectChangeLogBO extends ParentBO<ObjectChangeLogBO> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "操作类名和方法名")
    private String className;

    @ApiModelProperty(value = "标识 唯一外键（业务通过这个字段关联）")
    private String identification;

    @ApiModelProperty(value = "操作类型")
    private String operateType;

    @ApiModelProperty(value = "业务类型")
    private String businessType;

    @ApiModelProperty(value = "改变记录")
    private String changeBody;

    @ApiModelProperty(value = "请求url")
    private String url;

    @ApiModelProperty(value = "客户端IP")
    private String clinetIp;

    @ApiModelProperty(value = "操作人手机号")
    private String phone;

    @ApiModelProperty(value = "工号")
    private String serial;

    @ApiModelProperty(value = "请求全部参数记录")
    private String parameter;




}
