package com.kunpeng.framework;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableConfigurationProperties
@EnableAsync
//@EnableSwagger2Doc //开启swagger2
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        String[] details = {
                "本地接口地址：http://127.0.0.1:9001/doc.html",
                "本地Druid地址：http://127.0.0.1:9001/druid/index.html",

                "正式接口地址：https://getway.jfzh.com.cn/doc.html",
                "正式前端地址：https://jfzh-auth-system-web.jfzh.com.cn",
                "测试接口地址：https://rht-getway.wjbt.net.cn/doc.html",
                "测试前端地址：https://jfzh-auth-system.wjbt.net.cn"
        };
        System.out.println(details);
    }
}
