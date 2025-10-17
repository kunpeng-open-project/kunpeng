package com.kp.framework.configruation.config;

import com.kp.framework.configruation.properties.KPMinioProperties;
import com.kp.framework.utils.kptool.KPStringUtil;
import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MinioConfig {

    @Autowired
    private KPMinioProperties minioProperties;

    @Bean("minioClient")
    public MinioClient getMinioClient()  {
//        MinioClient minioClient = new MinioClient(endpoint, accessKey, secretKey);
//        return minioClient;
        //用户不使用minio时保证启动不报错
        if (KPStringUtil.isEmpty(minioProperties.getUrl())) return null;
        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getUserName(), minioProperties.getPassword())
                .build();
    }
}
