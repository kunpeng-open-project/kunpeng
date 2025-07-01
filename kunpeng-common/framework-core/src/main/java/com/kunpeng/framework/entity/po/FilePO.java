package com.kunpeng.framework.entity.po;


import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(value="FilePO对象", description="文件信息")
public class FilePO implements Serializable {

//    @ApiModelProperty(value = "桶名称", required = true, example = MinioConstant.bucketNames)
//    public String bucketName;
//
//    @ApiModelProperty(value = "文件名称", required = true)
//    private String fileName;
//
//    @ApiModelProperty(value = "文件路径", required = true)
//    private String filePath;
}
