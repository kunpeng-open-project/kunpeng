package com.kp.framework.entity.po;

import com.kp.framework.entity.bo.FileUploadBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "FilePOElement对象", description = "FilePOElement对象")
public class FileUploadElementPO extends FileUploadBO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "前端唯一标识")
    private String uid;
}
