package com.kunpeng.framework.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value="EhcacheListCustomerPO", description="EhcacheListCustomerPO")
public class EhcacheListCustomerPO {

    @ApiModelProperty(value = "内容")
    private Object body;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "最后访问时间")
    private String lastAccessDate;

    @ApiModelProperty(value = "过期时间")
    private String expirationDate;

    @ApiModelProperty(value = "最后更新时间")
    private String lastUpdateDate;

    @ApiModelProperty(value = "命中次数")
    private Long htCount;

    @ApiModelProperty(value = "存活时间")
    private String timeToLive;

    @ApiModelProperty(value = "空闲时间")
    private String timeToIdle;

}
