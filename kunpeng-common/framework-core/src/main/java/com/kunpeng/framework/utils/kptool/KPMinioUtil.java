package com.kunpeng.framework.utils.kptool;

import com.kunpeng.framework.constant.MinioConstant;
import com.kunpeng.framework.exception.KPServiceException;
import com.kunpeng.framework.exception.KPUtilException;
import io.minio.BucketExistsArgs;
import io.minio.CopyObjectArgs;
import io.minio.CopySource;
import io.minio.DownloadObjectArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.ListObjectsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.RemoveObjectsArgs;
import io.minio.Result;
import io.minio.SetBucketPolicyArgs;
import io.minio.StatObjectArgs;
import io.minio.StatObjectResponse;
import io.minio.UploadObjectArgs;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author lipeng
 * @Description minio操作工具类
 * @Date 2021/12/29 10:59
 * @return
 **/
public final class KPMinioUtil {


    private static MinioClient minioClient;


    private static final int DEFAULT_EXPIRY_TIME = 7 * 24 * 3600;

    private KPMinioUtil() {
    }

    static {
        minioClient = KPServiceUtil.getBean("minioClient", MinioClient.class);
    }


    /**
     * @Author lipeng
     * @Description 检查存储桶是否存在
     * @Date 2021/12/16 16:52
     * @param bucketName 存储桶名称
     * @return boolean
     **/
    public static boolean bucketExists(String bucketName) {
        boolean flag = false;
        try {
            flag = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new KPUtilException("查看Minio桶是否存在出错！" + e.getMessage());
        }
        return flag;
    }

