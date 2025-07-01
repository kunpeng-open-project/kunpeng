package com.kunpeng.framework;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.kunpeng.framework.utils.kptool.KPIconUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//DruidDataSourceAutoConfigure.class 移除自动加载数据源
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class} )
//@MapperScan({"com.**.modules.*.mapper", "com.jfzh.framework.mapper"})
//@EnableSwagger2Doc
//@ComponentScan({"com.jfzh.framework", "com.jfzh.rht", "com.framework.security"})
@MapperScan({"com.**.modules.*.mapper","com.**.mapper"})
@EnableConfigurationProperties
@EnableAsync
//@EnableScheduling
//@EnableDiscoveryClient
public class AuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
        KPIconUtil.ok(KPIconUtil.GREEN);
        String[] details = {
                "本地接口地址：http://127.0.0.1:9001/doc.html",
                "本地Druid地址：http://127.0.0.1:9001/druid/index.html",

                "正式接口地址：https://getway.jfzh.com.cn/doc.html",
                "正式前端地址：https://jfzh-auth-system-web.jfzh.com.cn",
                "测试接口地址：https://rht-getway.wjbt.net.cn/doc.html",
                "测试前端地址：https://jfzh-auth-system.wjbt.net.cn"
        };
        KPIconUtil.println(KPIconUtil.YELLOW, Arrays.asList(details), false);
    }
}
