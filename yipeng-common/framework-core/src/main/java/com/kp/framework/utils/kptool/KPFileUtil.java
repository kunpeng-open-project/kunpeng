package com.kp.framework.utils.kptool;

import com.kp.framework.exception.KPUtilException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 文件处理工具类，提供文件创建、删除、转换、下载等常用操作。
 *
 * <p>注意事项：
 * <ul>
 *     <li>所有方法均为静态方法，无需实例化此类。</li>
 *     <li>方法会抛出明确的异常，调用者应根据需要捕获并处理。</li>
 *     <li>涉及文件操作的方法，确保调用者拥有相应的文件系统权限。</li>
 * </ul>
 */
@Slf4j
@UtilityClass
public final class KPFileUtil {

    /** 文件类型检测器 */
    private static final Tika TIKA = new Tika();

    /** 默认缓冲区大小 (8KB) */
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    /** 默认连接超时时间 (5秒) */
    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;

    /** 默认读取超时时间 (10秒) */
    private static final int DEFAULT_READ_TIMEOUT = 10000;


    // --- 文件夹操作 ---

    /**
     * 创建文件夹，如果父目录不存在则一并创建。
     *
     * @param folderPath 文件夹路径
     * @return 如果文件夹创建成功或已存在，返回 {@code true}
     * @throws IOException 如果路径无效或无法创建文件夹
     */
    public static boolean createFolder(String folderPath) {
        Objects.requireNonNull(folderPath, "文件夹路径不能为空");
        Path path = Paths.get(folderPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new KPUtilException("无法创建文件夹: " + e.getMessage());
            }
            log.info("文件夹创建成功: {}", folderPath);
        }
        return true;
    }

    // --- 文件判断与删除 ---

    /**
     * 判断指定路径是否为一个文件。
     *
     * @param filePath 文件路径
     * @return 如果是文件，返回 {@code true}
     */
    public static boolean isFile(String filePath) {
        Objects.requireNonNull(filePath, "文件路径不能为空");
        return Files.isRegularFile(Paths.get(filePath));
    }

    /**
     * 删除单个文件。
     *
     * @param filePath 文件路径
     * @return 如果文件删除成功，返回 {@code true}
     */
    public static boolean deleteFile(String filePath) {
        Objects.requireNonNull(filePath, "文件路径不能为空");
        File file = new File(filePath);
        if (file.isFile() && file.delete()) {
            log.info("文件删除成功: {}", filePath);
            return true;
        }
        log.warn("文件删除失败或文件不存在: {}", filePath);
        return false;
    }

    /**
     * 递归删除文件夹及其所有内容。
     *
     * @param folderPath 文件夹路径
     */
    public static void deleteFolder(String folderPath) {
        Objects.requireNonNull(folderPath, "文件夹路径不能为空");
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            deleteDirectoryContent(folder);
            if (folder.delete()) {
                log.info("文件夹删除成功: {}", folderPath);
            } else {
                log.warn("文件夹删除失败: {}", folderPath);
            }
        } else {
            log.warn("文件夹不存在或不是一个目录: {}", folderPath);
        }
    }

    /**
     * 递归删除目录内容（内部使用）
     */
    private static void deleteDirectoryContent(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDirectoryContent(file);
                if (file.delete()) {
                    log.info("删除子文件夹成功: {}", file.getPath());
                }
            } else if (file.delete()) {
                log.info("删除文件成功: {}", file.getPath());
            }
        }
    }

    // --- Base64 与文件/字节数组转换 ---

    /**
     * 将 Base64 字符串解码并保存为文件。
     *
     * @param folderPath 保存文件的文件夹路径
     * @param fileName   文件名
     * @param base64Str  Base64 编码的字符串
     * @return 保存文件的完整路径
     * @throws IOException 如果解码失败或文件写入失败
     */
    public static String saveFileFromBase64(String folderPath, String fileName, String base64Str) {
        Objects.requireNonNull(folderPath, "文件夹路径不能为空");
        Objects.requireNonNull(fileName, "文件名不能为空");
        Objects.requireNonNull(base64Str, "Base64字符串不能为空");

        createFolder(folderPath);
        Path filePath = Paths.get(folderPath, fileName);
        byte[] fileBytes = base64String2ByteFun(base64Str);
        try {
            Files.write(filePath, fileBytes);
        } catch (IOException e) {
            throw new KPUtilException("文件写入失败: " + e.getMessage());
        }
        log.info("文件从Base64保存成功: {}", filePath);
        return filePath.toString();
    }

    /**
     * Base64 字符串转字节数组（自动处理前缀）
     */
    public static byte[] base64String2ByteFun(String base64Str) {
        String[] parts = base64Str.split(",", 2);
        String base64Body = parts.length > 1 ? parts[1] : parts[0];
        try {
            return Base64.getDecoder().decode(base64Body);
        } catch (IllegalArgumentException e) {
            log.error("Base64字符串格式无效: {}", base64Str, e);
            throw new IllegalArgumentException("无效的Base64字符串", e);
        }
    }

    /**
     * 字节数组转 Base64 字符串
     */
    public static String byte2Base64StringFun(byte[] bytes) {
        Objects.requireNonNull(bytes, "字节数组不能为空");
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 从文件读取字节数组
     */
    public static byte[] getBytesByFile(String filePath) {
        Objects.requireNonNull(filePath, "文件路径不能为空");
        try {
            return Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            throw new KPUtilException("文件读取失败: " + e.getMessage());
        }
    }

    /**
     * 将字节数组写入文件
     */
    public static void writeBytesToFile(byte[] bytes, String folderPath, String fileName) {
        Objects.requireNonNull(bytes, "字节数组不能为空");
        Objects.requireNonNull(folderPath, "文件夹路径不能为空");
        Objects.requireNonNull(fileName, "文件名不能为空");

        createFolder(folderPath);
        Path filePath = Paths.get(folderPath, fileName);
        try {
            Files.write(filePath, bytes);
        } catch (IOException e) {
            throw new KPUtilException("文件写入失败: " + e.getMessage());
        }
        log.info("字节数组写入文件成功: {}", filePath);
    }

    // --- 网络文件操作 ---

    /**
     * 从 URL 读取字节流
     */
    public static byte[] readBytesFromUrl(String urlStr) {
        try {
            Objects.requireNonNull(urlStr, "URL不能为空");
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
            connection.setReadTimeout(DEFAULT_READ_TIMEOUT);

            try (InputStream in = connection.getInputStream();
                 ByteArrayOutputStream out = new ByteArrayOutputStream()) {

                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                return out.toByteArray();
            } finally {
                connection.disconnect();
            }
        } catch (Exception ex) {
            throw new KPUtilException("从URL读取字节流失败" + ex.getMessage());
        }
    }

    /**
     * 下载网络文件到本地
     */
    public static File downloadFile(String urlStr, String localFolderPath) {
        Objects.requireNonNull(urlStr, "URL不能为空");
        Objects.requireNonNull(localFolderPath, "本地文件夹路径不能为空");

        String fileName = extractFileNameFromUrl(urlStr);
        createFolder(localFolderPath);
        Path localFilePath = Paths.get(localFolderPath, fileName);

        byte[] fileBytes = readBytesFromUrl(urlStr);
        try {
            Files.write(localFilePath, fileBytes);
        } catch (IOException e) {
            throw new KPUtilException("文件写入失败" + e.getMessage());
        }
        log.info("文件下载成功: {} -> {}", urlStr, localFilePath);
        return localFilePath.toFile();
    }

    /**
     * 从 URL 中提取文件名
     */
    private static String extractFileNameFromUrl(String urlStr) {
        try {
            String fileName = new File(new URL(urlStr).getPath()).getName();
            if (fileName.isEmpty()) {
                fileName = "downloaded_file_" + System.currentTimeMillis();
            }
            return fileName;
        } catch (Exception ex) {
            throw new KPUtilException("异常" + ex.getMessage());
        }
    }

    // --- 图片处理 ---

    /**
     * 在二维码图片中间添加 Logo
     */
    public static byte[] encodeWithLogo(String qrCodeUrl, String logoBase64) {
        try {
            Objects.requireNonNull(qrCodeUrl, "二维码URL不能为空");
            Objects.requireNonNull(logoBase64, "Logo的Base64字符串不能为空");

            // 读取二维码图片
            BufferedImage qrImage = ImageIO.read(new URL(qrCodeUrl));
            int qrWidth = qrImage.getWidth();
            int qrHeight = qrImage.getHeight();

            // 读取Logo图片
            byte[] logoBytes = base64String2ByteFun(logoBase64);
            BufferedImage logoImage = ImageIO.read(new ByteArrayInputStream(logoBytes));

            // Logo尺寸为二维码的1/5
            int logoSize = qrWidth / 5;
            BufferedImage resizedLogo = resizeImage(logoImage, logoSize, logoSize);

            // 在二维码中心绘制Logo
            Graphics2D g2d = qrImage.createGraphics();
            int x = (qrWidth - logoSize) / 2;
            int y = (qrHeight - logoSize) / 2;

            // 绘制白色边框
            g2d.setColor(Color.WHITE);
            g2d.fillRoundRect(x - 2, y - 2, logoSize + 4, logoSize + 4, 10, 10);

            // 绘制Logo
            g2d.drawImage(resizedLogo, x, y, logoSize, logoSize, null);
            g2d.dispose();

            // 转换为字节数组
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", out);
            return out.toByteArray();
        } catch (Exception ex) {
            throw new KPUtilException("图片处理失败" + ex.getMessage());
        }
    }

    /**
     * 调整图片尺寸
     */
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

    // --- 文件命名 ---

    /**
     * 处理重复文件名
     */
    public static String getUniqueName(Map<String, Integer> existingNames, String originalName) {
        Objects.requireNonNull(existingNames, "existingNames Map不能为空");
        Objects.requireNonNull(originalName, "原始名称不能为空");

        String baseName = originalName;
        String extension = "";
        int dotIndex = originalName.lastIndexOf('.');

        if (dotIndex > 0) {
            baseName = originalName.substring(0, dotIndex);
            extension = originalName.substring(dotIndex);
        }

        Integer count = existingNames.get(baseName);
        if (count == null) {
            existingNames.put(baseName, 1);
            return originalName;
        } else {
            String newName = baseName + "(" + (count + 1) + ")" + extension;
            existingNames.put(baseName, count + 1);
            return newName;
        }
    }

    // --- 目录遍历 ---

    /**
     * 查找目录下的所有文件（递归）
     */
    public static void findAllFiles(File dir, List<String> filePaths) {
        Objects.requireNonNull(dir, "目录不能为空");
        Objects.requireNonNull(filePaths, "文件路径列表不能为空");

        if (!dir.exists() || !dir.isDirectory()) {
            log.warn("目录不存在或不是目录: {}", dir.getPath());
            return;
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                filePaths.add(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                findAllFiles(file, filePaths);
            }
        }
    }
}