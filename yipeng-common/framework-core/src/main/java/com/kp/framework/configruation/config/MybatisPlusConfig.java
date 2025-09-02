package com.kp.framework.configruation.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.kp.framework.configruation.interceptor.DeleteEhcacheSqiInnerceptor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;


/**
 * @Author lipeng
 * @Description mybatisplus 相关配置
 * @Date  2020/7/15
 * @return
 **/
@Configuration
@EnableTransactionManagement
public class MybatisPlusConfig {

    /**
     * @Author lipeng
     * @Description  mybatis plus  配置  拦截器 分页插件等
     * @Date 2025/1/2 9:02
     * @param
     * @return com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
     **/
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //自定义拦截器
        interceptor.addInnerInterceptor(new DeleteEhcacheSqiInnerceptor());
        // 分页插件 如果配置多个插件, 切记分页最后添加
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 阻止恶意的全表更新删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }


    /**
     * @Author lipeng
     * @Description 加入批量添加
     * @Date 2022/3/29 17:05
     * @param
     * @return com.daoben.framework.configruation.MybatisPlusConfig
     **/
    @Bean
    public MybatisPlusBathConfig sqlInjectorPlus(){
        return new MybatisPlusBathConfig();
    }




    /**
     * @Author lipeng
     * @Description 配置数据库类型识别器，用于区分不同数据库类型
     * @Date 2025/8/26
     * @param
     * @return org.apache.ibatis.mapping.DatabaseIdProvider
     **/
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
