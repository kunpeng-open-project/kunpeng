package com.kp.framework.entity.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author lipeng
 * @Description 文件上传bo
 * @Date 2022/1/6 9:30
 * @return
 **/
@Data
@Accessors(chain = true)
@ApiModel(value="FilePO对象", description="文件信息")
public class FileUploadBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "存储路径")
    private String filePath;

    public FileUploadBO(String fileName, Long fileSize, String fileType, String filePath){
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.filePath = filePath;
    }
}
