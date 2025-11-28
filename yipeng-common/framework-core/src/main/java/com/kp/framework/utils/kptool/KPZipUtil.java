package com.kp.framework.utils.kptool;


import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import com.kp.framework.entity.po.ZipFilePO;
import com.kp.framework.exception.KPServiceException;
import lombok.experimental.UtilityClass;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @Author lipeng
 * @Description 压缩相关操作
 * @Date 2022/5/18 12:52
 * @return
 **/
@UtilityClass
public final class KPZipUtil {

    private static Logger log = LoggerFactory.getLogger(KPZipUtil.class);



    /**
     * @Author lipeng
     * @Description 解压文件流
     * @Date 2022/5/18 16:37
     * @param inputStream
     * @param filePath
     * @return java.util.List<com.alibaba.fastjson.JSONObject>
     **/
    public static List<ZipFilePO> unInputStream(InputStream inputStream, String filePath) {
        List<ZipFilePO> list = new ArrayList<>();
        switch (new Tika().detect(filePath)){
            case "application/zip":
                list = unZipInputStream(inputStream);
                break;
            case "application/x-rar-compressed":
                list = unRarInputStream(inputStream);
                break;
            default:
                throw new KPServiceException("只支持.zip和.rar的文件");
        }
        return list;
    }


    /**
     * @Author lipeng
     * @Description 把多个文件流压缩并返回流文件
     * @Date 2022/6/30 17:08
     * @param zipFilePOList 要压缩的文件流
     * @param fileName 压缩后的压缩包名称
     * @return void
     **/
    public static void decompressionByZip(List<ZipFilePO> zipFilePOList, String fileName) {
        long beforeTime = System.currentTimeMillis();
        HttpServletResponse response =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        fileName = fileName.trim();
        if (KPStringUtil.isEmpty(fileName))
            throw new KPServiceException("请输入压缩后的文件名称");

        if (!fileName.endsWith(".zip"))
            fileName = fileName + ".zip";
        ZipArchiveOutputStream zipArchiveOutputStream = null;
        try {
            zipArchiveOutputStream = new ZipArchiveOutputStream(response.getOutputStream());
            zipArchiveOutputStream.setUseZip64(Zip64Mode.AsNeeded);

            try {
                fileName = URLEncoder.encode(fileName, "utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new KPServiceException("encode 加密失败！");
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType(new Tika().detect(fileName));
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);

            KPIOUtil.setDownloadResponseHeader(fileName, response);

            for (ZipFilePO zipFilePO : zipFilePOList) {
                ZipArchiveEntry entry = new ZipArchiveEntry(zipFilePO.getFileName());
                zipArchiveOutputStream.putArchiveEntry(entry);
                InputStream in = zipFilePO.getFileBody();
                IOUtils.copy(in, zipArchiveOutputStream);
                // 结束
                try {
                    zipArchiveOutputStream.closeArchiveEntry();
                }catch (Exception ex){}
                try {
                    in.close();
                }catch (Exception ex){}
            }
        }catch (Exception ex){
            throw new KPServiceException("压缩文件失败！");
        }finally {
            try {
                zipArchiveOutputStream.close();
            }catch (Exception ex){}
        }
        System.gc();

        long time = System.currentTimeMillis() - beforeTime;
        log.info("压缩耗时：" + time  +" 毫秒");
    }


    /**
     * @Author lipeng
     * @Description 把本地地址转为InputStream流
     * @Date 2022/5/18 11:26
     * @param localPath 本地地址
     * @return java.io.InputStream
     **/
    public static final InputStream pathByInputStream(String localPath){
        File file = new File(localPath);
        try {
            InputStream inputStream = new FileInputStream(file);
            return inputStream;
        } catch (FileNotFoundException e) {
            throw new KPServiceException("文件转流失败");
        }
    }


    /**
     * @Author lipeng
     * @Description 解压到指定目录  目前没有实现压缩包里压缩文件的解压（多重解压）
     * @Date 2022/5/18 11:47
     * @param inputStream  输入流  pathByInputStream("F://add.zip")
     * @param path 存放文件目录  例如 "F://addqewqe//"
     **/
    public static final void unZipFile(InputStream inputStream, String path) {
        try {
            ZipInputStream zin; // 创建ZipInputStream对象
            zin = new ZipInputStream(inputStream, Charset.forName("GBK")); // 实例化对象，指明要解压的文件
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                //目录
                if (entry.getSize()==0){
                    KPFileUtil.createFolder(path + entry.getName());
                    continue;
                }
                log.info(path + entry.getName());
                KPFileUtil.createFolder(new File(path + entry.getName()).getParent());
                OutputStream out = null;
                try {
                    out = new BufferedOutputStream(new FileOutputStream(new File(path + entry.getName())));
                    int read_size = 1024, len;
                    byte[] buffer = new byte[read_size];
                    while ((len = zin.read(buffer, 0 ,read_size)) != -1) {
                        out.write(buffer);
                    }
                } finally {
                    if (null != out) {
                        out.close();
                    }
                }

//                BufferedInputStream bs = new BufferedInputStream(zin);
                // 将文件信息写到byte数组中
//                byte[] bytes = new byte[(int) entry.getSize()];
//                bs.read(bytes, 0, (int) entry.getSize());
//                if ("zip".equals(entry.getName().substring(entry.getName().lastIndexOf(".") + 1))) {
//                    ZipInputStream secondZip = new ZipInputStream(new ByteArrayInputStream(bytes), Charset.forName("GBK"));
//                    // 循环解压
            }
            zin.close();
        } catch (IOException e) {
            throw new KPServiceException("解压文件失败" + e.getMessage());
        } catch (Exception e) {
            throw new KPServiceException("解压文件失败" + e.getMessage());
        }
    }



