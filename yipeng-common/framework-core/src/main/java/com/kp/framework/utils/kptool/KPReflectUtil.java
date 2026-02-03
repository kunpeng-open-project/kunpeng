package com.kp.framework.utils.kptool;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 反射的工具。
 * @author lipeng
 * 2020/9/3
 */
@UtilityClass
public final class KPReflectUtil {

    private static Logger log = LoggerFactory.getLogger(KPReflectUtil.class);
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * 通过反射获取指定字段。
     * @author lipeng
     * 2024/2/23
     * @param obj 对象
     * @param fieldName 要获取的字段值
     * @return java.lang.Object
     */
    public static Object getField(Object obj, String fieldName) {
        try {
            // 获取字段
            Field field = obj.getClass().getDeclaredField(fieldName);
            // 设置访问权限，允许访问私有字段
            field.setAccessible(true);
            // 获取字段的值
            Object fieldValue = field.get(obj);
            return fieldValue;
        } catch (Exception ex) {
            log.info("[反射获取字段失败！]" + ex.getMessage());
//           throw new KPServiceException("反射获取字段失败！");
        }
        return null;
    }

    /**
     * 获取所有Field  包括父类。
     * @author lipeng
     * 2024/3/26
     * @param clazz  类
     * @return java.util.List<java.lang.reflect.Field>
     */
    public static List<Field> getAllDeclaredFields(Class<?> clazz) {
        List<Field> allFields = new ArrayList<>();
        Set<String> fieldNames = new HashSet<>(); // 用 Set 提高去重效率
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (fieldNames.add(field.getName())) { // 如果是新字段名才添加
                    allFields.add(field);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return allFields;

//        List<Field> allFields = new ArrayList<>();
//        List<String> fileNames = new ArrayList<>();
//        while (clazz != null) {
//            for (Field field : clazz.getDeclaredFields()) {
//                if (fileNames.contains(field.getName())) continue;
//                allFields.add(field);
//                fileNames.add(field.getName());
//            }
//            clazz = clazz.getSuperclass();
//        }
//        fileNames.clear();
//        return allFields;
    }

    /**
     * 获取所有Field  包括父类。
     * @author lipeng
     * 2024/3/26
     * @param clazz  类
     * @param excludeFieldLists 排除的字段
     * @return java.util.List<java.lang.reflect.Field>
     */
    public static List<Field> getAllDeclaredFields(Class<?> clazz, List<String> excludeFieldLists) {
        List<Field> allFields = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (fileNames.contains(field.getName())) continue;
                if (excludeFieldLists.size() > 0 && excludeFieldLists.contains(field.getName())) continue;
                allFields.add(field);
                fileNames.add(field.getName());
            }
            clazz = clazz.getSuperclass();
        }
        fileNames.clear();
        return allFields;
    }

    /**
     * 获取所有Field  包括父类
     * @author lipeng
     * 2024/3/26
     * @param clazz  类
     * @param excludeFieldLists 排除的字段
     * @param globalList 包含的字段
     * @return java.util.List<java.lang.reflect.Field>
     */
    public static List<Field> getAllDeclaredFields(Class<?> clazz, List<String> excludeFieldLists, List<String> globalList) {
        List<Field> allFields = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (globalList.size() > 0) {
                    if (!globalList.contains(field.getName())) continue;
                    allFields.add(field);
                    fileNames.add(field.getName());
                } else {
                    if (fileNames.contains(field.getName())) continue;
                    if (excludeFieldLists.size() > 0 && excludeFieldLists.contains(field.getName())) continue;
                    allFields.add(field);
                    fileNames.add(field.getName());
                }

            }
            clazz = clazz.getSuperclass();
        }
        fileNames.clear();
        return allFields;
    }

    /**
     * 判断对象及其所有属性是否为空，可选是否检查父类的字段。。
     * @author lipeng
     * 2024/3/26
     * @param obj 需要检查的对象
     * @param excludeFieldLists 排除检查的字段集合
     * @param checkSuperClassFields 是否检查父类的字段，默认为false
     * @return boolean
     */
    public static boolean isObjectEmpty(Object obj, List<String> excludeFieldLists, Boolean checkSuperClassFields) {
        if (obj == null) return true;

        if (KPStringUtil.isEmpty(excludeFieldLists)) excludeFieldLists = new ArrayList<>();
        if (checkSuperClassFields == null) checkSuperClassFields = false;

        try {
            Class<?> clazz = obj.getClass();
            do {
                for (Field field : clazz.getDeclaredFields()) {
                    if (excludeFieldLists.contains(field.getName())) continue;
                    field.setAccessible(true); // 允许访问私有属性
                    Object fieldValue = field.get(obj);
                    if (fieldValue instanceof String && KPStringUtil.isNotEmpty(fieldValue)) {
                        return false;
                    } else if (fieldValue instanceof List && KPStringUtil.isNotEmpty((List) fieldValue)) {
                        return false;
                    } else if (fieldValue instanceof Integer && KPStringUtil.isNotEmpty((Integer) fieldValue)) {
                        return false;
                    }
                }
                clazz = checkSuperClassFields ? clazz.getSuperclass() : null; // 根据参数决定是否继续检查父类
            } while (clazz != null);
        } catch (Exception ex) {
            log.info("[反射获取字段失败！]" + ex.getMessage());
        }
        return true;
    }


    /**
     * 获取对象的getter方法。
     *
     * @param obj 对象
     * @return 对象的getter方法列表
     */

    public static List<Method> getGetterMethods(Object obj) {
        // getter方法列表
        List<Method> getterMethods = new ArrayList<Method>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();

        // 查找getter方法
        for (Method method : methods) {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0)) {
                getterMethods.add(method);
            }
        }
        // 返回getter方法列表
        return getterMethods;
    }


    public static Object getMethod(Object obj, String methodName) {
        try {
            // 获取所有方法
            Method method = obj.getClass().getMethod(methodName);
            // 调用方法并获取返回值
            return method.invoke(obj);
        } catch (Exception ex) {
        }
        return null;
    }


    /**
     * 泛型版反射调用方法：直接返回指定类型，无需手动强转
     * @param obj 调用对象
     * @param methodName 方法名
     * @param clazz 要返回的类型
     * @param <T> 泛型类型
     * @return 对应类型的返回值（失败/类型不匹配返回null）
     */
    public static <T> T getMethod(Object obj, String methodName, Class<T> clazz) {
        try {
            Method method = obj.getClass().getMethod(methodName);
            Object result = method.invoke(obj);
            // 类型匹配才返回，否则返回null（避免类型转换异常）
            return clazz.isInstance(result) ? clazz.cast(result) : null;
        } catch (Exception ex) {
            return null; // 异常时返回null，也可根据需求抛异常
        }
    }

    public static Object getMethod(String packageName, String className, String methodName) {
        try {
            // 通过类名获取Class对象
            Class<?> clazz = Class.forName(packageName + "." + className);
            // 创建类的实例（需要有无参构造方法）
            Object instance = clazz.newInstance();
            // 获取所有方法
            Method method = clazz.getMethod(methodName);
            // 调用方法并获取返回值
            return method.invoke(instance);
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * 通过反射给变量赋值。
     * @author lipeng
     * 2020/9/10
     * @param obj 对象
     * @param propetryName 属性名
     * @param value 值
     */
    public static void setPropetry(Object obj, String propetryName, Object value) {
        try {
            Class c = obj.getClass();
            Field pnanme = c.getDeclaredField(propetryName);
            pnanme.setAccessible(true);
            pnanme.set(obj, value);
        } catch (Exception e) {
            log.info("[反射赋值失败！]" + e.getMessage());
        }
    }

    /**
     * 通过反射给参数赋值。
     * @author lipeng
     * 2020/9/15
     * @param obj 对象
     * @param propetryName 属性名
     * @param value 值
     */
    public static void setPropetryMethod(Object obj, String propetryName, String value) {
        try {
            Class c = obj.getClass();
            Method me = c.getMethod(propetryName, String.class);
            me.invoke(obj, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPropetryMethod(Object obj, String propetryName, Integer value) {
        try {
            Class c = obj.getClass();
            Method me = c.getMethod(propetryName, Integer.class);
            me.invoke(obj, value);
        } catch (Exception e) {
        }
    }

    public static void setPropetryMethod(Object obj, String propetryName, Date value) {
        try {
            Class c = obj.getClass();
            Method me = c.getMethod(propetryName, Date.class);
            me.invoke(obj, value);
        } catch (Exception e) {
        }
    }

    public static void setPropetryMethod(Object obj, String propetryName, boolean value) {
        try {
            Class c = obj.getClass();
            Method me = c.getMethod(propetryName, boolean.class);
            me.invoke(obj, value);
        } catch (Exception e) {
        }
    }

    /**
     * 通过反射给参数赋值。
     * @author lipeng
     * 2020/9/15
     * @param obj 对象
     * @param propetryName 属性名
     * @param value 值
     */
    private static void setPropetryMethod(Object obj, String propetryName, Long value) {
        try {
            Class c = obj.getClass();
            Method me = c.getMethod(propetryName, Long.class);
            Object fhz = me.invoke(obj, value);
        } catch (Exception e) {
        }
    }


    /**
     * 获取对象的setter方法。
     *
     * @param obj 对象
     * @return 对象的setter方法列表
     */
    public static List<Method> getSetterMethods(Object obj) {
        // setter方法列表
        List<Method> setterMethods = new ArrayList<Method>();

        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();

        // 查找setter方法

        for (Method method : methods) {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1)) {
                setterMethods.add(method);
            }
        }
        // 返回setter方法列表
        return setterMethods;
    }
}
