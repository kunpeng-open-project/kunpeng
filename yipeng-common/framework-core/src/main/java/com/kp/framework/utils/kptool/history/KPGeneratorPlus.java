package com.kp.framework.utils.kptool.history;
//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.InjectionConfig;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.FileOutConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.TemplateConfig;
//import com.baomidou.mybatisplus.generator.config.po.TableInfo;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import com.kp.framework.configruation.config.DatabaseConfig;
//import com.kp.framework.configruation.properties.DatasourceProperties;
//import com.kp.framework.entity.po.GeneratorPO;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * @Author lipeng
// * @Description plus 代码生成器
// * @Date 2022/3/20
// * @return
// **/
//@Component
//public class KPGeneratorPlus {
//
//    @Autowired
//    private DatasourceProperties datasourceProperties;
//
//    @Autowired
//    private DatabaseConfig databaseConfig;
//
//    /**
//     * @Author lipeng
//     * @Description
//     * @Date 2022/5/20 19:25
//     * @param generatorPO
//     * @param isFirst 是否首次生成
//     * @return void
//     **/
//    public void generate(GeneratorPO generatorPO, Boolean isFirst) throws Exception {
//        String userName = KPStringUtil.isEmpty(generatorPO.getDatabaseName())?datasourceProperties.getUserName():databaseConfig.getDatasource().get(generatorPO.getDatabaseName()).getUsername();
//        String url = KPStringUtil.isEmpty(generatorPO.getDatabaseName())?datasourceProperties.getUrl():databaseConfig.getDatasource().get(generatorPO.getDatabaseName()).getUrl();
//        String driverClassName = KPStringUtil.isEmpty(generatorPO.getDatabaseName())?datasourceProperties.getDriverClassName():databaseConfig.getDatasource().get(generatorPO.getDatabaseName()).getDriverClassName();
//        String password = KPStringUtil.isEmpty(generatorPO.getDatabaseName())?datasourceProperties.getPassword():databaseConfig.getDatasource().get(generatorPO.getDatabaseName()).getPassword();
//        Resource resource = new ClassPathResource("");
//        String path = new File(resource.getFile().getParent()).getParent();
//
//        System.out.println("生成的跟目录=" + path);
//
//        String packName;
//        if (KPStringUtil.isEmpty(generatorPO.getBackageName())){
//            packName = new StringBuilder()
//                    .append("com.jfzh.rht")
//                    .append(".modules.")
//                    .append(generatorPO.getModulesName())
//                    .toString();
//        }else {
//            packName = new StringBuilder()
//                    .append(generatorPO.getBackageName())
//                    .append(generatorPO.getBackageName().endsWith(".")?"":".")
//                    .append(generatorPO.getModulesName())
//                    .toString();
//        }
//        System.out.println("生成的包名=" + packName);
//
//
//        AutoGenerator mpg = new AutoGenerator();
//        //配置数据源
//        mpg.setDataSource(this.configureDataSource(url, driverClassName, userName, password, userName));
//        //全局配置
//        mpg.setGlobalConfig(this.configureGlobal(path, generatorPO.getAuthor(), isFirst, generatorPO.getKeyStrategy()));
//        //策略配置
//        mpg.setStrategy(this.configureStrategy(generatorPO.getTablePrefix(), generatorPO.getTableName(), generatorPO.getFilesPrefix()));
//        //模版配置
//        mpg.setTemplate(this.configureTemplate(isFirst));
//        //包配置
//        mpg.setPackageInfo(this.configurePackage(packName));
//        //自定义配置
//        mpg.setCfg(this.configureCfg(path, packName, isFirst));
//        // 执行生成
//        mpg.execute();
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description 设置数据库链接信息
//     * @Date 2022/3/20 16:27
//     * @param url url
//     * @param driverName 驱动
//     * @param userName 用户名
//     * @param password 密码
//     * @param schemaName 模式
//     * @return com.baomidou.mybatisplus.generator.config.DatabaseConfig
//     **/
//    private DataSourceConfig configureDataSource(String url, String driverName, String userName, String password, String schemaName){
//        DbType dbType = DbType.MYSQL;
//        if (driverName.toLowerCase().contains("mysql")) dbType = DbType.MYSQL;
//        if (driverName.toLowerCase().contains("sqlserver")) dbType = DbType.SQL_SERVER;
//        if (driverName.toLowerCase().contains("pgsql")) dbType = DbType.POSTGRE_SQL;
//        if (driverName.toLowerCase().contains("order")) dbType = DbType.ORACLE;
//
//        // 数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setDbType(dbType);
//        dsc.setDriverName(driverName);
//        if (KPStringUtil.isNotEmpty(schemaName))
//            dsc.setSchemaName(schemaName);
//        dsc.setUrl(url);
//        dsc.setUsername(userName);
//        dsc.setPassword(password);
//
////        dsc.setTypeConvert(new PostgreSqlTypeConvert() {
////            // 自定义数据库表字段类型转换【可选】
////            @Override
////            public IColumnType processTypeConvert(GlobalConfig gc, String fieldType) {
////                System.out.println("转换类型：" + fieldType);
////                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
////                //默认会把日期类型 转为LocalDateTime ，在查询的时候会报错，这里改为Date
////                String t = fieldType.toLowerCase();
////                if (t.contains("date") || t.contains("time") || t.contains("year")) {
////                    return DbColumnType.DATE;
////                } else {
////                    return super.processTypeConvert(gc, fieldType);
////                }
////
////            }
////        });
//        return dsc;
//    }
//
//
//    /**
//     * @Author lipeng
//     * @Description  设置全局配置设置
//     * @Date 2022/3/20 16:27
//     * @param path 根路径
//     * @param author 作者
//     * @param isFirst 是否首次生成
//     * @param keyStrategy 主键策略
//     * @return com.baomidou.mybatisplus.generator.config.GlobalConfig
//     **/
//    private GlobalConfig configureGlobal(String path, String author, Boolean isFirst, String keyStrategy){
//        // 全局配置
//        GlobalConfig gc = new GlobalConfig();
//        gc.setAuthor(author);
//        gc.setSwagger2(true); //实体属性 Swagger2 注解
//        gc.setOutputDir(path.concat("//src//main//java"));//代码生成路径
//        if (isFirst){//首次不覆盖
//            gc.setFileOverride(false);// 是否覆盖同名文件，默认是false
//        }else{
//            gc.setFileOverride(true);// 是否覆盖同名文件，默认是false
//        }
//
//        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
//        gc.setEnableCache(true);// XML 二级缓存
//        gc.setBaseResultMap(true);// XML ResultMap
//        gc.setBaseColumnList(true);// XML columList
////        gc.setDateType(DateType.TIME_PACK);// XML columList
//        gc.setOpen(false);//生成后打开文件夹
//
//        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
//        gc.setControllerName("%sController");
////        gc.setServiceName("%sService");
//        gc.setServiceImplName("%sService");
//        gc.setMapperName("%sMapper");
//        gc.setXmlName("%sMapper");
//        gc.setEntityName("%sPO");
//
//        if (keyStrategy.equalsIgnoreCase("uuid"))
//            gc.setIdType(IdType.UUID);
//        if (keyStrategy.equalsIgnoreCase("自增"))
//            gc.setIdType(IdType.AUTO);
//        return gc;
//    }
//
//
//
//    /**
//     * @Author lipeng
//     * @Description  设置策略
//     * @Date 2022/3/20 16:37
//     * @param tablePrefix 表前缀
//     * @param tableNnmes 需要生成的表名
//     * @param filesPrefix 字段前缀
//     * @return com.baomidou.mybatisplus.generator.config.StrategyConfig
//     **/
//    private StrategyConfig configureStrategy(String tablePrefix, String[] tableNnmes, String[] filesPrefix){
//        // 策略配置
//        StrategyConfig strategy = new StrategyConfig();
//        //strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
//        if(tablePrefix != null)
//            strategy.setTablePrefix(tablePrefix.contains("_")?tablePrefix:tablePrefix.concat("_"));// 表前缀
//        if(filesPrefix != null)
//            strategy.setFieldPrefix(filesPrefix);// 字段前缀
//        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//采用驼峰映射
//        strategy.setEntityLombokModel(true);//【实体】是否为lombok模型（默认 false）
//        strategy.setRestControllerStyle(true); // @RestController
//        strategy.setEntityTableFieldAnnotationEnable(true);
//        strategy.setInclude(tableNnmes); // 需要生成的表.如果需要生成所有的, 注释掉此行就可以
//        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
//        // 自定义实体父类
//         strategy.setSuperEntityClass("com.jfzh.framework.entity.bo.ParentBO");
//        // 自定义实体，公共字段
//         strategy.setSuperEntityColumns(new String[] { "create_date" ,"create_user_id", "create_user_name","update_date","update_user_id","update_user_name","delete_flag"});
//        // 自定义 mapper 父类
//         strategy.setSuperMapperClass("com.jfzh.framework.mapper.ParentMapper");
//
//
//        // 自定义 service 父类
//        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
//        // 自定义 service 实现类父类
//        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
//        // 自定义 controller 父类
//        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
//        // 【实体】是否生成字段常量（默认 false）
//        // public static final String ID = "test_id";
//        // strategy.setEntityColumnConstant(true);
//        // 【实体】是否为构建者模型（默认 false）
//        // public User setName(String name) {this.name = name; return this;}
//        // strategy.setEntityBuilderModel(true);
//
//
//         return strategy;
//    }
//
//    /**
//     * @Author lipeng
//     * @Description 配置模板
//     * @Date 2024/4/8 18:34
//     * @param isFirst
//     * @return com.baomidou.mybatisplus.generator.config.TemplateConfig
//     **/
//    private TemplateConfig configureTemplate(Boolean isFirst) {
//        // 选择 freemarker 引擎，默认 Veloctiy  用
////        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//
//        // 配置模板
//        TemplateConfig templateConfig = new TemplateConfig();
//
//        if (isFirst){
//            templateConfig.setService(null)
//                    .setController("controller.java.vm")
//                    .setServiceImpl("serviceImpl.java.vm")
//                    .setMapper("mapper.java.vm")
//                    .setEntity("entity.java.vm")
//                    .setXml(null);
//
//        }else{
//            templateConfig.setController(null)
//                    .setService(null)
//                    .setServiceImpl(null)
//                    .setMapper("mapper.java.vm")
//                    .setEntity("entity.java.vm")
//                    .setXml(null);
//        }
//        return templateConfig;
//    }
//
//
//
//    /**
//     * @Author lipeng
//     * @Description 包名
//     * @Date 2024/7/28 15:27
//     * @param packName
//     * @return com.baomidou.mybatisplus.generator.config.PackageConfig
//     **/
//    private PackageConfig configurePackage(String packName) {
//        // 包配置
//        PackageConfig packageConfig = new PackageConfig();
//        packageConfig.setParent(packName);// 自定义包路径
//        packageConfig.setController("controller");// 这里是控制器包名，默认 web
//        packageConfig.setMapper("mapper");// 设置Mapper包名，默认mapper
//        packageConfig.setService("service");// 设置Service包名，默认service
//        packageConfig.setServiceImpl("service");// 设置Service包名，默认service
//        packageConfig.setEntity("po");// 设置Entity包名，默认entity,继承的父类  已序列化
//        packageConfig.setXml("mapper.xml");// 设置Mapper XML包名，默认mapper.xml
//        return packageConfig;
//    }
//
//
//
//    /**
//     * @Author lipeng
//     * @Description 自定义配置
//     * @Date 2024/7/28 15:55
//     * @param path 生成的跟目录
//     * @param packName 包名称
//     * @return com.baomidou.mybatisplus.generator.InjectionConfig
//     **/
//    private InjectionConfig configureCfg(String path, String packName, Boolean isFirst) {
//        List<FileOutConfig> focList = new ArrayList<>();
//        String finalCanonicalPath = path;
//
//        //自定义配置
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                this.setMap(map);
//            }
//        };
//
//        //自定义生成xml  修改xml 位置 和文件生成名称
//        focList.add(new FileOutConfig("/mapper.xml.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return new StringBuilder(finalCanonicalPath)
//                        .append("//src//main//resources//mapper//")
//                        .append( packName.substring(packName.lastIndexOf(".")+1, packName.length()))
//                        .append("//")
//                        .append(tableInfo.getMapperName())
//                        .append(".xml")
//                        .toString();
//            }
//        });
//
//        //重复生成
//        if (!isFirst){
//            cfg.setFileOutConfigList(focList);
//            return cfg;
//        }
//
//        //首次生成
//        focList.add(new FileOutConfig("/listParamPO.java.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 列表入参
//                return  finalCanonicalPath + "\\src\\main\\java\\" + packName.replaceAll("\\.", "\\\\") + "\\po\\param\\" + tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2) + "ListParamPO.java";
//            }
//        });
//
//        focList.add(new FileOutConfig("/editParamPO.java.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 新增或修改的入参
//                return  finalCanonicalPath + "\\src\\main\\java\\" + packName.replaceAll("\\.", "\\\\") + "\\po\\param\\" + tableInfo.getEntityName().substring(0, tableInfo.getEntityName().length() - 2) + "EditParamPO.java";
//            }
//        });
//
//        cfg.setFileOutConfigList(focList);
//        return cfg;
//    }
//
////
////    public static void main(String[] args) {
////        //获取项目路径
////        try {
////            path = new File("").getCanonicalPath();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////        System.out.println("path=" + path);
////        String filePath = path + "//src//main//";
////
////        AutoGenerator mpg = new AutoGenerator();
////        // 选择 freemarker 引擎，默认 Veloctiy
//////        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
////
////        // 全局配置
////        GlobalConfig gc = new GlobalConfig();
////        gc.setAuthor("Mr.Chen");
////        gc.setSwagger2(true); //实体属性 Swagger2 注解
////        gc.setOutputDir(filePath + "java");//代码生成路径
////        gc.setFileOverride(true);// 是否覆盖同名文件，默认是false
////        gc.setActiveRecord(true);// 不需要ActiveRecord特性的请改为false
////        gc.setEnableCache(false);// XML 二级缓存
////        gc.setBaseResultMap(true);// XML ResultMap
////        gc.setBaseColumnList(false);// XML columList
////        gc.setOpen(false);//生成后打开文件夹
////        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
////        gc.setControllerName("%sController");
////        gc.setServiceName("%sService");
////        gc.setServiceImplName("%sServiceImpl");
////        gc.setMapperName("%sMapper");
////        gc.setXmlName("%sMapper");
////        mpg.setGlobalConfig(gc);
////
////        // 数据源配置
////        DatabaseConfig dsc = new DatabaseConfig();
////        dsc.setDbType(DbType.POSTGRE_SQL);
////        dsc.setTypeConvert(new PostgreSqlTypeConvert() {
////            // 自定义数据库表字段类型转换【可选】
////            @Override
////            public IColumnType processTypeConvert(GlobalConfig gc, String fieldType) {
////                System.out.println("转换类型：" + fieldType);
////                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
////                //默认会把日期类型 转为LocalDateTime ，在查询的时候会报错，这里改为Date
////                String t = fieldType.toLowerCase();
////                if (t.contains("date") || t.contains("time") || t.contains("year")) {
////                    return DbColumnType.DATE;
////                } else {
////                    return super.processTypeConvert(gc, fieldType);
////                }
////
////            }
////        });
////        //数据库连接配置
////
////
////
////        dsc.setDriverName("org.postgresql.Driver");
////
////        dsc.setSchemaName("bpm");
////        dsc.setUrl("jdbc:postgresql://117.62.205.181:5432/hivedb");
////        dsc.setUsername("bpm");
////        dsc.setPassword("lp1993LP@#");
////        mpg.setDataSource(dsc);
////
////        // 策略配置
////        StrategyConfig strategy = new StrategyConfig();
////        //strategy.setCapitalMode(true);// 全局大写命名 ORACLE 注意
////        strategy.setTablePrefix("bpm_");// 此处可以修改为您的表前缀
////        strategy.setFieldPrefix(new String[]{"bp_","bpd_"});
////        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
////        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//采用驼峰映射
////        strategy.setEntityLombokModel(true);//【实体】是否为lombok模型（默认 false）
////        strategy.setEntityTableFieldAnnotationEnable(true);
//////        strategy.setInclude(new String[]{"bpm_process"}); // 需要生成的表.如果需要生成所有的, 注释掉此行就可以
////        // strategy.setExclude(new String[]{"test"}); // 排除生成的表
////        // 自定义实体父类
////        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
////        // 自定义实体，公共字段
////         strategy.setSuperEntityColumns(new String[] { "test_id", "age","createUserId" });
////        // 自定义 mapper 父类
////        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
////        // 自定义 service 父类
////        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
////        // 自定义 service 实现类父类
////        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
////        // 自定义 controller 父类
////        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
////        // 【实体】是否生成字段常量（默认 false）
////        // public static final String ID = "test_id";
////        // strategy.setEntityColumnConstant(true);
////        // 【实体】是否为构建者模型（默认 false）
////        // public User setName(String name) {this.name = name; return this;}
////        // strategy.setEntityBuilderModel(true);
////        mpg.setStrategy(strategy);
////
////        // 包配置
////        PackageConfig pc = new PackageConfig();
////        pc.setParent("com.faw.jetta1111");// 自定义包路径
////        pc.setController("controller");// 这里是控制器包名，默认 web
////        pc.setMapper("mapper");// 设置Mapper包名，默认mapper
////        pc.setService("service");// 设置Service包名，默认service
////        pc.setEntity("pojo.entity");// 设置Entity包名，默认entity,继承的父类  已序列化
////        pc.setXml("mapper.xml");// 设置Mapper XML包名，默认mapper.xml
////        mpg.setPackageInfo(pc);
////
////
////        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
////        InjectionConfig cfg = new InjectionConfig() {
////            @Override
////            public void initMap() {
//////                Map<String, Object> map = new HashMap<String, Object>();
//////                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//////                this.setMap(map);
////            }
////        };
////        // 调整 xml 生成目录演示
////        List<FileOutConfig> focList = new ArrayList<>();
////
////
////
////        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
////            @Override
////            public String outputFile(TableInfo tableInfo) {
////                return filePath + "//resources//mapper//" + tableInfo.getEntityName() + "Mapper.xml";
////            }
////        });
////
////        // 配置模板
//////        TemplateConfig templateConfig = new TemplateConfig();
//////        //控制 不生成 controller
//////        templateConfig.setController("");
//////        templateConfig.setXml(null);
//////        mpg.setTemplate(templateConfig);
////
////        cfg.setFileOutConfigList(focList);
////        mpg.setCfg(cfg);
////
////        // 执行生成
////        mpg.execute();
////
////        // 打印注入设置【可无】
//////        System.err.println(mpg.getCfg().getMap().get("abc"));
////    }
//}
