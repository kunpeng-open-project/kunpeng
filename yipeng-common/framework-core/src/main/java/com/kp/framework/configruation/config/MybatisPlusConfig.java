package com.kp.framework.configruation.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * mybatisplus 相关配置。
 * @author lipeng
 * 2020/7/15
 */
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * mybatis plus  配置  拦截器 分页插件等。
     * @author lipeng
     * 2025/1/2
     * @return com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //自定义拦截器
//        interceptor.addInnerInterceptor(new DeleteEhcacheSqiInnerceptor());
        // 分页插件 如果配置多个插件, 切记分页最后添加
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 阻止恶意的全表更新删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;

//          PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
//        // 设置数据库类型（核心修复点）
//        paginationInnerInterceptor.setDbType(DbType.MYSQL);
//        // 可选：设置最大单页限制数量，默认 500 条，-1 不受限
//        paginationInnerInterceptor.setMaxLimit(500L);
//        interceptor.addInnerInterceptor(paginationInnerInterceptor);
    }

    /**
     * 加入批量添加。
     * @author lipeng
     * 2022/3/29
     * @return com.kp.framework.configruation.config.MybatisPlusBathConfig
     */
    @Bean
    public MybatisPlusBathConfig sqlInjectorPlus() {
        return new MybatisPlusBathConfig();
    }

    /**
     * 配置数据库类型识别器，用于区分不同数据库类型。
     * @author lipeng
     * 2025/8/26
     * @return org.apache.ibatis.mapping.DatabaseIdProvider
     */
    @Bean
    public DatabaseIdProvider databaseIdProvider() {
        VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();

        // 数据库厂商名称与识别标识的映射
        properties.setProperty("MySQL", "mysql");
        properties.setProperty("PostgreSQL", "postgresql");
        properties.setProperty("SQL Server", "sqlserver");
        properties.setProperty("Oracle", "oracle");
        properties.setProperty("H2", "h2"); // 支持H2内存数据库用于测试

        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }

}
