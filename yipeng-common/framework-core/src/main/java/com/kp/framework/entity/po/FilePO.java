package com.kp.framework.entity.po;


import com.kp.framework.annotation.verify.KPNotNull;
import com.kp.framework.constant.MinioConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(value="FilePO对象", description="文件信息")
public class FilePO implements Serializable {

    @KPNotNull(errMeg = "请输入桶名称")
    @ApiModelProperty(value = "桶名称", required = true, example = MinioConstant.bucketNames)
    public String bucketName;

    @KPNotNull(errMeg = "请输入文件名称-不影响下载文件 只会影响下载后后端返回的文件名称  文件名称在header里面的filename")
    @ApiModelProperty(value = "文件名称", required = true)
    private String fileName;

    @KPNotNull(errMeg = "请输入文件路径")
    @ApiModelProperty(value = "文件路径", required = true)
    private String filePath;
}
