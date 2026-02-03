package com.kp.framework.configruation.config;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.kp.framework.utils.kptool.KPJsonUtil;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * 全局自定义缓存Key生成器（适配所有入参类型）
 * 支持：集合、数组、Map、基本类型、字符串、枚举、JSON对象/数组、自定义对象、null等所有类型
 * @author lipeng
 * 2026/1/15
 */
@Component("pageKeyGenerator")
public class PageKeyGenerator implements KeyGenerator {

    @Override
    @NonNull
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        // 1. 前缀：类名_方法名（核心防重复）
        sb.append(target.getClass().getSimpleName()).append("_").append(method.getName());

        // 2. 遍历所有入参，全类型适配拼接
        for (Object param : params) {
            if (param == null) {
                sb.append("_Null(空值)"); // null值单独标识，避免Key缺失
                continue;
            }

            // 获取参数类型简称（如 ArrayList → ArrayList，int[] → int[]）
            String paramType = getParamTypeSimpleName(param);
            sb.append("_").append(paramType).append("(");

            try {
                // 分类型处理核心逻辑
                if (param instanceof Collection<?>) {
                    // 处理集合类型（List/Set/ArrayList/HashSet等）
                    handleCollectionParam((Collection<?>) param, sb);
                } else if (param.getClass().isArray()) {
                    // 处理数组类型（基本类型数组int[]、对象数组String[]等）
                    handleArrayParam(param, sb);
                } else if (param instanceof JSONObject) {
                    // 处理FastJSON的JSONObject类型
                    handleJSONObjectParam((JSONObject) param, sb);
                } else if (param instanceof JSONArray) {
                    // 处理FastJSON的JSONArray类型
                    handleJSONArrayParam((JSONArray) param, sb);
                } else if (param instanceof Map<?, ?>) {
                    // 处理Map类型（HashMap/LinkedHashMap等）
                    handleMapParam((Map<?, ?>) param, sb);
                } else if (isBasicType(param)) {
                    // 处理基本类型/包装类型/字符串/枚举
                    handleBasicTypeParam(param, sb);
                } else {
                    // 处理自定义对象（转JSON后遍历键值对）
                    handleCustomObjectParam(param, sb);
                }
            } catch (Exception e) {
                // 任何类型处理失败时的兜底逻辑（避免Key生成失败）
                sb.append("解析失败_").append(param.toString().replace(",", "_").replace("=", "_"));
            }

            // 移除最后一个多余的逗号（所有类型通用）
            if (sb.charAt(sb.length() - 1) == ',') {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(")");
        }

        // 最终Key去重空格/特殊字符（防止Redis Key包含非法字符）
        String finalKey = sb.toString().replaceAll("\\s+", "").replaceAll("[\\t\\n\\r]", "");
        return finalKey;
    }

    /**
     * 获取参数类型的简洁名称（适配数组/基本类型）
     */
    private String getParamTypeSimpleName(Object param) {
        Class<?> clazz = param.getClass();
        if (clazz.isArray()) {
            // 数组类型：如 int[] → int[]，String[] → String[]
            Class<?> componentType = clazz.getComponentType();
            return componentType.getSimpleName() + "[]";
        }
        // 普通类型：直接返回类名（如 ArrayList、User、Enum）
        return clazz.getSimpleName();
    }

    /**
     * 判断是否为基本类型/包装类型/字符串/枚举
     */
    private boolean isBasicType(Object param) {
        Class<?> clazz = param.getClass();
        return clazz.isPrimitive() // 基本类型（int/long/boolean等）
                || param instanceof String // 字符串
                || param instanceof Integer || param instanceof Long || param instanceof Double
                || param instanceof Float || param instanceof Boolean || param instanceof Short
                || param instanceof Byte || param instanceof Character || param instanceof Number
                || param instanceof Enum<?>; // 枚举类型
    }

