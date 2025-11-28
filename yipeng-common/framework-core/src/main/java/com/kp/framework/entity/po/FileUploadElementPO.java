package com.kp.framework.entity.po;

import com.kp.framework.entity.bo.FileUploadBO;
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
@ApiModel(value = "FilePOElement对象", description = "文件信息")
public class FileUploadElementPO extends FileUploadBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "前端唯一标识")
    private String uid;
}
