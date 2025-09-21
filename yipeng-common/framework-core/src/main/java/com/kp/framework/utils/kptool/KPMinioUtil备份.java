package com.kp.framework.utils.kptool;//package com.kp.framework.utils.kptool;
//
//import com.kp.framework.constant.MinioConstant;
//import com.kp.framework.entity.bo.FileUploadBO;
//import com.kp.framework.exception.KPServiceException;
//import io.minio.BucketExistsArgs;
//import io.minio.CopyObjectArgs;
//import io.minio.CopySource;
//import io.minio.DownloadObjectArgs;
//import io.minio.GetObjectArgs;
//import io.minio.GetPresignedObjectUrlArgs;
//import io.minio.ListObjectsArgs;
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import io.minio.ObjectWriteResponse;
//import io.minio.PutObjectArgs;
//import io.minio.RemoveBucketArgs;
//import io.minio.RemoveObjectArgs;
//import io.minio.RemoveObjectsArgs;
//import io.minio.Result;
//import io.minio.StatObjectArgs;
//import io.minio.UploadObjectArgs;
//import io.minio.errors.InvalidExpiresRangeException;
//import io.minio.http.Method;
//import io.minio.messages.Bucket;
//import io.minio.messages.DeleteError;
//import io.minio.messages.DeleteObject;
//import io.minio.messages.Item;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
///**
// * @Author lipeng
// * @Description minio操作工具类
// * @Date 2021/12/29 10:59
// * @return
// **/
//public final class KPMinioUtil备份 {
//
//
//    private static MinioClient minioClient;
//
//
//    private static final int DEFAULT_EXPIRY_TIME = 7 * 24 * 3600;
//
//    private KPMinioUtil备份(){}
//
//    static {
//        minioClient = KPServiceUtil.getBean("minioClient", MinioClient.class);
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 检查存储桶是否存在
//     * @Date 2021/12/16 16:52
//     * @param bucketName 存储桶名称
//     * @return boolean
//     **/
//    public static boolean bucketExists(String bucketName) throws Exception {
//        boolean flag =minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
//        if (flag) return true;
//        return false;
//    }
//
//    /**
//     * @Author lipeng
//     * @Description 存储桶名称
//     * @Date 2021/12/16 16:53
//     * @param bucketName 桶名称
//     * @return void
//     **/
//    public static void makeBucket(String bucketName) throws Exception {
//        boolean flag = bucketExists(bucketName);
//        if (flag) return;
//        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 列出所有存储桶名称
//     * @Date 2021/12/16 17:09
//     * @param
//     * @return java.util.List<java.lang.String>
//     **/
//    public static List<String> listBucketNames() throws Exception{
//        List<Bucket> bucketList = listBuckets();
//        List<String> bucketListName = new ArrayList<>();
//        for (Bucket bucket : bucketList) {
//            bucketListName.add(bucket.name());
//        }
//        return bucketListName;
//    }
//
//    /**
//     * @Author lipeng
//     * @Description 列出所有存储桶
//     * @Date 2021/12/16 17:09
//     * @param
//     * @return java.util.List<io.minio.messages.Bucket>
//     **/
//    public static List<Bucket> listBuckets() throws Exception {
//        return minioClient.listBuckets();
//    }
//
//    /**
//     * @Author lipeng
//     * @Description 删除存储桶
//     * @Date 2021/12/16 17:10
//     * @param bucketName 存储桶名称
//     * @return boolean
//     **/
//    public static boolean removeBucket(String bucketName) throws Exception {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
//            flag = bucketExists(bucketName);
//            if (!flag) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * @Author lipeng
//     * @Description 列出存储桶中的所有对象名称
//     * @Date 2021/12/16 17:11
//     * @param bucketName 存储桶名称
//     * @param  isFile 递归查询存储桶下文件信息：
//     * @return java.util.List<java.lang.String>
//     **/
//    public static List<String> listObjectNames(String bucketName, Boolean isFile) throws Exception {
//        List<String> listObjectNames = new ArrayList<>();
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            Iterable<Result<Item>> myObjects = listObjects(bucketName, isFile);
//            for (Result<Item> result : myObjects) {
//                Item item = result.get();
//                listObjectNames.add(item.objectName());
//            }
//        }
//        return listObjectNames;
//    }
//
//    /**
//     * @Author lipeng
//     * @Description 列出存储桶中的所有对象
//     * @Date 2021/12/16 17:14
//     * @param bucketName 存储桶名称
//     * @param  isFile 递归查询存储桶下文件信息：
//     * @return java.lang.Iterable<io.minio.Result<io.minio.messages.Item>>
//     **/
//    public static Iterable<Result<Item>> listObjects(String bucketName, Boolean isFile) throws Exception {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            return minioClient.listObjects(
//                    ListObjectsArgs.builder()
//                            .bucket(bucketName)
//                            .recursive(isFile)
//                            .build());
//        }
//        return null;
//    }
//
//
//
//
//
//    /**
//     * @Author lipeng
//     * @Description 复制文件
//     * @Date 2022/1/12 22:21
//     * @param bucketName
//     * @param objectName
//     * @param newBucketName
//     * @param newObjectName
//     * @return boolean
//     **/
//    public static ObjectWriteResponse copyObject(String bucketName, String objectName, String newBucketName, String newObjectName) throws Exception {
//        KPMinioUtil备份.makeBucket(newBucketName);
//        return minioClient.copyObject(
//                CopyObjectArgs.builder()
//                        .source(CopySource.builder().bucket(bucketName).object(objectName).build())
//                        .bucket(newBucketName)
//                        .object(newObjectName)
//                        .build()
//                );
//    }
//
//    /**
//     * @Author lipeng
//     * @Description  通过文件上传到对象
//     * @Date 2021/12/16 17:14
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称 aa.txt
//     * @param fileName  文件全路径 d://aa.txt
//     * @return boolean
//     **/
//    public static boolean putObject(String bucketName, String objectName, String fileName) throws Exception {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            minioClient.uploadObject(
//                    UploadObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(objectName)
//                            .filename(fileName)
//                            .build());
//            ObjectStat statObject = statObject(bucketName, objectName);
//            if (statObject != null && statObject.length() > 0) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description  通过InputStream上传对象
//     * @Date 2021/12/16 17:17
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @param stream 要上传的流
//     * @return boolean
//     **/
//    public static boolean putObject(String bucketName, String objectName, InputStream stream) throws Exception {
//        KPMinioUtil备份.makeBucket(bucketName);
//        boolean flag = bucketExists(bucketName);
//        try {
//            if (flag) {
//                minioClient.putObject(
//                        PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
//                                        stream, stream.available(), -1)
//                                .build());
//                ObjectStat statObject = statObject(bucketName, objectName);
//                if (statObject != null && statObject.length() > 0) {
//                    return true;
//                }
//            }
//            return false;
//        }catch (Exception ex){
//            throw new Exception(ex);
//        }finally {
//            stream.close();
//        }
//    }
//
//
//
//    /**
//     * @Author lipeng
//     * @Description  以流的形式获取一个文件对象（断点下载）
//     * @Date 2021/12/16 17:18
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @param offset     起始字节的位置
//     * @param length     要读取的长度 (可选，如果无值则代表读到文件结尾)
//     * @return java.io.InputStream
//     **/
//    public static InputStream getObject(String bucketName, String objectName, long offset, Long length) throws Exception {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            ObjectStat statObject = statObject(bucketName, objectName);
//            if (statObject != null && statObject.length() > 0) {
////                InputStream stream = minioClient.getObject(bucketName, objectName, offset, length);
//                InputStream stream = minioClient.getObject(
//                        GetObjectArgs.builder()
//                                .bucket(bucketName)
//                                .object(objectName)
//                                .offset(offset)
//                                .length(length)
//                                .build());
//                return stream;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * @Author lipeng
//     * @Description  以流的形式获取一个文件对象
//     * @Date 2021/12/16 17:18
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @return java.io.InputStream
//     **/
//    public static InputStream getObject(String bucketName, String objectName) throws Exception {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            ObjectStat statObject = statObject(bucketName, objectName);
//            if (statObject != null && statObject.length() > 0) {
//                InputStream stream = minioClient.getObject(
//                        GetObjectArgs.builder()
//                                .bucket(bucketName)
//                                .object(objectName)
//                                .build());
//                return stream;
//            }
//        }
//        return null;
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 下载并将文件保存到本地
//     * @Date 2021/12/16 17:19
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @param fileName   File name 全路径
//     * @return boolean
//     **/
//    public static boolean getObject(String bucketName, String objectName, String fileName) throws Exception {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            ObjectStat statObject = statObject(bucketName, objectName);
//            if (statObject != null && statObject.length() > 0) {
//                minioClient.downloadObject(
//                        DownloadObjectArgs.builder()
//                                .bucket(bucketName)
//                                .object(objectName)
//                                .filename(fileName)
//                                .build());
//                return true;
//            }
//        }
//        return false;
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 删除一个对象
//     * @Date 2021/12/16 17:19
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @return boolean
//     **/
//    public static boolean removeObject(String bucketName, String objectName) throws Exception {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            minioClient.removeObject(
//                    RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
//            return true;
//        }
//        return false;
//
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description  除指定桶的多个文件对象,返回删除错误的对象列表，全部删除成功，返回空列表
//     * @Date 2021/12/16 17:19
//     * @param bucketName  存储桶名称
//     * @param objectNames 含有要删除的多个object名称的迭代器对象
//     * @return java.util.List<java.lang.String>
//     **/
//    public static List<String> removeObject(String bucketName, List<String> objectNames) throws Exception {
//        List<DeleteObject> objects = new LinkedList<>();
//        objectNames.forEach(fileName->{
//            objects.add(new DeleteObject(fileName));
//        });
//
//
//        List<String> deleteErrorNames = new ArrayList<>();
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            Iterable<Result<DeleteError>> results = minioClient.removeObjects(
//                    RemoveObjectsArgs.builder().bucket(bucketName).objects(objects).build());
//
//            for (Result<DeleteError> result : results) {
//                DeleteError error = result.get();
//                deleteErrorNames.add(error.objectName());
//            }
//        }
//        return deleteErrorNames;
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description  生成一个给HTTP GET请求用的presigned URL。
//     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
//     * @Date 2021/12/16 17:20
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @param hours    失效时间（以秒为小时），不得大于七天
//     * @return java.lang.String
//     **/
//    public static String presignedGetObject(String bucketName, String objectName, Integer hours)  {
//        try {
//            boolean flag = bucketExists(bucketName);
//            String url = "";
//            if (flag) {
//                if (hours < 1) throw new KPServiceException("失效时间不能小于1小时");
//                if (hours > 168)  throw new KPServiceException("失效时间不能超过7天");
////                url = minioClient.presignedGetObject(bucketName, objectName, expires);
//                url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().
//                        bucket(bucketName).object(objectName).method(Method.GET).expiry(hours, TimeUnit.HOURS).build());
//                return url;
//            }
//        }catch (Exception ex){
//            throw new KPServiceException("生成http地址异常！" + ex.getMessage());
//        }
//        return null;
//    }
//
//    /**
//     * @Author lipeng
//     * @Description  生成一个给HTTP PUT请求用的presigned URL。 浏览器/移动端的客户端可以用这个URL进行上传，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
//     * @Date 2021/12/16 17:21
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
//     * @return java.lang.String
//     **/
//    public static String presignedPutObject(String bucketName, String objectName, Integer expires) throws Exception {
//        boolean flag = bucketExists(bucketName);
//        String url = "";
//        if (flag) {
//            if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
//                throw new InvalidExpiresRangeException(expires,
//                        "expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
//            }
//            url = minioClient.presignedPutObject(bucketName, objectName, expires);
//        }
//        return url;
//    }
//
//    /**
//     * @Author lipeng
//     * @Description 获取对象的元数据
//     * @Date 2021/12/16 17:21
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @return io.minio.ObjectStat
//     **/
//    public static ObjectStat statObject(String bucketName, String objectName) throws Exception {
//        boolean flag = bucketExists(bucketName);
//        if (flag) {
//            ObjectStat statObject = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
//            return statObject;
//        }
//        return null;
//    }
//
//
//
//    /**
//     * @Author lipeng
//     * @Description  文件访问路径
//     * @Date 2021/12/16 17:22
//     * @param bucketName 存储桶名称
//     * @param objectName 存储桶里的对象名称
//     * @return java.lang.String
//     **/
//    public static String getObjectUrl(String bucketName, String objectName) throws Exception {
//        boolean flag = bucketExists(bucketName);
//        String url = "";
//        if (flag) {
//            url = minioClient.getObjectUrl(bucketName, objectName);
//        }
//        return url;
//    }
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
//            KPMinioUtil备份.putObject(bucketName, newPath, in);
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
//    /**
//     * @Author lipeng
//     * @Description 新增或修改文件
//     * 删除文件时不删除 文件管理器里面的文件
//     * @Date 2022/6/10 14:50
//     * @param folder 保存的文件夹
//     * @param filePath 临时文件地址
//     * @param newBucketName 新的桶名称
//     * @return java.lang.String
//     **/
//    public static String copyFile(String folder, String filePath, String newBucketName) {
//        if (folder.startsWith("/"))
//            folder = folder.substring(1,folder.length());
//
//        if (folder.endsWith("/"))
//            folder = folder.substring(0,folder.length()-1);
//        String newName;
//        try {
//            newName = new StringBuilder(folder)
//                .append("/")
//                .append(KPDateUtil.dateFormat(new Date(), KPDateUtil.TIMESTAMP_YMD))
//                .append("/")
//                .append(UUID.randomUUID().toString().replaceAll("-", ""))
//                .append(filePath.substring(filePath.lastIndexOf(".")))
//                .toString();
//        }catch (Exception ex){
//            throw new KPServiceException("文件地址错误！");
//        }
//
//        if (!filePath.startsWith(MinioConstant.TEMPORARY_BUCKET_NAME))
//            return filePath;
//
//        try {
//            KPMinioUtil备份.copyObject(MinioConstant.TEMPORARY_BUCKET_NAME, filePath, newBucketName, newName);
//        } catch (Exception e) {
//            throw new KPServiceException("文件保存错误：" + filePath);
//        }
//        return newName;
//    }
//}