package com.kp.framework.entity.po;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.InputStream;
import java.io.Serializable;

@Data
@Schema(name = "ZipFilePO对象", description = "压缩文件信息")
public class ZipFilePO implements Serializable {

    @Schema(description = "文件名称")
    public String fileName;

    @Schema(description = "文件大小")
    private Long fileSize;

    @Schema(description = "文件类型")
    private String fileType;

    @Schema(description = "文件流")
    private InputStream fileBody;

    public ZipFilePO(String fileName, Long fileSize, String fileType, InputStream fileBody) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.fileBody = fileBody;
    }

    public ZipFilePO(String fileName, InputStream fileBody) {
        this.fileName = fileName;
        this.fileBody = fileBody;
    }
}