    /**
     * @Author lipeng
     * @Description 存储桶名称
     * @Date 2021/12/16 16:53
     * @param bucketName 桶名称
     * @return void
     **/
    public static void createBucket(String bucketName) {
        boolean flag = bucketExists(bucketName);
        if (flag) return;
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            if (bucketName.equals(MinioConstant.TEMPORARY_BUCKET_NAME))
                KPMinioUtil.setBucketAccessPolicy(bucketName, true);
        } catch (Exception e) {
            throw new KPUtilException("Minio创建桶异常" + e.getMessage());
        }
    }

    /**
     * @Author lipeng
     * @Description 设置桶权限 在最新的minio管理页面中 已经没有设置的地方 只能通过代码设置
     * @Date 2025/7/9
     * @param bucketName 桶名称
     * @param isPublic 是否公有桶 true 为公有桶 false 为私有桶
     * @return void
     **/
    public static void setBucketAccessPolicy(String bucketName, Boolean isPublic) {
        try {
            String policy = new KPJSONFactoryUtil()
                    .put("Version", "2012-10-17")                          // 设置策略版本（固定为2012-10-17，支持最新S3功能）
                    .put("Statement", Arrays.asList(                        // 添加策略语句列表（可包含多个Statement，此处仅一个）
                            new KPJSONFactoryUtil()
                                    .put(isPublic, "Effect", "Allow")               // 根据isPublic参数动态设置权限效果（true=允许，false=忽略此行）
                                    .put(!isPublic, "Effect", "Deny")               // 根据isPublic参数动态设置权限效果（false=拒绝，true=忽略此行）
                                    .put("Principal", new KPJSONFactoryUtil()       // 设置策略主体为所有用户（*表示任何人）
                                            .put("AWS", Arrays.asList("*"))             // 特殊值"*"表示所有AWS用户
                                            .build()
                                    )
                                    .put("Action", Arrays.asList("s3:*"))           // 允许所有S3操作（s3:ListBucket、s3:GetObject等）
                                    .put("Resource", Arrays.asList(                 // 设置策略生效的资源范围
                                            "arn:aws:s3:::" + bucketName,               // 针对桶本身的操作
                                            "arn:aws:s3:::" + bucketName + "/*"         // 针对桶内所有对象的操作
                                    ))
                                    .put(!isPublic, "Condition", new KPJSONFactoryUtil()  // 仅在私有桶模式下添加条件
                                            .put("Bool", new KPJSONFactoryUtil()        // 布尔条件判断
                                                    .put("aws:SecureTransport", "false")    // 拒绝非HTTPS请求（仅允许通过HTTPS访问）
                                                    .build()
                                            )
                                            .build()
                                    )
                                    .build()
                    ))
                    .buildString();                                          // 构建完整的JSON策略字符串

            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config(policy)
                            .build()
            );
        } catch (Exception e) {
            throw new KPUtilException("设置桶权限失败: " + e.getMessage());
        }
    }


    /**
     * @Author lipeng
     * @Description 列出所有存储桶名称
     * @Date 2021/12/16 17:09
     * @param
     * @return java.util.List<java.lang.String>
     **/
    public static List<String> listBucketNames() {
        List<Bucket> bucketList = listBuckets();
        List<String> bucketListName = new ArrayList<>();
        for (Bucket bucket : bucketList) {
            bucketListName.add(bucket.name());
        }
        return bucketListName;
    }

    /**
     * @Author lipeng
     * @Description 列出所有存储桶
     * @Date 2021/12/16 17:09
     * @param
     * @return java.util.List<io.minio.messages.Bucket>
     **/
    public static List<Bucket> listBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new KPUtilException("重新Minio桶列表异常！" + e.getMessage());
        }
    }

    /**
     * @Author lipeng
     * @Description 删除存储桶
     * @Date 2021/12/16 17:10
     * @param bucketName 存储桶名称
     * @return boolean
     **/
    public static boolean removeBucket(String bucketName) {
        boolean flag = bucketExists(bucketName);
        if (!flag) return true;

        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new KPUtilException("删除Minio桶失败！" + e.getMessage());
        }

        return !bucketExists(bucketName);
    }

    /**
     * @Author lipeng
     * @Description 列出存储桶中的所有对象名称
     * @Date 2021/12/16 17:11
     * @param bucketName 存储桶名称
     * @param  isFile 递归查询存储桶下文件信息：
     * @return java.util.List<java.lang.String>
     **/
    public static List<String> listObjectNames(String bucketName, Boolean isFile) {
        List<String> listObjectNames = new ArrayList<>();
        boolean flag = bucketExists(bucketName);
        if (!flag) return listObjectNames;

        Iterable<Result<Item>> myObjects = listObjects(bucketName, isFile);
        for (Result<Item> result : myObjects) {
            Item item = null;
            try {
                item = result.get();
            } catch (Exception e) {
                continue;
            }
            listObjectNames.add(item.objectName());
        }
        return listObjectNames;
    }

    /**
     * @Author lipeng
     * @Description 列出存储桶中的所有对象
     * @Date 2021/12/16 17:14
     * @param bucketName 存储桶名称
     * @param  isFile 递归查询存储桶下文件信息：
     * @return java.lang.Iterable<io.minio.Result < io.minio.messages.Item>>
     **/
    public static Iterable<Result<Item>> listObjects(String bucketName, Boolean isFile) {
        boolean flag = KPMinioUtil.bucketExists(bucketName);
        if (!flag) return null;
        return minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(bucketName)
                        .recursive(isFile)
                        .build());
    }


    /**
     * @Author lipeng
     * @Description 复制文件
     * @Date 2022/1/12 22:21
     * @param bucketName 目标桶名称
     * @param objectName 目标文件存储路径
     * @param newBucketName 移动桶名称
     * @param newObjectName 移动存储路径
     * @return boolean
     **/
    public static ObjectWriteResponse copyObject(String bucketName, String objectName, String newBucketName, String newObjectName) {
        KPMinioUtil.createBucket(newBucketName);
        try {
            return minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .source(CopySource.builder().bucket(bucketName).object(objectName).build())
                            .bucket(newBucketName)
                            .object(newObjectName)
                            .build()
            );
        } catch (Exception e) {
            throw new KPUtilException("复制文件失败" + e.getMessage());
        }
    }


    /**
     * @Author lipeng
     * @Description 通过文件上传到对象
     * @Date 2021/12/16 17:14
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称 aa.txt
     * @param fileName  文件全路径 d://aa.txt
     * @return boolean
     **/
    public static boolean putObject(String bucketName, String objectName, String fileName) {
        if (!bucketExists(bucketName)) createBucket(bucketName);

        try {
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .filename(fileName)
                            .build());

            StatObjectResponse statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.size() > 0) return true;
        } catch (Exception e) {
            throw new KPUtilException("上传文件失败！" + e.getMessage());
        }
        return false;
    }


    /**
     * @Author lipeng
     * @Description 通过InputStream上传对象
     * @Date 2021/12/16 17:17
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param stream 要上传的流
     * @return boolean
     **/
    public static boolean putObject(String bucketName, String objectName, InputStream stream) {
        if (!bucketExists(bucketName)) createBucket(bucketName);

        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                            stream, stream.available(), -1)
                    .build());
            StatObjectResponse statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.size() > 0) return true;
            return false;
        } catch (Exception e) {
            throw new KPUtilException("上传文件失败！" + e.getMessage());
        } finally {
            try {
                stream.close();
            } catch (IOException e) {
            }
        }
    }


    /**
     * @Author lipeng
     * @Description 以流的形式获取一个文件对象（断点下载）
     * @Date 2021/12/16 17:18
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param offset     起始字节的位置
     * @param length     要读取的长度 (可选，如果无值则代表读到文件结尾)
     * @return java.io.InputStream
     **/
    public static InputStream getObject(String bucketName, String objectName, long offset, Long length) {
        if (!bucketExists(bucketName)) return null;

        StatObjectResponse statObject = statObject(bucketName, objectName);
        if (statObject != null && statObject.size() > 0) {
            try {
                return minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .offset(offset)
                                .length(length)
                                .build());
            } catch (Exception e) {
                throw new KPUtilException("下载异常" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * @Author lipeng
     * @Description 以流的形式获取一个文件对象
     * @Date 2021/12/16 17:18
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return java.io.InputStream
     **/
    public static InputStream getObject(String bucketName, String objectName) {
        if (!bucketExists(bucketName)) return null;
        StatObjectResponse statObject = statObject(bucketName, objectName);
        if (statObject != null && statObject.size() > 0) {
            try {
                return minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .build());
            } catch (Exception e) {
                throw new KPUtilException("下载异常" + e.getMessage());
            }
        }
        return null;
    }


    /**
     * @Author lipeng
     * @Description 下载并将文件保存到本地
     * @Date 2021/12/16 17:19
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param fileName   File name 全路径
     * @return boolean
     **/
    public static boolean getObject(String bucketName, String objectName, String fileName) {
        if (!bucketExists(bucketName)) return false;
        StatObjectResponse statObject = statObject(bucketName, objectName);
        if (statObject != null && statObject.size() > 0) {
            try {
                minioClient.downloadObject(
                        DownloadObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .filename(fileName)
                                .build());
                return true;
            } catch (Exception e) {
                throw new KPUtilException("下载异常" + e.getMessage());
            }
        }
        return false;
    }


    /**
     * @Author lipeng
     * @Description 删除一个对象
     * @Date 2021/12/16 17:19
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return boolean
     **/
    public static boolean removeObject(String bucketName, String objectName) {
        if (!bucketExists(bucketName)) return false;

        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new KPUtilException("删除文件异常：" + e.getMessage());
        }
        return true;
    }


    /**
     * @Author lipeng
     * @Description 除指定桶的多个文件对象, 返回删除错误的对象列表，全部删除成功，返回空列表
     * @Date 2021/12/16 17:19
     * @param bucketName  存储桶名称
     * @param objectNames 含有要删除的多个object名称的迭代器对象
     * @return java.util.List<java.lang.String>
     **/
    public static List<String> removeObject(String bucketName, List<String> objectNames) {
        List<DeleteObject> objects = new LinkedList<>();
        objectNames.forEach(fileName -> {
            objects.add(new DeleteObject(fileName));
        });

        List<String> deleteErrorNames = new ArrayList<>();
        boolean flag = bucketExists(bucketName);
        if (flag) {
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(
                    RemoveObjectsArgs.builder().bucket(bucketName).objects(objects).build());

            for (Result<DeleteError> result : results) {
                try {
                    deleteErrorNames.add(result.get().objectName());
                } catch (Exception e) {
                }
            }
        }
        return deleteErrorNames;
    }


    /**
     * @Author lipeng
     * @Description 生成一个给HTTP GET请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     * @Date 2021/12/16 17:20
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param hours    失效时间（以小时为单位），不得大于七天
     * @return java.lang.String
     **/
    public static String getUrl(String bucketName, String objectName, Integer hours) {
        if (KPStringUtil.isEmpty(objectName)) return null;
        if (!bucketExists(bucketName)) return null;

        try {
            if (hours < 1) throw new KPUtilException("失效时间不能小于1小时");
            if (hours > 168) throw new KPUtilException("失效时间不能超过7天");
            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().
                    bucket(bucketName).object(objectName).method(Method.GET).expiry(hours, TimeUnit.HOURS).build());
            return url;

        } catch (Exception ex) {
            throw new KPUtilException("生成http地址异常！" + ex.getMessage());
        }
    }

    /**
     * @Author lipeng
     * @Description 获取url地址
     * @Date 2025/7/8 17:25
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param time 失效时间
     * @param timeUnit 时间单位
     * @return java.lang.String
     **/
    public static String getUrl(String bucketName, String objectName, Integer time, TimeUnit timeUnit) {
        if (KPStringUtil.isEmpty(objectName)) return null;
        if (!bucketExists(bucketName)) return null;

        try {
            String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().
                    bucket(bucketName).object(objectName).method(Method.GET).expiry(time, timeUnit).build());
            return url;

        } catch (Exception ex) {
            throw new KPUtilException("生成http地址异常！" + ex.getMessage());
        }
    }


    /**
     * @Author lipeng
     * @Description 获取对象的元数据
     * @Date 2021/12/16 17:21
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return io.minio.ObjectStat
     **/
    public static StatObjectResponse statObject(String bucketName, String objectName) {
        if (!bucketExists(bucketName)) return null;
        try {
            return minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            throw new KPUtilException("获取对象的元数据异常：" + e.getMessage());
        }
    }



