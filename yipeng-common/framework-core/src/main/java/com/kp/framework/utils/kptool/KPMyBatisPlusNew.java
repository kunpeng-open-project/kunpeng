package com.kp.framework.utils.kptool;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.kp.framework.configruation.config.DatabaseConfig;
import com.kp.framework.configruation.config.EnhanceFreemarkerTemplateEngine;
import com.kp.framework.configruation.properties.DatasourceProperties;
import com.kp.framework.entity.bo.ParentBO;
import com.kp.framework.entity.po.GeneratorPO;
import com.kp.framework.exception.KPServiceException;
import com.kp.framework.mapper.ParentMapper;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * 代码生成器。
 * @author lipeng
 * 2022/3/20
 */
@Component
public class KPMyBatisPlusNew {

    @Autowired
    private DatasourceProperties datasourceProperties;

    @Autowired
    private DatabaseConfig databaseConfig;

    //原始 启用 留着备份
    private void generate(GeneratorPO generatorPO) throws Exception {
        String userName = databaseConfig.getDatasource().get(generatorPO.getDatabaseName()).getUsername();
        String url = databaseConfig.getDatasource().get(generatorPO.getDatabaseName()).getUrl();
        String password = databaseConfig.getDatasource().get(generatorPO.getDatabaseName()).getPassword();
        Resource resource = new ClassPathResource("");
        String path = new File(resource.getFile().getParent()).getParent();

        System.out.println("生成的跟目录=" + path);

        String packName = new StringBuilder()
                .append(generatorPO.getBackageName())
                .append(generatorPO.getBackageName().endsWith(".") ? "" : ".")
                .append(generatorPO.getModulesName())
                .toString();
        System.out.println("生成的包名=" + packName);


        FastAutoGenerator.create(url, userName, password).
                dataSourceConfig(builder -> builder
                        .typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            // 兼容旧版本转换成Integer
                            if (JdbcType.TINYINT == metaInfo.getJdbcType()) {
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                // 全局配置
                .globalConfig(builder -> builder
                                .author(generatorPO.getAuthor()) //设置作者名
                                .enableSwagger() // 开启 Swagger 模式
                                .disableOpenDir() //禁止自动打开输出目录
                                .outputDir(path.concat("//src//main//java")) //指定代码生成的输出目录
//                        .enableSpringdoc()
//                        .dateType(DateType.ONLY_DATE) // 设置时间类型策略
                                .commentDate("yyyy-MM-dd HH:mm:ss") //设置注释日期格式
                )
                // 包配置
                .packageConfig(builder -> builder
                        .parent(packName) // 自定义包路径
                        .controller("controller") // 这里是控制器包名，默认 web
                        .service("service") // 设置Service包名，默认service
                        .serviceImpl("service") // 设置Service包名，默认service service.impl
                        .entity("po") // 设置Entity包名，默认entity,继承的父类  已序列化
                        .mapper("mapper") // 设置Mapper包名，默认mapper
                        .xml("mapper.xml") // 设置Mapper XML包名，默认mapper.xml
                        .pathInfo(Collections.singletonMap(OutputFile.xml, KPStringUtil.format("{0}/src/main/resources/mapper/{1}", path, packName.substring(packName.lastIndexOf(".") + 1, packName.length())))) // 设置 Mapper XML 文件生成路径

                )
                // 策略配置
                .strategyConfig(builder -> builder
                                .enableCapitalMode() // enableCapitalMode
                                .addInclude(generatorPO.getTableName()) //生成的表
                                .addTablePrefix(KPStringUtil.isEmpty(generatorPO.getTablePrefix()) ? new ArrayList<>() : generatorPO.getTablePrefix()) // 增加过滤表前缀
                                .addFieldPrefix(KPStringUtil.isEmpty(generatorPO.getFilesPrefix()) ? new ArrayList<>() : generatorPO.getFilesPrefix()) //增加过滤字段前缀

                                .controllerBuilder()//控制器
                                .enableHyphenStyle() //开启驼峰转连字符
                                .enableFileOverride()// 覆盖已生成文件
                                .enableRestStyle()//开启生成@RestController 控制器
                                .formatFileName("%sController")     //格式化文件名称

                                .serviceBuilder()//服务层
                                .enableFileOverride() // 覆盖已生成文件
                                .formatServiceImplFileName("%sService") //格式化文件名称
//                                .
                                .entityBuilder()//实体类
                                .superClass(ParentBO.class)
                                .idType(generatorPO.getIdType()) //设置主键策略
                                .enableFileOverride() // 覆盖已生成实体文件
                                .enableChainModel() // 开启链式模型
                                .enableLombok() // 开启 Lombok 模型
                                .enableRemoveIsPrefix() // 开启 Boolean 类型字段移除 is 前缀
                                .enableTableFieldAnnotation() //开启生成实体时生成字段注解 数据库名称
                                .formatFileName("%sPO") //格式化文件名称

                                .mapperBuilder()
                                .enableFileOverride() //覆盖已生成文件
                                .enableMapperAnnotation() //开启 @Mapper 注解
                                .enableBaseResultMap() //启用 BaseResultMap 生成
                                .enableBaseColumnList() //启用 BaseColumnList
                                .formatMapperFileName("%sMapper") //格式化文件名称
                                .formatXmlFileName("%sMapper") //格式化文件名称
                                .superClass(ParentMapper.class)
//                                .cache(LoggingEhcache.class)

//                        .enableActiveRecord() //开启 ActiveRecord 模型 也就是实体类继承Model
//                        .enableColumnConstant() // 开启生成字段常量
//                        .logicDeleteColumnName("delete_flag")
                )
                //模版配置
                .templateConfig(builder -> builder
                        .controller("freemarker/controller.java")
                        .service(null)
                        .serviceImpl("freemarker/serviceImpl.java")
                        .mapper("freemarker/mapper.java")
                        .entity("freemarker/entity.java")
                        .xml("freemarker/mapper.xml")
                )
                //自定义模版配置
                .injectionConfig(consumer -> {
                    List<CustomFile> customFileList = Arrays.asList(
                            new CustomFile.Builder()
                                    .fileName("ListParamPO.java")
                                    .filePath(path + "\\src\\main\\java\\" + packName.replaceAll("\\.", "\\\\") + "\\po\\param\\")
                                    .packageName(packName + ".po.param")
                                    .templatePath("/freemarker/listParamPO.java.ftl")
                                    .build(),
                            new CustomFile.Builder()
                                    .fileName("EditParamPO.java")
                                    .filePath(path + "\\src\\main\\java\\" + packName.replaceAll("\\.", "\\\\") + "\\po\\param\\")
                                    .packageName(packName + ".po.param")
                                    .templatePath("/freemarker/editParamPO.java.ftl")
                                    .build()
                    );
                    consumer.customFile(customFileList);

                    // 添加日志输出以检查传递给模板的数据
                    consumer.beforeOutputFile((file, objectMap) -> {
                        System.out.println("Template Data: " + objectMap.toString());
                    });
                })
                .templateEngine(new EnhanceFreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 首次生成。
     * @author lipeng
     * 2025/4/7
     * @param generatorPO 入参
     */
    public void firstGenerate(GeneratorPO generatorPO) {
        String path = createPath();
        String packName = createPack(generatorPO);

        createFastAutoGenerator(generatorPO)
                .globalConfig(configureGlobal(generatorPO.getAuthor(), path))
                .packageConfig(configurePackage(path, packName))
                .injectionConfig(configureInjection(path, packName))
                .strategyConfig(configureStrategy(generatorPO))
                .templateEngine(new EnhanceFreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 重新生成。
     * @author lipeng
     * 2025/4/7
     * @param generatorPO 入参
     */
    public void againGenerate(GeneratorPO generatorPO) {
        String path = createPath();
        String packName = createPack(generatorPO);

        createFastAutoGenerator(generatorPO)
                .globalConfig(configureGlobal(generatorPO.getAuthor(), path))
                .packageConfig(configurePackage(path, packName))
                .templateConfig(builder -> {
                    builder.disable(TemplateType.CONTROLLER)
                            .disable(TemplateType.SERVICE)
                            .disable(TemplateType.SERVICE_IMPL)
//                            .serviceImpl("freemarker/serviceImpl.java") // 设置Service包名，默认service service.impl
                            .mapper("freemarker/mapper.java")
                            .entity("freemarker/entity.java")
                            .xml("freemarker/mapper.xml");
                })
                .strategyConfig(configureStrategy(generatorPO))
                .templateEngine(new EnhanceFreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 生成的目录跟文件。
     * @author lipeng
     * 2025/4/7
     * @return java.lang.String
     */
    private String createPath() {
        Resource resource = new ClassPathResource("");
        String path = null;
        try {
            path = new File(resource.getFile().getParent()).getParent();
        } catch (IOException e) {
            throw new KPServiceException("生成跟目录失败" + e.getMessage());
        }
        System.out.println("生成的跟目录=" + path);
        return path;
    }

    /**
     * 包名配置。
     * @author lipeng
     * 2025/4/7
     * @param generatorPO 入参
     * @return java.lang.String
     */
    private String createPack(GeneratorPO generatorPO) {
        String packName = new StringBuilder()
                .append(generatorPO.getBackageName())
                .append(generatorPO.getBackageName().endsWith(".") ? "" : ".")
                .append(generatorPO.getModulesName())
                .toString();
        System.out.println("生成的包名=" + packName);
        return packName;
    }

    /**
     * 配置数据源和数据模式。
     * @author lipeng
     * 2025/4/7
     * @param generatorPO 入参
     * @return com.baomidou.mybatisplus.generator.FastAutoGenerator
     */
    private FastAutoGenerator createFastAutoGenerator(GeneratorPO generatorPO) {
        String userName = null;
        try {
            userName = databaseConfig.getDatasource().get(generatorPO.getDatabaseName()).getUsername();
        } catch (Exception ex) {
            throw new KPServiceException(KPStringUtil.format("当前项目未配置{0}数据库", generatorPO.getDatabaseName()));
        }
        String url = databaseConfig.getDatasource().get(generatorPO.getDatabaseName()).getUrl();
        String password = databaseConfig.getDatasource().get(generatorPO.getDatabaseName()).getPassword();

        return FastAutoGenerator.create(url, userName, password).
                dataSourceConfig(builder -> builder
                        .typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            // 兼容旧版本转换成Integer
                            if (JdbcType.TINYINT == metaInfo.getJdbcType()) {
                                return DbColumnType.INTEGER;
                            }
                            // 处理 SMALLINT 转为 Integer
                            if (JdbcType.SMALLINT == metaInfo.getJdbcType()) {
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                );
    }

    /**
     * 全局配置。
     * @author lipeng
     * 2025/4/7
     * @param author 作者
     * @param path 生成的路径
     * @return java.util.function.Consumer<com.baomidou.mybatisplus.generator.config.GlobalConfig.Builder>
     */
    private Consumer<GlobalConfig.Builder> configureGlobal(String author, String path) {
        return builder -> {
            builder.author(author) //生成的作者名字
                    .enableSwagger() // 开启 Swagger 模式
                    .disableOpenDir() //禁止打开输出目录，默认false
                    .outputDir(path.concat("//src//main//java")) //指定代码生成的输出目录
//                .dateType(DateType.ONLY_DATE) // 设置时间类型策略 TIME_PACK
                    .commentDate("yyyy-MM-dd HH:mm:ss"); //设置注释日期格式
        };
    }

    /**
     * 包配置。
     * @author lipeng
     * 2025/4/7
     * @param path 生成的路径
     * @param packName 生成的包名
     * @return java.util.function.Consumer<com.baomidou.mybatisplus.generator.config.PackageConfig.Builder>
     */
    private Consumer<PackageConfig.Builder> configurePackage(String path, String packName) {
        return builder -> {
            builder.parent(packName) // 自定义包路径
                    .controller("controller") // 这里是控制器包名，默认 web
                    .service("service") // 设置Service包名，默认service
                    .serviceImpl("service") // 设置Service包名，默认service service.impl
                    .entity("po") // 设置Entity包名，默认entity,继承的父类  已序列化
                    .mapper("mapper") // 设置Mapper包名，默认mapper
                    .xml("mapper.xml") // 设置Mapper XML包名，默认mapper.xml
                    .pathInfo(Collections.singletonMap(OutputFile.xml, KPStringUtil.format("{0}/src/main/resources/mapper/{1}", path, packName.substring(packName.lastIndexOf(".") + 1, packName.length())))); // 设置 Mapper XML 文件生成路径
        };
    }

    /**
     * 注入配置 (自定义模版配置)。
     * @author lipeng
     * 2025/4/7
     * @param path 生成的路径
     * @param packName 生成的包名
     * @return java.util.function.Consumer<com.baomidou.mybatisplus.generator.config.InjectionConfig.Builder>
     */
    private Consumer<InjectionConfig.Builder> configureInjection(String path, String packName) {
        return builder -> {
            builder.customFile(Arrays.asList(
                            new CustomFile.Builder()
                                    .fileName("ListParamPO.java")
                                    .filePath(path + "\\src\\main\\java\\" + packName.replaceAll("\\.", "\\\\") + "\\po\\param\\")
                                    .packageName(packName + ".po.param")
                                    .templatePath("/freemarker/listParamPO.java.ftl")
                                    .build(),
                            new CustomFile.Builder()
                                    .fileName("EditParamPO.java")
                                    .filePath(path + "\\src\\main\\java\\" + packName.replaceAll("\\.", "\\\\") + "\\po\\param\\")
                                    .packageName(packName + ".po.param")
                                    .templatePath("/freemarker/editParamPO.java.ftl")
                                    .build()
                    ))
                    .beforeOutputFile((file, objectMap) -> {
                        System.out.println("Template Data: " + objectMap.toString());
                    });
        };
    }

    /**
     * 策略配置。
     * @author lipeng
     * 2025/4/7
     * @param generatorPO 生成参数
     * @return java.util.function.Consumer<com.baomidou.mybatisplus.generator.config.StrategyConfig.Builder>
     */
    private Consumer<StrategyConfig.Builder> configureStrategy(GeneratorPO generatorPO) {
        return builder -> {
            builder.enableCapitalMode() // enableCapitalMode
                    .addInclude(generatorPO.getTableName()) //生成的表
                    .addTablePrefix(KPStringUtil.isEmpty(generatorPO.getTablePrefix()) ? new ArrayList<>() : generatorPO.getTablePrefix()) // 增加过滤表前缀
                    .addFieldPrefix(KPStringUtil.isEmpty(generatorPO.getFilesPrefix()) ? new ArrayList<>() : generatorPO.getFilesPrefix()) //增加过滤字段前缀

                    .controllerBuilder()//控制器
                    .enableHyphenStyle() //开启驼峰转连字符
                    .enableRestStyle()//开启生成@RestController 控制器
                    .formatFileName("%sController")     //格式化文件名称
                    .template("freemarker/controller.java")

                    .serviceBuilder()//服务层
                    .disableService()
                    .formatServiceImplFileName("%sService") //格式化文件名称
                    .serviceImplTemplate("freemarker/serviceImpl.java")

//                                .
                    .entityBuilder()//实体类
                    .superClass(ParentBO.class)
                    .idType(generatorPO.getIdType()) //设置主键策略
                    .enableFileOverride() // 覆盖已生成实体文件
                    .enableChainModel() // 开启链式模型
                    .enableLombok() // 开启 Lombok 模型
                    .enableRemoveIsPrefix() // 开启 Boolean 类型字段移除 is 前缀
                    .enableTableFieldAnnotation() //开启生成实体时生成字段注解 数据库名称
                    .formatFileName("%sPO") //格式化文件名称
                    .javaTemplate("freemarker/entity.java")

                    .mapperBuilder()
                    .enableFileOverride() //覆盖已生成文件
                    .enableMapperAnnotation() //开启 @Mapper 注解
                    .enableBaseResultMap() //启用 BaseResultMap 生成
                    .enableBaseColumnList() //启用 BaseColumnList
                    .formatMapperFileName("%sMapper") //格式化文件名称
                    .formatXmlFileName("%sMapper") //格式化文件名称
                    .superClass(ParentMapper.class)
                    .mapperTemplate("freemarker/mapper.java")
                    .mapperXmlTemplate("freemarker/mapper.xml");
//                                .cache(LoggingEhcache.class)

//                        .enableActiveRecord() //开启 ActiveRecord 模型 也就是实体类继承Model
//                        .enableColumnConstant() // 开启生成字段常量
//                        .logicDeleteColumnName("delete_flag")
        };
    }
}
