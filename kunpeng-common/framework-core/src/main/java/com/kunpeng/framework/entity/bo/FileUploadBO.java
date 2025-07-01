package com.kunpeng.framework.entity.bo;

import java.io.Serializable;

/**
 * @Author lipeng
 * @Description 文件上传bo
 * @Date 2022/1/6 9:30
 * @return
 **/
public class FileUploadBO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileName;

    private Long fileSize;

    private String fileType;

    //保存路径
    private String filePath;

    public FileUploadBO(String fileName, Long fileSize, String fileType, String filePath){
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.filePath = filePath;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return this.fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
