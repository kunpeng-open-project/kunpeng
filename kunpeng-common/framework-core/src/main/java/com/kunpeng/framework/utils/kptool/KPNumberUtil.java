package com.kunpeng.framework.utils.kptool;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName:  util.KPNumberUtil
 * @Description:对数值的操作 
 * @author: 李鹏 
 * @date:   2018年10月17日 上午8:44:05
 */
public final class KPNumberUtil {


	private KPNumberUtil(){}

	/**
	 * 
	 * @author 李鹏
	 * @Title: rod   
	 * @Description: 获取随机数 
	 * @param: @param start 开始数
	 * @param: @param end 结束数
	 * @param: @return      
	 * @return: int 返回随机数
	 * @throws
	 * @date 2018年10月17日上午8:44:23
	 * @version 1.0.0
	 */
	public static final int rod(int start, int end){
		return  (int)(Math.random() * (end-start+1)) + start;
	}


	/**
	 * @Author lipeng
	 * @Description 随机生成指定位数的随机数
	 * @Date 2024/8/2 10:24
	 * @param length  长度
	 * @param pattern  是否以时间开头 如果以时间开头 输入格式  YYYYmmddhh...
	 * @return java.lang.String
	 **/
	public static final String random(int length, String pattern){
		String row = "";
		if (KPStringUtil.isNotEmpty(pattern))
			row = KPDateUtil.dateFormat(new Date(), pattern);
		while (row.length() <= length){
			row += String.valueOf(rod(0, 9));
		}
		return row;
	}

	/**
	 * @Author lipeng
	 * @Description 查询字符串中的数字
	 * @Date 2024/2/23 11:41
	 * @param str 要查询的字符
	 * @return java.lang.Integer
	 **/
	public static List<Integer> queryNumber(String str){
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


	public static Map<Integer, String> queryNumberSplit(String str) {
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
}
