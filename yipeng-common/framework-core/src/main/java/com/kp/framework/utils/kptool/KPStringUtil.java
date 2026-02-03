package com.kp.framework.utils.kptool;

import com.kp.framework.exception.KPServiceException;
import lombok.experimental.UtilityClass;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 字符串操作。
 * @author lipeng
 * 2018年10月17日
 */
@UtilityClass
public final class KPStringUtil {

    /**
     * 字符串是否在字符串数组中。
     * @author lipeng
     * 2018年10月17日
     * @param str 字符串
     * @param arr 字符串数组
     * @return boolean 结果 true 存在  flase 不存在
     */
    public static boolean isContain(String str, String[] arr) {
        for (String string : arr) {
            if (str.indexOf(string) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串首字母大写。
     * @author lipeng
     * 2020/5/18
     * @param str 需要操作的字符串
     * @return java.lang.String
     */
    public static String initialsUpperCase(String str) {
        if (KPStringUtil.isEmpty(str)) return str;
        if (Character.isUpperCase(str.charAt(0)))
            return str;
        return (new StringBuilder())
                .append(Character.toUpperCase(str.charAt(0)))
                .append(str.substring(1)).toString();
    }

    /**
     * 字符串首字母小写。
     * @author lipeng
     * 2021/2/7
     * @param str 需要操作的字符串
     * @return java.lang.String
     */
    public static String initialsLowerCase(String str) {
        if (Character.isLowerCase(str.charAt(0)))
            return str;

        return (new StringBuilder())
                .append(Character.toLowerCase(str.charAt(0)))
                .append(str.substring(1)).toString();
    }

    /**
     * 校验为空。
     * @author lipeng
     * 2020/9/4
     * @param str 需要操作的字符串
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isEmpty(LocalDate str) {
        return str == null;
    }

//    public static boolean isEmpty(List obj) {
//        return obj == null || obj.size() == 0 || obj.isEmpty();
//    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }


    public static boolean isEmpty(Object obj) {
        if (obj == null) return true;
        if (obj instanceof String) return isEmpty((String) obj);
        if (obj instanceof List) return isEmpty((List<?>) obj);
        if (obj instanceof Collection) return isEmpty((Collection<?>) obj);
        if (obj instanceof LocalDate) return isEmpty((LocalDate) obj);
        if (obj instanceof Integer) return isEmpty((Integer) obj);
        if (obj instanceof Long) return isEmpty((Long) obj);
        if (obj instanceof Double) return isEmpty((Double) obj);
        if (obj instanceof Map) return isEmpty((Map<?, ?>) obj);
        if (obj.getClass().isArray()) return java.lang.reflect.Array.getLength(obj) == 0;
        if (obj instanceof CharSequence) return ((CharSequence) obj).length() == 0;
        return false;
    }

    public static boolean isEmpty(Integer value) {
        return value == null;
    }

    public static boolean isEmpty(Double value) {
        return value == null;
    }

    public static boolean isEmpty(Long value) {
        return value == null;
    }

    /**
     * 校验不为空。
     * @author lipeng
     * 2020/9/4
     * @param str 需要操作的字符串
     * @return boolean
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isNotEmpty(Integer value) {
        return !isEmpty(value);
    }

    public static boolean isNotEmpty(LocalDate value) {
        return !isEmpty(value);
    }

    /**
     * 获取当前类和方法名。
     * @author lipeng
     * 2020/9/4
     * @return java.lang.String
     */
    public static String getClassAndMethodName() {
        return Thread.currentThread().getStackTrace()[2].getClassName()
                .concat(".")
                .concat(Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    /**
     * 字符串替换  {num} 占位符 0 開始。
     * @author lipeng
     * 2022/5/10
     * @param format 字符串模板
     * @param args 参数
     * @return java.lang.String
     */
    public static String format(String format, Object... args) {
        try {
            if (format.contains("'")) format = format.replaceAll("'", "''");
            format = MessageFormat.format(format, args);
        } catch (Exception e) {
            for (int i = 0; i < args.length; i++) {
                format = format.replace("{" + i + "}", args[i].toString());
            }
        }
        return format;
    }

    /**
     * 为空格式化。
     * @author lipeng
     * 2024/9/10
     * @param oldStr 旧字符串
     * @param newStr 新字符串
     * @return java.lang.String
     */
    public static String emptyFormat(String oldStr, String newStr) {
        return KPStringUtil.isNotEmpty(oldStr) ? oldStr : newStr;
    }

    /**
     * 为空格式化 并且添加分隔符。
     * @author lipeng
     * 2024/9/10
     * @param oldStr 旧字符串
     * @param newStr 新字符串
     * @param split 分隔符
     * @return java.lang.String
     */
    public static String emptyFormat(String oldStr, String newStr, String split) {
        return KPStringUtil.isNotEmpty(oldStr) ? oldStr + split : newStr;
    }

    /**
     * 获取字符串 没值返回空字符串。
     * @author lipeng
     * 2023/11/5
     * @param txtType 字符串
     * @return java.lang.String
     */
    public static String toString(String txtType) {
        return txtType == null ? "" : txtType.toString();
    }

    /**
     * 获取字符串 没值返回默认值。
     * @author lipeng
     * 2023/11/5
     * @param txtType 字符串
     * @param defaultValue 默认值
     * @return java.lang.String
     */
    public static String toString(String txtType, String defaultValue) {
        return txtType == null ? defaultValue : txtType.toString();
    }

    /**
     * 获取字符串 没值返回默认值 如果有拼接joint。
     * @author lipeng
     * 2024/11/27
     * @param txtType 源字符串
     * @param defaultValue 默认值
     * @param joint 拼接符
     * @return java.lang.String
     */
    public static String toString(String txtType, String defaultValue, String joint) {
        String row = toString(txtType, defaultValue);
        if (row.equals(defaultValue))
            return row;
        return row + joint;
    }

    /**
     * 将汉字转换为拼音。
     * @author lipeng
     * 2024/6/17
     * @param chinese 汉字字符串
     * @return java.lang.String
     */
    public static String convertToPinyin(String chinese) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        List<String> pinyinList = new ArrayList<>();

        char[] charArray = chinese.toCharArray();
        for (char c : charArray) {
            if (Character.isWhitespace(c)) { // 跳过空格等非汉字字符
                continue;
            }
            try {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (pinyinArray != null) {
                    pinyinList.add(pinyinArray[0]);
                }
            } catch (Exception e) {
                throw new KPServiceException("字符 '" + c + "' 无法转换为拼音");
            }
        }

        String body = "";
        for (String pinyinArray : pinyinList) {
//			body += String.join(",", pinyinArray);
            body += pinyinArray;
        }
        return body;
    }

    /**
     * 将汉字转换为拼音首字母。
     * @author lipeng
     * 2024/6/17
     * @param chinese 汉字字符串
     * @return java.lang.String
     */
    public static String convertToPinyinFirstLetter(String chinese) {
        StringBuilder firstLetters = new StringBuilder();
        char[] charArray = chinese.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

        for (char c : charArray) {
            if (Character.isWhitespace(c)) { // 跳过空格等非汉字字符
                firstLetters.append(' ');
                continue;
            }
            try {
                String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (pinyinArray != null && pinyinArray.length > 0) {
                    firstLetters.append(pinyinArray[0].charAt(0));
                }
            } catch (Exception e) {
                throw new KPServiceException("字符 '" + c + "' 无法转换为首字母");
            }
        }
        return firstLetters.toString();
    }
}

