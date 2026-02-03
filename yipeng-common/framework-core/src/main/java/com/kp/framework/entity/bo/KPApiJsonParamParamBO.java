package com.kp.framework.entity.bo;

import io.swagger.v3.oas.models.media.Schema;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 全局 Schema 注册器。
 * @author lipeng
 * 2025/12/30
 */
public class KPApiJsonParamParamBO {

    public static final ConcurrentHashMap<String, Schema> SCHEMAS = new ConcurrentHashMap<>();
}
