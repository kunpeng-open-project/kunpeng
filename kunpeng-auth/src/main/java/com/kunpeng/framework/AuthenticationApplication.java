package com.kunpeng.framework;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.kunpeng.framework.utils.kptool.KPIconUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class} )
//@ComponentScan
@MapperScan({"com.**.modules.*.mapper","com.**.mapper"})
@EnableConfigurationProperties
@EnableAsync
@EnableScheduling
@EnableDiscoveryClient
public class AuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationApplication.class, args);
        KPIconUtil.ok(KPIconUtil.GREEN);
        String[] details = {
                "本地接口地址：http://127.0.0.1:9001/doc.html",
                "本地Druid地址：http://127.0.0.1:9001/druid/index.html",
                "正式接口地址：http://kunpengtool.cn/gateway/doc.html",
                "正式前端地址：http://kunpengtool.cn/auth",
                "测试接口地址：",
                "测试前端地址："
        };
        KPIconUtil.println(KPIconUtil.YELLOW, Arrays.asList(details), false);
    }
}
