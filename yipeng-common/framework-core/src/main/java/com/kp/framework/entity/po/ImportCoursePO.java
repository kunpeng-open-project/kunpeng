package com.kp.framework.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(name = "ImportCoursePO对象", description = "导入过程控制")
public class ImportCoursePO {

    @Schema(description = "成功数")
    private Integer succeedNumber = 0;

    @Schema(description = "失败数")
    private Integer errorNumber = 0;

    @Schema(description = "总条数")
    private Integer totalNumber = 99999;

    @Schema(description = "状态 1 执行中 2 执行完毕")
    private Integer state = 1;

    @Schema(description = "异常信息")
    private String errorMessage;
}