//
//
//
//    /**
//     * @Author lipeng
//     * @Description 已流的形式下载
//     * @Date 2021/12/29 14:21
//     * @param bucketName 桶名称
//     * @param fileName 要下载文件名
//     * @return void
//     **/
////    public static void downLoad(String bucketName, String fileName) throws Exception{
////        InputStream inputStream = KPMinioUtil.getObject(bucketName, fileName);
////        HttpServletResponse response =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
////        String suffix = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
////        fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());
////
////
////       String contentType = "";
////        try {
////            Path path = Paths.get(fileName);
////            contentType =  Files.probeContentType(path);
////        } catch (IOException e) {
////            throw new KPServiceException("获取文件后缀失败！");
////        }
////
////        response.setCharacterEncoding("UTF-8");
////        response.setContentType(contentType);
////        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
////        response.setHeader("filename", fileName);
////        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
////        //创建存放文件内容的数组
////        byte[] buff =new byte[1024];
////        //所读取的内容使用n来接收
////        int n;
////        //当没有读取完时,继续读取,循环
////        while((n=inputStream.read(buff))!=-1){
////            //将字节数组的数据全部写入到输出流中
////            outputStream.write(buff,0,n);
////        }
////
////        //强制将缓存区的数据进行输出
////        outputStream.flush();
////        //关流
////        outputStream.close();
////        inputStream.close();
////    }
//
//
//
//    /**
//     * @Author lipeng
//     * @Description 上传流文件到minio
//     * @Date 2022/5/18 14:01
//     * @param in 文件流
//     * @param bucketName 桶名字
//     * @param folder 文件保存的目录名
//     * @param fileName 文件名
//     * @param fileSize 文件大小
//     * @param fileType 文件类型
//     * @return com.daoben.framework.entity.bo.KPResult
//     **/
//    public static FileUploadBO updateByInputStream(InputStream in, String bucketName, String folder, String fileName, Long fileSize, String fileType) {
//        String newName = UUID.randomUUID().toString().replaceAll("-", "").concat(fileName.substring(fileName.lastIndexOf(".")));
//
//        if(folder.startsWith("/"))
//            folder = folder.substring(1, folder.length());
//        if(folder.endsWith("/"))
//            folder = folder.substring(0, folder.length()-1);
//
//        String newPath = new StringBuilder(folder)
//                .append("/")
//                .append(KPDateUtil.dateFormat(new Date(), KPDateUtil.TIMESTAMP_YMD))
//                .append("/")
//                .append(newName)
//                .toString();
//
//        try {
//            KPMinioUtil.putObject(bucketName, newPath, in);
//            return new FileUploadBO(newName, fileSize, fileType, newPath);
//        }catch (Exception ex){
//            throw new KPServiceException(newName.concat(" 上传失败！ 失败原因: ".concat(ex.getMessage()) ));
//        }
//    }
//
//
//
//
//

    /**
     * @Author lipeng
     * @Description 把临时桶里面的文件转入业务数据正式桶中 如果不是临时桶移动 请使用copyObject
     * @Date 2022/6/10 14:50
     * @param folder 保存的文件夹
     * @param filePath 临时文件地址
     * @param newBucketName 新的桶名称
     * @return java.lang.String
     **/
    public static String copyTemporaryFile(String folder, String filePath, String newBucketName) {
        if (KPStringUtil.isEmpty(filePath)) return null;
        if (!filePath.contains(MinioConstant.TEMPORARY_BUCKET_NAME)) return filePath;

        if (folder.startsWith("/")) folder = folder.substring(1, folder.length());

        if (folder.endsWith("/")) folder = folder.substring(0, folder.length() - 1);
        String newName;
        try {
            newName = new StringBuilder(folder)
                    .append("/")
                    .append(KPDateUtil.dateFormat(new Date(), KPDateUtil.DATE_TIME_PATTERN) + "_" + KPUuidUtil.getSimpleUUID())
                    .append(filePath.substring(filePath.lastIndexOf(".")))
                    .toString();
        } catch (Exception ex) {
            throw new KPServiceException("文件地址错误！");
        }

        if (!filePath.startsWith(MinioConstant.TEMPORARY_BUCKET_NAME))
            return filePath;

        try {
            KPMinioUtil.copyObject(MinioConstant.TEMPORARY_BUCKET_NAME, filePath, newBucketName, newName);
        } catch (Exception e) {
            throw new KPServiceException("文件保存错误：" + filePath);
        }
        return newName;
    }
}