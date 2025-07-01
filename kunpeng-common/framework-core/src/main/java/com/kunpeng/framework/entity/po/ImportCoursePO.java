package com.kunpeng.framework.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="ImportCoursePO对象", description="导入过程控制")
public class ImportCoursePO {

    @ApiModelProperty(value = "成功数")
    private Integer succeedNumber = 0;

    @ApiModelProperty(value = "失败数")
    private Integer errorNumber = 0;

    @ApiModelProperty(value = "总条数")
    private Integer totalNumber = 99999;

    @ApiModelProperty(value = "状态 1 执行中 2 执行完毕")
    private Integer state = 1;

    @ApiModelProperty(value = "异常信息")
    private String errorMessage;
}
