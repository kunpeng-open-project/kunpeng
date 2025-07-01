package com.kunpeng.framework.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@ApiModel(value="ImportPO对象", description="导入内容信息")
public class ImportPO {

    @ApiModelProperty(value = "唯一标识")
    private String identification;

    public ImportPO(){
        this.identification = UUID.randomUUID().toString();
    }
}
