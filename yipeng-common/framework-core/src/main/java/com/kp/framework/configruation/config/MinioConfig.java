package com.kp.framework.configruation.config;

import com.kp.framework.configruation.properties.KPMinioProperties;
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
        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties.getUserName(), minioProperties.getPassword())
                .build();
    }
}
