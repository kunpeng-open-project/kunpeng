package com.kp.framework.entity.po.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 操作系统统计信息。
 * @author lipeng
 * 2025/10/17
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Sys", description = "服务器信息")
public class SysPO {

    @Schema(description = "服务器名称")
    private String computerName;

    @Schema(description = "服务器IP")
    private String computerIp;

    @Schema(description = "项目路径")
    private String userDir;

    @Schema(description = "操作系统")
    private String osName;

    @Schema(description = "系统架构")
    private String osArch;
}
