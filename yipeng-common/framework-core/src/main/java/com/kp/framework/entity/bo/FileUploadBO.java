package com.kp.framework.entity.bo;

import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 文件上传bo。
 * @author lipeng
 * 2022/1/6
 */
@Data
@Accessors(chain = true)
@Schema(name = "FileUploadBO", description = "文件信息")
public class FileUploadBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "文件名称")
    @KPNotNull(errMeg = "文件名称不能为空")
    private String fileName;

    @Schema(description = "文件大小")
    @KPNotNull(errMeg = "文件大小不能为空")
    private Long fileSize;

    @Schema(description = "文件类型")
    @KPNotNull(errMeg = "文件类型不能为空")
    private String fileType;

    @Schema(description = "存储路径")
    @KPNotNull(errMeg = "存储路径不能为空")
    private String filePath;

    public FileUploadBO() {
    }

    public FileUploadBO(String fileName, Long fileSize, String fileType, String filePath) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.filePath = filePath;
    }
}