    /**
     * @Author lipeng
     * @Description 解压到内存中
     * @Date 2022/5/18 13:39
     * @param inputStream
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
//    public static final List<ZipFilePO> unZipInputStream(InputStream inputStream) {
//        try {
//            ZipInputStream zin; // 创建ZipInputStream对象
//            zin = new ZipInputStream(inputStream, Charset.forName("GBK")); // 实例化对象，指明要解压的文件
//            ZipEntry entry;
//            List<ZipFilePO> zipFilePOList = new ArrayList<>();
//            while ((entry = zin.getNextEntry()) != null) {
//                if (entry.getSize()==0) continue;
//                log.info(entry.getName());
//                BufferedInputStream bs = new BufferedInputStream(zin);
//                byte[] bytes = new byte[(int) entry.getSize()];
//                bs.read(bytes, 0, (int) entry.getSize());
//
//                ZipFilePO zipFilePO = new ZipFilePO(new File(entry.getName()).getName(), entry.getSize(),  new Tika().detect(entry.getName()), new ByteArrayInputStream(bytes));
//                zipFilePOList.add(zipFilePO);
//            }
//            zin.close();
//            return zipFilePOList;
//        } catch (IOException e) {
//            throw new KPServiceException("解压文件失败" + e.getMessage());
//        } catch (Exception e) {
//            throw new KPServiceException("解压文件失败" + e.getMessage());
//        }
//    }

    public static final List<ZipFilePO> unZipInputStream(InputStream inputStream) {
        try (ZipInputStream zin = new ZipInputStream(inputStream, StandardCharsets.UTF_8)) { // 使用try-with-resources自动关闭zin
            List<ZipFilePO> zipFilePOList = new ArrayList<>();
            ZipEntry entry;
            while ((entry = zin.getNextEntry()) != null) {
                if (entry.getSize() == 0) continue; // 检查是否为目录或空文件并跳过

                String fileName = entry.getName();
                log.info(fileName);
                byte[] bytes = IOUtils.toByteArray(zin); // 使用IOUtils读取字节，简化代码

                String mimeType = new Tika().detect(fileName); // 获取文件类型
                ZipFilePO zipFilePO = new ZipFilePO(fileName, entry.getSize(), mimeType, new ByteArrayInputStream(bytes));
                zipFilePOList.add(zipFilePO);
            }
            return zipFilePOList;
        } catch (IOException e) {
            throw new KPServiceException("解压文件失败: " + e.getMessage()); // 包含异常栈信息
        } catch (Exception e) {
            throw new KPServiceException("解压文件失败" + e.getMessage());
        }
    }


    /**
     * @Author lipeng
     * @Description 解压到指定目录
     * @Date 2022/5/18 16:14
     * @param inputStream  输入流  pathByInputStream("F://add.zip")
     * @param path 存放文件目录  例如 "F://addqewqe//"
     * @return void
     **/
    public static final void unRarFile(InputStream inputStream, String path) {
        try {
            Archive archive = new Archive(inputStream);
            FileHeader fileHeader;
            while ((fileHeader = archive.nextFileHeader()) != null) {
                String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader.getFileNameString() :
                        fileHeader.getFileNameW();
                //目录
                if (fileHeader.getDataSize()==0){
                    KPFileUtil.createFolder(path + fileName);
                    continue;
                }

                log.info(fileName);
                KPFileUtil.createFolder(new File(path + fileName).getParent());
                ByteArrayOutputStream os = null;
                OutputStream out = null;

                try {
                    os = new ByteArrayOutputStream();
                    out = new BufferedOutputStream(new FileOutputStream(new File(path +fileName)));

                    archive.extractFile(fileHeader, os);
                    byte[] bytes = os.toByteArray();
                    out.write(bytes);
                } finally {
                    if (null != out) {
                        out.close();
                    }
                    if (null != os) {
                        os.close();
                    }
                }

                // 解决文件名中文乱码问题
//                String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader.getFileNameString() :
//                        fileHeader.getFileNameW();
//                String fileExt =
//                        fileName.substring(fileName.lastIndexOf(FileConstant.FILE_SEPARATOR) + 1);
//                System.out.println(fileName);
//                ByteArrayOutputStream os = new ByteArrayOutputStream();
//                archive.extractFile(fileHeader, os);
//                // 将文件信息写到byte数组中
//                byte[] bytes = os.toByteArray();
//                System.out.println(bytes.length);
//                if ("rar".equals(fileExt)) {
//                    Archive secondArchive = new Archive(new ByteArrayInputStream(bytes));
//                    // 循环解压
//                }
            }
        } catch (IOException e) {
            throw new KPServiceException("解压文件失败" + e.getMessage());
        } catch (RarException e) {
            throw new KPServiceException("解压文件失败, rar5算法不支持解压，请压缩时选择rar4格式的算法" + e.getMessage());
        }
    }

