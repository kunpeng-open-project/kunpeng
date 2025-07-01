package com.kunpeng.framework.configruation.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.kunpeng.framework.configruation.interceptor.DeleteEhcacheSqiInnerceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

;




/**
 * <p>Title: Mybatis模块相关配置</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: www.jfzh.com</p>
 *
 * @author Chen Haidong
 * @version 1.0
 * @date 2020/7/15  15:11
 */
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

}
