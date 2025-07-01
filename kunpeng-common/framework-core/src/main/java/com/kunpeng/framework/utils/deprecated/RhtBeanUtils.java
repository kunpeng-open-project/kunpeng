package com.kunpeng.framework.utils.deprecated;
//
//import com.alibaba.fastjson.JSON;
//import org.apache.commons.lang3.ObjectUtils;
//import org.apache.http.util.TextUtils;
//import org.springframework.beans.BeanUtils;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * Bean 工具类
// *
// * @author chd
// */
//@Deprecated
//public class RhtBeanUtils extends BeanUtils {
//
//    /**
//     * 转为新列表(对象属性名要相同)
//     *
//     * @param originList 原列表
//     * @param tClass     新列表类对象
//     * @param <T>
//     * @return
//     */
//    @Deprecated
//    public static <T> List<T> list2OtherList(List originList, Class<T> tClass) {
//        List<T> list = new ArrayList<>();
//        for (Object info : originList) {
//            T t = JSON.parseObject(JSON.toJSONString(info), tClass);
//            list.add(t);
//        }
//        return list;
//    }
//
//    /**
//     * @MethodName: bean2Map
//     * @Param: [t]
//     * @Return: java.util.Map<java.lang.String, java.lang.Object>
//     * @Description: 实体类转Map, 属性名不变(快速适配小程序)
//     * @Author: yan
//     * @Date 2021/2/20
//     **/
//    @Deprecated
//    public static <T> Map<String, Object> bean2Map(T t) {
//        Field[] declaredFields = t.getClass().getDeclaredFields();
//        Map<String, Object> map = new HashMap<>();
//        for (Field declaredField : declaredFields) {
//            declaredField.setAccessible(true);
//            String name = declaredField.getName();
//            String value = null;
//            try {
//                value = ObjectUtils.toString(declaredField.get(t), "");
//            } catch (IllegalAccessException e) {
//                value = null;
//            }
//            map.put(name, "".equals(value) ? null : value);
//        }
//        return map;
//    }
//
//
//
//    /**
//     * @MethodName: map2Bean
//     * @Param: [map, t]
//     * @Return: T
//     * @Description: 根据map中的key把值赋予对象
//     * @Author: yan
//     * @Date 2021/2/21
//     **/
//    @Deprecated
//    public static String map2Key(Map<String, Object> map, String result) {
//        if (TextUtils.isEmpty(result))
//            result = "";
//
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            String mapKey = entry.getKey();
//            String mapValue = ObjectUtils.toString(entry.getValue(), null);
//
//            if (!TextUtils.isEmpty(mapValue)) {
//                result += (mapKey + mapValue);
//            }
//        }
//
//        return result;
//    }
//    /*  5Tao 2022.2.28 */
//
//
//    /**
//     * @MethodName: bean2MapByTips
//     * @Param: [t]
//     * @Return: java.util.Map<java.lang.String, java.lang.Object>
//     * @Description: 转换时String类型的空字段改为暂无数据
//     * @Author: yan
//     * @Date 2021/2/25
//     **/
//    @Deprecated
//    public static <T> Map<String, Object> bean2MapByTips(T t) {
//        Field[] declaredFields = t.getClass().getDeclaredFields();
//        Map<String, Object> map = new HashMap<>();
//        for (Field declaredField : declaredFields) {
//            declaredField.setAccessible(true);
//            String name = declaredField.getName();
//            String value = null;
//            try {
//                value = ObjectUtils.toString(declaredField.get(t), "");
//            } catch (IllegalAccessException e) {
//                value = "";
//            }
//            if (declaredField.getType().getName().equals("java.lang.String") && "".equals(value)) {
//                map.put(name, "".equals(value) ? "暂无数据" : value);
//            } else {
//                map.put(name, "".equals(value) ? null : value);
//            }
//        }
//
//        return map;
//    }
//}
