package com.kunpeng.framework.configruation.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kunpeng.framework.configruation.properties.FrameworkEncryptionProperties;
import com.kunpeng.framework.configruation.properties.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * redis配置
 *
 * @author rht
 */
@Configuration
@EnableCaching
@Order(2)
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private RedisProperties redisProperties;


    @Autowired
    private FrameworkEncryptionProperties frameworkProperties;

//    @Bean(name = "myredisConnectionFactory")
//    public RedisConnectionFactory myLettuceConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
//        redisStandaloneConfiguration.setPort(redisProperties.getPort());
//        redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase() == null?0:redisProperties.getDatabase());
//        if (frameworkProperties.getRedis())
//            redisStandaloneConfiguration.setPassword(new DweEncryptionUtil(huaWeiProperties).decode(redisProperties.getPassword()));
//        return new LettuceConnectionFactory(redisStandaloneConfiguration);
//    }

//    @Bean
//    @SuppressWarnings(value = { "unchecked", "rawtypes" })
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
//        FastJson2JsonRedisSerializer serializer = new FastJson2JsonRedisSerializer(Object.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);
//
//        template.setValueSerializer(serializer);
//        // 使用StringRedisSerializer来序列化和反序列化redis的key值
//        template.setKeySerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//        return template;
//    }


//    @Bean("redisTemplate")
//    @SuppressWarnings("all")
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
//        template.setConnectionFactory(factory);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        // key采用String的序列化方式
//        template.setKeySerializer(stringRedisSerializer);
//        // hash的key也采用String的序列化方式
//        template.setHashKeySerializer(stringRedisSerializer);
//        // value序列化方式采用jackson
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        // hash的value序列化方式采用jackson
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//        return template;
//    }


    /**
     * 配置RedisTemplate Bean，使用Jackson序列化器处理Java对象
     * 支持传统Date类型和Java 8的LocalDateTime/LocalDate等时间类型
     */
    @Bean("redisTemplate")
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建RedisTemplate实例并设置连接工厂
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 创建Jackson JSON序列化器，用于序列化Redis中的值
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();

        // 配置ObjectMapper可见性，允许序列化所有字段（包括私有字段）
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);

        // 启用默认类型信息，确保反序列化时能正确识别具体对象类型
        // 使用NON_FINAL策略，为非final类型添加类型信息
        // 使用WRAPPER_ARRAY格式，将类型信息作为数组包装器存储
        om.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.WRAPPER_ARRAY
        );

        // 注册JavaTimeModule模块，专门处理Java 8日期时间API
        om.registerModule(new JavaTimeModule());

        // 禁用Date类型序列化为时间戳，使用ISO-8601字符串格式
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 将配置好的ObjectMapper设置到序列化器中
        jacksonSerializer.setObjectMapper(om);

        // 创建String类型序列化器，用于处理Redis的键
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // 配置RedisTemplate的序列化策略：
        // - 键和哈希键使用String序列化器，保持可读性
        // - 值和哈希值使用Jackson序列化器，支持复杂对象结构
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setValueSerializer(jacksonSerializer);
        template.setHashValueSerializer(jacksonSerializer);

        // 完成模板配置的初始化工作
        template.afterPropertiesSet();

        return template;
    }
}