    /**
     * @Author lipeng
     * @Description 解压到内存中
     * @Date 2022/5/18 13:39
     * @param inputStream
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     **/
    public static final List<ZipFilePO> unRarInputStream(InputStream inputStream){
        try {
            Archive archive = new Archive(inputStream);
            FileHeader fileHeader;
            List<ZipFilePO> zipFilePOList = new ArrayList<>();
            while ((fileHeader = archive.nextFileHeader()) != null) {
                String fileName = fileHeader.getFileNameW().isEmpty() ? fileHeader.getFileNameString() :
                        fileHeader.getFileNameW();
                //目录
                if (fileHeader.getDataSize()==0) continue;

                log.info(fileName);

                ByteArrayOutputStream os = null;
                try {
                    os = new ByteArrayOutputStream();
                    archive.extractFile(fileHeader, os);
                    byte[] bytes = os.toByteArray();
                    ZipFilePO zipFilePO = new ZipFilePO(new File(fileName).getName(), fileHeader.getDataSize(),  new Tika().detect(fileName), new ByteArrayInputStream(bytes));
                    zipFilePOList.add(zipFilePO);

                } finally {
                    if (null != os) {
                        os.close();
                    }
                }
            }

            return zipFilePOList;
        } catch (IOException e) {
            throw new KPServiceException("解压文件失败" + e.getMessage());
        } catch (RarException e) {
            throw new KPServiceException("解压文件失败, rar5算法不支持解压，请压缩时选择rar4格式的算法" + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
//        unRarFile(pathByInputStream("D://hahaha.rar"), "D://jy//rar//");


//        System.out.println(unZipInputStream(pathByInputStream("F://add.zip")));
//        System.out.println(unRarInputStream(pathByInputStream("F://add.rar")));
    }
}
