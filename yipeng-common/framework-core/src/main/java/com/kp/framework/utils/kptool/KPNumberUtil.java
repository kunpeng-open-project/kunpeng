package com.kp.framework.utils.kptool;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对数值的操作。
 * @author lipeng
 * 2018年10月17日
 */
@UtilityClass
public final class KPNumberUtil {

    /**
     * 获取随机数。
     * @author lipeng
     * 2026/1/21
     * @param start 开始数
     * @param end 结束数
     * @return int 返回随机数
     */
    public int rod(int start, int end) {
        return (int) (Math.random() * (end - start + 1)) + start;
    }

    /**
     * 随机生成指定位数的随机数。
     * @author lipeng
     * 2024/8/2
     * @param length  长度
     * @param pattern  是否以时间开头 如果以时间开头 输入格式  YYYYmmddhh...
     * @return java.lang.String
     */
    public String random(int length, String pattern) {
        String row = "";
        if (KPStringUtil.isNotEmpty(pattern))
            row = KPDateUtil.format(new Date(), pattern);
        while (row.length() <= length) {
            row += String.valueOf(rod(0, 9));
        }
        return row;
    }

    /**
     * 查询字符串中的数字。
     * @author lipeng
     * 2024/2/23
     * @param str 要查询的字符
     * @return java.util.List<java.lang.Integer>
     */
    public List<Integer> queryNumber(String str) {
        StringBuilder digitString = new StringBuilder();
        List<Integer> numbers = new ArrayList<>();
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                digitString.append(c);
            } else if (digitString.length() > 0) {
                // 当遇到非数字字符且StringBuilder中有数字时，输出当前的数字字符串并清空StringBuilder
                Integer num = Integer.valueOf(String.valueOf(digitString));
                numbers.add(num);
                digitString.setLength(0);
            }
        }

        // 检查最后是否遗留了未打印的数字（例如，字符串以数字结尾）
        if (digitString.length() > 0) {
            Integer num = Integer.valueOf(String.valueOf(digitString));
            numbers.add(num);
        }
        return numbers;
    }

    public Map<Integer, String> queryNumberSplit(String str) {
        List<Integer> numbers = queryNumber(str);
        if (numbers.size() == 0) return null;

        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < numbers.size(); i++) {
            try {
                map.put(numbers.get(i), str.substring(str.indexOf(numbers.get(i).toString()) + numbers.get(i).toString().length(), str.indexOf(numbers.get(i + 1).toString()) - 1));
            } catch (Exception ex) {
                map.put(numbers.get(i), str.substring(str.indexOf(numbers.get(i).toString()) + numbers.get(i).toString().length(), str.length()));
            }
        }
        //数据清洗
        map.forEach((key, value) -> {
            if (value.startsWith("0"))
                value = value.replaceFirst("^0+", "");
            if (value.startsWith("-"))
                value = value.replaceFirst("^-+", "");
            if (value.startsWith(","))
                value = value.replaceFirst("^,+", "");
            if (value.startsWith("，"))
                value = value.replaceFirst("^，+", "");
            map.put(key, value);
        });
        return map;
    }

    /**
     * 生成唯一编号  通过redis 保证唯一。
     * @author lipeng
     * 2025/11/2
     * @param project 项目名称  一般是RedisSecurityConstant 或者  RedisConstant 里面的内容
     * @param prefix 前缀
     * @return java.lang.String
     */
    public String createNumber(String project, String prefix) {
        String datePart = KPDateUtil.format(new Date(), "yyyyMMddHH");
        String number = prefix + "-" + datePart + "-" + KPNumberUtil.rod(0, 9999);

        //24 小时过期
        if (KPRedisUtil.lock(project + "createNumber:" + number, 86400)) return number;
        return createNumber(project, prefix);
    }
}
