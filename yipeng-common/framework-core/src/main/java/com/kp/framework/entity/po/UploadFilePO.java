package com.kp.framework.entity.po;


import com.kp.framework.annotation.verify.KPNotNull;
import com.kp.framework.constant.MinioConstant;
import com.kp.framework.utils.kptool.KPStringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@ApiModel(value="UploadFilePO", description="上传文件返回的文件路径")
public class UploadFilePO implements Serializable {

    @KPNotNull(errMeg = "请输入桶名称")
    @ApiModelProperty(value = "桶名称", required = true, example = MinioConstant.bucketNames)
    public String bucketName;

    @KPNotNull(errMeg = "请输入文件路径")
    @ApiModelProperty(value = "文件路径", required = true)
    private String filePath;

    public UploadFilePO(String filePath) {
        if (KPStringUtil.isEmpty(filePath)){
            this.bucketName= "";
            this.filePath= "";
            return;
        }
        int index = filePath.indexOf("/");
        this.bucketName= filePath.substring(0, index);
        this.filePath= filePath.substring(index + 1);
    }
}
