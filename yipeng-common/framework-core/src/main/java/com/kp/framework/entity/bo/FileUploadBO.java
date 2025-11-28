package com.kp.framework.entity.bo;

import com.kp.framework.annotation.verify.KPNotNull;
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
    @KPNotNull(errMeg = "文件名称不能为空")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    @KPNotNull(errMeg = "文件大小不能为空")
    private Long fileSize;

    @ApiModelProperty(value = "文件类型")
    @KPNotNull(errMeg = "文件类型不能为空")
    private String fileType;

    @ApiModelProperty(value = "存储路径")
    @KPNotNull(errMeg = "存储路径不能为空")
    private String filePath;

    public FileUploadBO(){}

    public FileUploadBO(String fileName, Long fileSize, String fileType, String filePath){
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.filePath = filePath;
    }
}
