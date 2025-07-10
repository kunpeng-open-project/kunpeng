package com.kunpeng.framework.constant;

public class MinioConstant {

    //临时上传目录
    public static final String TEMPORARY_BUCKET_NAME = "temporary-upload-file";

    //鉴权项目传目录
    public static final String AUTH_BUCKET_NAME = "authentication";


    public static final String TEMPLATE = "template/";

    public static final String DEFAULT = "default/";


    public static final String bucketNames =
            MinioConstant.TEMPORARY_BUCKET_NAME + "," +
                    MinioConstant.AUTH_BUCKET_NAME;

}
