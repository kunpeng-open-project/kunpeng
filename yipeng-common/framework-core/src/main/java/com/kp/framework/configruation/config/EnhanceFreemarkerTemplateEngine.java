package com.kp.framework.configruation.config;

import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 自定义代码生成器模板  用于支持自定义模版。
 * @author lipeng
 * 2024/12/27
 */
public class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {
    @Override
    protected void outputCustomFile(@NotNull List<CustomFile> customFiles, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2);
        customFiles.forEach(customFile -> {
            String fileName = customFile.getFilePath() + entityName + customFile.getFileName();
            this.outputFile(new File(fileName), objectMap, customFile.getTemplatePath(), true);
        });
    }
}