package com.kunpeng.framework.constant;

public class MinioConstant {

    //临时上传目录
    public static final String TEMPORARY_BUCKET_NAME = "temporary-upload-file";

    //督办上传目录
    public static final String SUPERVISE_BUCKET_NAME = "supervise-upload-file";

    //需求提报
    public static final String BPM_BUCKET_NAME = "bpm-upload-file";

    //创新申报
    public static final String INNOVATION_BUCKET_NAME = "innovation-upload-file";

    //班组
    public static final String TEAMAPPRAISING_BUCKET_NAME = "teamappraising-upload-file";

    //消防
    public static final String FIREFIGHTING_BUCKET_NAME = "firefighting-upload-file";

    public static final String TEMPLATE = "template/";

    public static final String DEFAULT = "default/";




    public static final String bucketNames =
            MinioConstant.TEMPORARY_BUCKET_NAME + "," +
                    MinioConstant.SUPERVISE_BUCKET_NAME + "," +
                    MinioConstant.BPM_BUCKET_NAME + "," +
                    MinioConstant.INNOVATION_BUCKET_NAME + "," +
                    MinioConstant.TEAMAPPRAISING_BUCKET_NAME + "," +
                    MinioConstant.FIREFIGHTING_BUCKET_NAME;

}
