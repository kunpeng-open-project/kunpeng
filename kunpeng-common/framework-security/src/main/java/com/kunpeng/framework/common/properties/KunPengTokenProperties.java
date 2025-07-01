package com.kunpeng.framework.common.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "kp.token")
@Configuration
public class KunPengTokenProperties {

    // 令牌自定义标识
    private String header = "Authorization";

    // 令牌秘钥
    private String secret = "abcdefghijklmnopqrstuvwxyz";

    // 令牌有效期（默认30分钟）单位秒
    private int expireTime = 1800;

    // 令牌前缀
    private String head = "Bearer";

    // 是否允许同一账号多人登录
    private Boolean multiAccess = false;

}
