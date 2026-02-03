package com.kp.framework.entity.po;


import com.kp.framework.annotation.verify.KPNotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 下载文件入参。
 * @author lipeng
 * 2022/3/7
 */
@Data
@Accessors(chain = true)
@Schema(name = "FilePO", description = "下载文件入参")
public class FilePO implements Serializable {

//    @KPNotNull(errMeg = "请输入桶名称")
//    @Schema(value = "桶名称", requiredMode = Schema.RequiredMode.REQUIRED, example = MinioConstant.bucketNames)
//    public String bucketName;

    @KPNotNull(errMeg = "请输入文件名称-不影响下载文件 只会影响下载后后端返回的文件名称  文件名称在header里面的filename")
    @Schema(description = "文件名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fileName;

    @KPNotNull(errMeg = "请输入文件路径")
    @Schema(description = "文件路径", requiredMode = Schema.RequiredMode.REQUIRED)
    private String filePath;
}
