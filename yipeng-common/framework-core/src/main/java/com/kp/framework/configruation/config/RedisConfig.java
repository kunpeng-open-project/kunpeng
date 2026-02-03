package com.kp.framework.configruation.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kp.framework.configruation.properties.KPFrameworkConfig;
import com.kp.framework.utils.kptool.KPStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * redis配置。
 * @author lipeng
 * 2020/1/20
 */
@Configuration
@EnableCaching
@Order(2)
public class RedisConfig {

    @Autowired
    private KPFrameworkConfig kpFrameworkConfig;


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


    /**
     * 自定义Redis缓存管理器配置方法
     * 核心作用：
     * 1. 构建并返回自定义的RedisCacheManager实例，替代默认缓存管理器；
     * 2. 统一配置Redis缓存的序列化规则（解决默认JDK序列化导致的key/value乱码、可读性差问题）；
     * 3. 配置缓存默认过期时间、key前缀、空值缓存策略等通用缓存规则；
     * 4. 支持Java 8时间类型（LocalDateTime等）的序列化/反序列化，避免时间类型序列化异常。
     *
     * @author lipeng
     * @date 2026/1/20
     * @param factory Redis连接工厂（由Spring容器自动注入，底层基于Lettuce实现Redis连接）
     * @return org.springframework.data.redis.cache.RedisCacheManager 自定义配置后的Redis缓存管理器
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        // 构建字符串序列化器：用于缓存Key的序列化，保证Key为可读的字符串格式，避免乱码
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        // 配置Jackson对象映射器：解决Value序列化问题，支持复杂对象、时间类型序列化
        ObjectMapper om = new ObjectMapper();
        // 设置所有属性可见（包括private），保证对象的所有字段都能被序列化/反序列化
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 激活默认的类型信息：序列化时保留对象的类型信息，反序列化时能正确还原为原对象类型
        // NON_FINAL：非final类才添加类型信息；WRAPPER_ARRAY：类型信息以数组包裹形式存储
        om.activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_ARRAY
        );
        // 注册JavaTimeModule模块：支持LocalDateTime/LocalDate等Java 8新时间类型的序列化
        om.registerModule(new JavaTimeModule());
        // 关闭时间类型序列化为时间戳：改为以ISO格式（如2026-01-20T17:00:00）存储，提高可读性
        om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 构建Jackson Redis序列化器：用于缓存Value的序列化，将对象转为JSON格式存储
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(om, Object.class);

        // 3. 构建Redis缓存核心配置规则
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(5))  // 设置缓存默认过期时间：5分钟（替代原1小时配置），避免缓存永久有效导致数据不一致
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer))  // 配置Key的序列化方式：使用字符串序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jacksonSerializer)) // 配置Value的序列化方式：使用Jackson JSON序列化器
                .disableCachingNullValues() // 禁用空值缓存：防止缓存null值导致缓存穿透问题（请求穿透缓存直接打满数据库）
                .prefixCacheNameWith(KPStringUtil.format("yipeng:cache:{0}:", kpFrameworkConfig.getProjectName())); // 配置缓存Key前缀：格式为 "yipeng:cache:{项目名}:"，区分不同项目/环境的缓存，避免Key冲突

        // 构建并返回Redis缓存管理器
        return RedisCacheManager.builder(factory)
                .cacheDefaults(cacheConfig) // 构建并返回Redis缓存管理器
                .build();// 构建并返回Redis缓存管理器
    }
}