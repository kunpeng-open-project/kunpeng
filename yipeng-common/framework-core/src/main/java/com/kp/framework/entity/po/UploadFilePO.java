package com.kp.framework.entity.po;


import com.kp.framework.annotation.verify.KPNotNull;
import com.kp.framework.constant.MinioConstant;
import com.kp.framework.utils.kptool.KPStringUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(name = "UploadFilePO", description = "上传文件返回的文件路径")
public class UploadFilePO implements Serializable {

    @KPNotNull(errMeg = "请输入桶名称")
    @Schema(description = "桶名称", requiredMode = Schema.RequiredMode.REQUIRED, example = MinioConstant.bucketNames)
    public String bucketName;

    @KPNotNull(errMeg = "请输入文件路径")
    @Schema(description = "文件路径", requiredMode = Schema.RequiredMode.REQUIRED)
    private String filePath;

    public UploadFilePO(String filePath) {
        if (KPStringUtil.isEmpty(filePath)) {
            this.bucketName = "";
            this.filePath = "";
            return;
        }
        int index = filePath.indexOf("/");
        this.bucketName = filePath.substring(0, index);
        this.filePath = filePath.substring(index + 1);
    }
}
