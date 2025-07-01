package com.kunpeng.framework.utils.kptool;

import com.kunpeng.framework.exception.KPServiceException;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @ClassName:  util.KPStringUtil
 * @Description: 判断  
 * @author: 李鹏 
 * @date:   2018年10月17日 上午8:45:47
 */
public final class KPStringUtil {

	private KPStringUtil(){}

	
	/**
	 * 
	 * @author 李鹏
	 * @Title: isContain   
	 * @Description: 字符串是否在字符串数组中   
	 * @param: @param str 字符串
	 * @param: @param arr 字符串数组
	 * @param: @return      
	 * @return: boolean  结果 true 存在  flase 不存在  
	 * @throws
	 * @date 2018年10月17日上午8:46:05
	 * @version 1.0.0
	 */
	public static final boolean isContain(String str, String[] arr) {
		for (String string : arr) {
			if(str.indexOf(string)>=0) {
				return true;
			}
		}
		return false;
	}


	/* *
	 * @Author 李鹏
	 * @Description 字符串首字母大写
	 * @Date 2020/5/18 22:14
	 * @Param [str] 需要操作的字符串
	 * @return java.lang.String
	 **/
	public static final String initialsUpperCase(String str){
		if (KPStringUtil.isEmpty(str)) return str;
		if (Character.isUpperCase(str.charAt(0)))
			return str;
		return (new StringBuilder())
				.append(Character.toUpperCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}


	/**
	 * @Author lipeng
	 * @Description 字符串首字母小写
	 * @Date 2021/2/7 17:37
	 * @param str
	 * @return java.lang.String
	 **/
	public static final String initialsLowerCase(String str){
		if (Character.isLowerCase(str.charAt(0)))
			return str;

		return (new StringBuilder())
				.append(Character.toLowerCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

	/**
	 * @Author lipeng
	 * @Description  校验为空
	 * @Date 2020/9/4 16:40
	 * @Param [str]
	 * @return boolean
	 **/
	public static final boolean isEmpty(String str){
		return str == null || str.length() == 0;
	}

	public static final boolean isEmpty(LocalDate str){
		return str == null;
	}

	public static final boolean isEmpty(List obj){
		return obj == null || obj.size() == 0;
	}

	public static final boolean isEmpty(Object obj){
		try {
			return KPStringUtil.isEmpty(obj.toString());
		}catch(Exception ex){}
		return obj == null;
	}

	public static final boolean isEmpty(Integer value){
		return value == null;
	}

	public static final boolean isEmpty(Double value){
		return value == null;
	}

	public static final boolean isEmpty(Long value){
		return value == null;
	}

	/**
	 * @Author lipeng
	 * @Description 校验不为空
	 * @Date 2020/9/4 16:41
	 * @Param [str]
	 * @return boolean
	 **/
	public static final boolean isNotEmpty(String str){
		return !isEmpty(str);
	}

	public static final boolean isNotEmpty(List list){
		return !isEmpty(list);
	}

	public static final boolean isNotEmpty(Object obj){
		return !isEmpty(obj);
	}

	public static final boolean isNotEmpty(Integer value){
		return !isEmpty(value);
	}
	public static final boolean isNotEmpty(LocalDate value){
		return !isEmpty(value);
	}



	/**
	 * @Author lipeng
	 * @Description 获取当前类和方法名
	 * @Date 2020/9/4 16:42
	 * @Param []
	 * @return java.lang.String
	 **/
	public static  final String getClassAndMethodName(){
		return  Thread.currentThread().getStackTrace()[2].getClassName()
				.concat(".")
				.concat(Thread.currentThread().getStackTrace()[2].getMethodName());
	}

	/**
	 * @Author lipeng
	 * @Description 字符串替换  {num} 占位符 0 開始
	 * @Date 2022/5/10 15:04
	 * @param format
	 * @param args
	 * @return java.lang.String
	 **/
	public static final String format(String format, Object... args) {
		try {
			if (format.contains("'")) format = format.replaceAll("'", "''");
			format = MessageFormat.format(format, args);
		}catch (Exception e){
			for (int i = 0; i < args.length; i++) {
				format = format.replace("{"+i+"}", args[i].toString());
			}
		}
		return format;
	}



	/**
	 * @Author lipeng
	 * @Description 为空格式化
	 * @Date 2024/9/10 9:44
	 * @param oldStr
	 * @param newStr
	 * @return java.lang.String
	 **/
	public static final String emptyFormat(String oldStr, String newStr) {
		return KPStringUtil.isNotEmpty(oldStr) ? oldStr : newStr;
	}


	/**
	 * @Author lipeng
	 * @Description 为空格式化 并且添加分隔符
	 * @Date 2024/9/10 10:51
	 * @param oldStr
	 * @param newStr
	 * @param split
	 * @return java.lang.String
	 **/
	public static final String emptyFormat(String oldStr, String newStr, String split) {
		return KPStringUtil.isNotEmpty(oldStr) ? oldStr + split : newStr;
	}


	/**
	 * @Author lipeng
	 * @Description 获取字符串 没值返回空字符串
	 * @Date 2023/11/5 15:25
	 * @param txtType
	 * @return java.lang.String
	 **/
	public static String toString(String txtType) {
		return txtType==null?"":txtType.toString();
	}


	/**
	 * @Author lipeng
	 * @Description 获取字符串 没值返回默认值
	 * @Date 2023/11/5 15:25
	 * @param txtType
	 * @param defaultValue 默认值
	 * @return java.lang.String
	 **/
	public static String toString(String txtType, String defaultValue) {
		return txtType==null?defaultValue:txtType.toString();
	}


	/**
	 * @Author lipeng
	 * @Description 获取字符串 没值返回默认值 如果有拼接joint
	 * @Date 2024/11/27 10:58
	 * @param txtType
	 * @param defaultValue 默认值
	 * @param joint
	 * @return java.lang.String
	 **/
	public static String toString(String txtType, String defaultValue, String joint) {
		String row = toString(txtType, defaultValue);
		if (row.equals(defaultValue))
			return row;
		return row + joint;
	}




	/**
	 * @Author lipeng
	 * @Description 将汉字转换为拼音
	 * @Date 2024/6/17 11:03
	 * @param chinese 汉字字符串
	 * @return 对应的拼音列表，每个汉字可能对应多个读音
	 **/
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
		for (String pinyinArray : pinyinList){
//			body += String.join(",", pinyinArray);
			body += pinyinArray;
		}
		return body;
	}


	/**
	 * @Author lipeng
	 * @Description 将汉字转换为拼音首字母
	 * @Date 2024/6/17 11:04
	 * @param chinese 汉字字符串
	 * @return 对应的拼音首字母列表
	 **/
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