    /**
     * 处理集合类型入参（List/Set等）
     */
    private void handleCollectionParam(Collection<?> collection, StringBuilder sb) {
        for (Object item : collection) {
            if (item != null) {
                // 集合元素递归处理（支持嵌套集合，如 List<List<String>>）
                if (item instanceof Collection<?> || item.getClass().isArray() || item instanceof Map<?, ?>) {
                    sb.append(getParamTypeSimpleName(item)).append("(");
                    if (item instanceof Collection<?>) {
                        handleCollectionParam((Collection<?>) item, sb);
                    } else if (item.getClass().isArray()) {
                        handleArrayParam(item, sb);
                    } else if (item instanceof Map<?, ?>) {
                        handleMapParam((Map<?, ?>) item, sb);
                    }
                    sb.append("),");
                } else {
                    sb.append(item).append(",");
                }
            }
        }
    }

    /**
     * 处理数组类型入参（支持基本类型数组和对象数组）
     */
    private void handleArrayParam(Object array, StringBuilder sb) {
        Class<?> componentType = array.getClass().getComponentType();
        // 基本类型数组（int[]/long[]等）
        if (componentType.isPrimitive()) {
            if (array instanceof int[]) {
                int[] intArray = (int[]) array;
                for (int val : intArray) sb.append(val).append(",");
            } else if (array instanceof long[]) {
                long[] longArray = (long[]) array;
                for (long val : longArray) sb.append(val).append(",");
            } else if (array instanceof boolean[]) {
                boolean[] boolArray = (boolean[]) array;
                for (boolean val : boolArray) sb.append(val).append(",");
            } else if (array instanceof double[]) {
                double[] doubleArray = (double[]) array;
                for (double val : doubleArray) sb.append(val).append(",");
            } else if (array instanceof float[]) {
                float[] floatArray = (float[]) array;
                for (float val : floatArray) sb.append(val).append(",");
            } else if (array instanceof short[]) {
                short[] shortArray = (short[]) array;
                for (short val : shortArray) sb.append(val).append(",");
            } else if (array instanceof byte[]) {
                byte[] byteArray = (byte[]) array;
                for (byte val : byteArray) sb.append(val).append(",");
            } else if (array instanceof char[]) {
                char[] charArray = (char[]) array;
                for (char val : charArray) sb.append(val).append(",");
            }
        } else {
            // 对象数组（String[]/User[]等）
            Object[] objArray = (Object[]) array;
            for (Object item : objArray) {
                if (item != null) sb.append(item).append(",");
            }
        }
    }

    /**
     * 处理Map类型入参
     */
    private void handleMapParam(Map<?, ?> map, StringBuilder sb) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (key != null && value != null) {
                sb.append(key).append("=").append(value).append(",");
            }
        }
    }

    /**
     * 处理JSONObject类型入参
     */
    private void handleJSONObjectParam(JSONObject jsonObject, StringBuilder sb) {
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value != null) {
                sb.append(key).append("=").append(value).append(",");
            }
        }
    }

    /**
     * 处理JSONArray类型入参
     */
    private void handleJSONArrayParam(JSONArray jsonArray, StringBuilder sb) {
        for (Object item : jsonArray) {
            if (item != null) sb.append(item).append(",");
        }
    }

    /**
     * 处理基本类型/包装类型/字符串/枚举
     */
    private void handleBasicTypeParam(Object param, StringBuilder sb) {
        // 枚举类型特殊处理：取枚举名称（而非toString）
        if (param instanceof Enum<?>) {
            sb.append(((Enum<?>) param).name());
        } else {
            // 其他基本类型直接拼接值
            sb.append(param);
        }
    }

    /**
     * 处理自定义对象类型（转JSON后遍历键值对）
     */
    private void handleCustomObjectParam(Object param, StringBuilder sb) {
        // 转非空JSON串（过滤null字段）
        String jsonStr = KPJsonUtil.toJsonStringNotEmpty(param);
        try {
            // 尝试转JSONObject（普通自定义对象）
            JSONObject json = KPJsonUtil.toJson(jsonStr);
            for (Map.Entry<String, Object> entry : json.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (value != null) {
                    sb.append(key).append("=").append(value).append(",");
                }
            }
        } catch (Exception e) {
            // 非JSONObject（如特殊对象）直接拼接JSON串
            sb.append(jsonStr);
        }
    }
}