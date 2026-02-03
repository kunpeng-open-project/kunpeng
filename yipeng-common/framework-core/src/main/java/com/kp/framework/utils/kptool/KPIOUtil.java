package com.kp.framework.utils.kptool;

import com.kp.framework.exception.KPUtilException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * IO工具类。
 * @author lipeng
 * 2025/11/20
 */
@Slf4j
@UtilityClass
public class KPIOUtil {

    // 复用 Tika 实例（线程安全）
    private static final Tika TIKA = new Tika();
    // 缓冲区大小（8KB，与操作系统页大小匹配）
    private static final int BUFFER_SIZE = 8192;

    /**
     * 下载文件。
     * @author lipeng
     * 2025/11/20
     * @param inputStream 文件输入流
     * @param originalFileName 原始文件名
     */
    public static void downloadFile(InputStream inputStream, String originalFileName) throws IOException {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        downloadFile(inputStream, originalFileName, response);
    }

    /**
     * 下载文件（核心方法：设置响应头 + 流传输）。
     * @author lipeng
     * 2025/11/20
     * @param inputStream 文件输入流（调用方需确保流有效，建议使用 try-with-resources 传入）
     * @param originalFileName 原始文件名（可含路径，会自动截取文件名部分）
     * @param response HTTP 响应对象（由调用方传入，避免线程安全问题）
     * @throws IOException 流传输异常
     */
    public static void downloadFile(InputStream inputStream, String originalFileName, HttpServletResponse response) throws IOException {
        // 1. 校验参数
        if (inputStream == null) {
            throw new KPUtilException("文件输入流不能为空");
        }
        if (!StringUtils.hasText(originalFileName)) {
            throw new KPUtilException("文件名不能为空");
        }

        // 2. 截取文件名（兼容 Windows/Linux 路径）
        String fileName = extractFileName(originalFileName);

        // 3. 设置响应头
        setDownloadResponseHeader(fileName, inputStream.available(), response);

        // 4. 流传输（try-with-resources 自动关闭流）
        try (OutputStream outputStream = new BufferedOutputStream(response.getOutputStream())) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            log.info("文件下载成功：{}", fileName);
        } catch (IOException e) {
            log.error("文件下载失败：{}", fileName, e);
            throw new KPUtilException("文件下载失败" + e.getMessage());
        }
    }

    /**
     * 仅设置下载响应头（供调用方单独使用，如先设置头再手动处理流）
     * @author lipeng
     * 2025/11/20
     * @param fileName 文件名（不含路径）
     * @param fileSize 文件大小（字节，用于设置 Content-Length）
     * @param response HTTP 响应对象
     */
    public static void setDownloadResponseHeader(String fileName, long fileSize, HttpServletResponse response) {
        try {
            // 1. 编码文件名（兼容中文、空格等特殊字符）
            String encodedFileName = URLEncoder.encode(fileName.replaceAll(" ", ""), "UTF-8")
                    .replace("+", "%20"); // 替换 + 为 %20，避免空格被编码为 +

            // 2. 设置响应头
            response.setCharacterEncoding("UTF-8");
            response.setContentType(TIKA.detect(fileName)); // 自动识别 MIME 类型
            response.setHeader("Content-Disposition", "attachment; filename=" + encodedFileName);
            if (fileSize != 0) response.setHeader("Content-Length", String.valueOf(fileSize)); // 显示文件大小
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setDateHeader("Expires", 0);
        } catch (Exception e) {
            log.error("设置下载响应头失败：{}", fileName, e);
            throw new KPUtilException("设置下载响应头失败" + e.getMessage());
        }
    }


    public static void setDownloadResponseHeader(String fileName, HttpServletResponse response) {
        setDownloadResponseHeader(fileName, 0, response);
    }

    /**
     * 截取文件名（兼容 Windows/Linux 路径）
     * @author lipeng
     * 2025/11/20
     * @param originalFileName 原始文件名（可含路径）
     * @return 纯文件名（不含路径）
     */
    private static String extractFileName(String originalFileName) {
        Path path = Paths.get(originalFileName);
        return path.getFileName().toString();
    }

    /**
     * 下载本地文件（重载方法，简化本地文件下载）
     * @author lipeng
     * 2025/11/20
     * @param localFilePath 本地文件路径
     * @param response HTTP 响应对象
     * @throws IOException 文件读取或传输异常
     */
    public static void downloadLocalFile(String localFilePath, HttpServletResponse response) throws IOException {
        Path filePath = Paths.get(localFilePath);
        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            log.error("本地文件不存在或不是常规文件：{}", localFilePath);
            throw new KPUtilException("文件不存在");
        }

        try (InputStream inputStream = Files.newInputStream(filePath)) {
            downloadFile(inputStream, filePath.getFileName().toString(), response);
        }
    }
}