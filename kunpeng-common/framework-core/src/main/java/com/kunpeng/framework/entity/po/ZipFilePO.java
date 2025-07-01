package com.kunpeng.framework.entity.po;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.InputStream;
import java.io.Serializable;

@Data
@ApiModel(value="ZipFilePO对象", description="文件信息")
public class ZipFilePO implements Serializable {

    @ApiModelProperty(value = "文件名称")
    public String fileName;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件流")
    private InputStream fileBody;

    public ZipFilePO(String fileName, Long fileSize, String fileType, InputStream fileBody){
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.fileBody = fileBody;
    }

    public ZipFilePO(String fileName, InputStream fileBody){
        this.fileName = fileName;
        this.fileBody = fileBody;
    }
}