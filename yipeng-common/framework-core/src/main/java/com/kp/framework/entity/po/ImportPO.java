package com.kp.framework.entity.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@Schema(name = "ImportPO对象", description = "导入内容信息")
public class ImportPO {

    @Schema(description = "唯一标识")
    private String identification;

    public ImportPO() {
        this.identification = UUID.randomUUID().toString();
    }
}
