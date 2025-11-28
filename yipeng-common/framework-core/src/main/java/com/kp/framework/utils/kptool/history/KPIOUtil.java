//package com.kp.framework.utils.kptool.history;
//
//import com.kp.framework.exception.KPServiceException;
//import lombok.experimental.UtilityClass;
//import org.apache.tika.Tika;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@UtilityClass
//public class KPIOUtil {
//
//
//    /**
//     * @Author lipeng
//     * @Description 以流的形式下载文件
//     * @Date 2022/3/16
//     * @param inputStream 文件流
//     * @param fileName 文件名称
//     * @return void
//     **/
//    public static void downLoad(InputStream inputStream, String fileName) throws IOException {
//        HttpServletResponse response =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        fileName = fileName.substring(fileName.lastIndexOf("/")+1, fileName.length());
//
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType(new Tika().detect(fileName));
//        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
////        response.setHeader("filename", fileName);
//        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//        //创建存放文件内容的数组
//        byte[] buff =new byte[1024];
//        //所读取的内容使用n来接收
//        int n;
//        //当没有读取完时,继续读取,循环
//        while((n=inputStream.read(buff))!=-1){
//            //将字节数组的数据全部写入到输出流中
//            outputStream.write(buff,0,n);
//        }
//
//        //强制将缓存区的数据进行输出
//        outputStream.flush();
//        //关流
//        outputStream.close();
//        inputStream.close();
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 设置下载流的信息
//     * @Date 2022/6/30 17:04
//     * @param fileName
//     * @param response
//     * @return void
//     **/
//    public static void downLoad(String fileName, HttpServletResponse response)   {
////        fileName = fileName.substring(fileName.lastIndexOf("/")+1);
//
//        try {
//            fileName = URLEncoder.encode(fileName, "utf-8");
//        } catch (UnsupportedEncodingException e) {
//           throw new KPServiceException("encode 加密失败！");
//        }
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType(new Tika().detect(fileName));
//        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
//    }
//
//    /**
//     * 截取文件名（兼容 Windows/Linux 路径）
//     * @param originalFileName 原始文件名（可含路径）
//     * @return 纯文件名（不含路径）
//     */
//    private static String extractFileName(String originalFileName) {
//        Path path = Paths.get(originalFileName);
//        return path.getFileName().toString();
//    }
//
//}
