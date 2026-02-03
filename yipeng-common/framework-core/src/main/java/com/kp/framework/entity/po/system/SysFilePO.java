package com.kp.framework.entity.po.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 硬盘统计信息。
 * @author lipeng
 * 2025/10/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SysFilePO", description = "磁盘信息")
public class SysFilePO {

    @Schema(description = "盘符路径")
    private String dirName;

    @Schema(description = "盘符类型")
    private String sysTypeName;

    @Schema(description = "文件类型")
    private String typeName;

    @Schema(description = "总大小")
    private String total;

    @Schema(description = "剩余大小")
    private String free;

    @Schema(description = "已经使用量")
    private String used;

    @Schema(description = "资源的使用率")
    private String usage;
}
