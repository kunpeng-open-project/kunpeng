package com.kp.framework;

import com.kp.framework.utils.kptool.KPFileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author lipeng
 * @Description 把指定目录下所有文件夹下的文件移动到指定目录下
 * @Date 2025/11/26 16:33
 * @return
 **/
public class FileMoveDemo {
//24.9
    public static void main(String[] args) {
        // 1. 配置源目录和目标目录（根据实际需求修改）
        String sourceDir = "F://aa"; // 源目录：会递归处理所有子文件夹
        String targetDir = "F://目标目录1"; // 目标目录：所有文件最终汇总到这里

        try {
            // 2. 校验目录合法性
            validateDirs(sourceDir, targetDir);

            // 3. 递归获取源目录下所有文件的绝对路径
            List<String> allFilePaths = new ArrayList<>();
            KPFileUtil.findAllFiles(new File(sourceDir), allFilePaths);
            System.out.printf("成功扫描到 %d 个文件%n", allFilePaths.size());

            if (allFilePaths.isEmpty()) {
                System.out.println("源目录下无文件，无需处理");
                return;
            }

            // 4. 复制文件到目标目录（处理重名），并删除原文件
            Map<String, Integer> nameCounter = new HashMap<>(); // 用于处理重复文件名
            for (String sourceFilePath : allFilePaths) {
                File sourceFile = new File(sourceFilePath);
                String originalFileName = sourceFile.getName();

                // 处理重名：生成唯一文件名（如 "文件(1).txt"）
                String uniqueFileName = KPFileUtil.getUniqueName(nameCounter, originalFileName);
                String targetFilePath = targetDir + File.separator + uniqueFileName;

                // 4.1 读取原文件字节数组
                byte[] fileBytes = KPFileUtil.getBytesByFile(sourceFilePath);

                // 4.2 写入到目标目录
                KPFileUtil.writeBytesToFile(fileBytes, targetDir, uniqueFileName);
                System.out.printf("已移动文件：%s → %s%n", sourceFilePath, targetFilePath);

                // 4.3 删除原文件
                boolean deleteSuccess = KPFileUtil.deleteFile(sourceFilePath);
                if (!deleteSuccess) {
                    System.err.printf("警告：原文件删除失败 → %s%n", sourceFilePath);
                }
            }

            // 5. 删除源目录下所有空文件夹（递归删除）
//            deleteEmptyDirs(new File(sourceDir));
            System.out.println("=====================================");
//            System.out.println("文件移动完成！所有文件已汇总到目标目录，原文件夹已清理");

        } catch (Exception e) {
            System.err.println("文件移动失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 校验源目录和目标目录的合法性
     */
    private static void validateDirs(String sourceDir, String targetDir) {
        File sourceFile = new File(sourceDir);
        File targetFile = new File(targetDir);

        // 校验源目录是否存在且是目录
        if (!sourceFile.exists()) {
            throw new IllegalArgumentException("源目录不存在：" + sourceDir);
        }
        if (!sourceFile.isDirectory()) {
            throw new IllegalArgumentException("源路径不是目录：" + sourceDir);
        }

        // 校验目标目录（不存在则创建）
        if (!targetFile.exists()) {
            KPFileUtil.createFolder(targetDir);
            System.out.println("目标目录不存在，已自动创建：" + targetDir);
        } else if (!targetFile.isDirectory()) {
            throw new IllegalArgumentException("目标路径不是目录：" + targetDir);
        }

        // 禁止源目录和目标目录相同（避免逻辑错误）
        if (sourceFile.getAbsolutePath().equals(targetFile.getAbsolutePath())) {
            throw new IllegalArgumentException("源目录和目标目录不能相同");
        }
    }

    /**
     * 递归删除目录下所有空文件夹（包括自身，如果为空）
     */
    private static void deleteEmptyDirs(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        // 先递归删除子文件夹
        File[] subFiles = dir.listFiles();
        if (subFiles != null) {
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    deleteEmptyDirs(subFile);
                }
            }
        }

        // 检查当前目录是否为空，为空则删除
        if (dir.listFiles() == null || dir.listFiles().length == 0) {
            boolean deleteSuccess = dir.delete();
            if (deleteSuccess) {
                System.out.printf("已删除空文件夹：%s%n", dir.getAbsolutePath());
            } else {
                System.err.printf("警告：空文件夹删除失败 → %s%n", dir.getAbsolutePath());
            }
        }
    }
}