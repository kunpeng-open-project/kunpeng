package com.kp.framework;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * 空文件夹清理工具：递归删除指定目录下所有嵌套的空文件夹，保留非空文件夹
 */
public class EmptyFolderCleaner {

    public static void main(String[] args) {
        // 配置需要清理的根目录（根据实际需求修改）
        String rootDir = "F://aa";

        try {
            // 1. 校验根目录合法性
            validateRootDir(rootDir);

            // 2. 递归清理空文件夹（true = 打印清理日志，false = 静默清理）
            int deletedCount = cleanEmptyFolders(new File(rootDir), true);

            // 3. 输出清理结果
            System.out.println("=====================================");
            System.out.printf("空文件夹清理完成！共删除 %d 个空文件夹%n", deletedCount);
            System.out.println("根目录：" + rootDir);
            System.out.println("说明：非空文件夹（含嵌套非空文件夹）已全部保留");

        } catch (IllegalArgumentException e) {
            System.err.println("清理失败：" + e.getMessage());
        } catch (Exception e) {
            System.err.println("清理过程出现异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 递归清理空文件夹
     * @param dir 待清理的目录
     * @param printLog 是否打印清理日志
     * @return 已删除的空文件夹数量
     */
    private static int cleanEmptyFolders(File dir, boolean printLog) {
        int deletedCount = 0;

        // 跳过非目录/不存在的路径
        if (!dir.exists() || !dir.isDirectory()) {
            return deletedCount;
        }

        // 1. 先递归处理所有子文件夹（深度优先：先清理子文件夹，再判断当前文件夹）
        File[] subFiles = dir.listFiles();
        if (subFiles != null) {
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    // 递归清理子文件夹，累计删除数量
                    deletedCount += cleanEmptyFolders(subFile, printLog);
                }
            }
        }

        // 2. 判断当前文件夹是否为空（子文件夹已清理完成，只剩文件或空目录）
        if (isFolderEmpty(dir)) {
            // 调用 KPFileUtil 删除文件夹（工具类已处理日志和异常）
            boolean deleteSuccess = dir.delete();
            if (deleteSuccess) {
                deletedCount++;
                if (printLog) {
                    System.out.printf("已删除空文件夹：%s%n", dir.getAbsolutePath());
                }
            } else {
                if (printLog) {
                    System.err.printf("警告：空文件夹删除失败（可能无权限）→ %s%n", dir.getAbsolutePath());
                }
            }
        }

        return deletedCount;
    }

    /**
     * 判断文件夹是否为空（不含文件，且不含非空子文件夹）
     * @param dir 待判断的文件夹
     * @return true = 空文件夹，false = 非空文件夹
     */
    private static boolean isFolderEmpty(File dir) {
        // 先获取文件夹下所有内容（文件+子文件夹）
        File[] subFiles = dir.listFiles();
        if (subFiles == null) {
            // 无法访问该目录（如权限不足），视为非空（避免误删）
            return false;
        }

        // 遍历所有子内容：只要存在一个文件，或存在一个非空文件夹 → 视为非空
        for (File subFile : subFiles) {
            if (subFile.isFile()) {
                // 文件夹下有文件 → 非空
                return false;
            } else if (subFile.isDirectory() && !isFolderEmpty(subFile)) {
                // 文件夹下有非空子文件夹 → 非空
                return false;
            }
        }

        // 无文件 + 所有子文件夹都是空的 → 视为空文件夹
        return true;
    }

    /**
     * 校验根目录合法性
     * @param rootDir 根目录路径
     */
    private static void validateRootDir(String rootDir) {
        File rootFile = new File(rootDir);

        if (!rootFile.exists()) {
            throw new IllegalArgumentException("根目录不存在：" + rootDir);
        }

        if (!rootFile.isDirectory()) {
            throw new IllegalArgumentException("指定路径不是目录：" + rootDir);
        }

        // 可选：禁止清理系统关键目录（避免误操作）
        List<String> forbiddenDirs = Arrays.asList("C:/Windows", "C:/Program Files", "C:/Program Files (x86)");
        String absolutePath = rootFile.getAbsolutePath().toLowerCase();
        for (String forbiddenDir : forbiddenDirs) {
            if (absolutePath.startsWith(forbiddenDir.toLowerCase())) {
                throw new IllegalArgumentException("禁止清理系统关键目录：" + rootDir);
            }
        }
    }
}