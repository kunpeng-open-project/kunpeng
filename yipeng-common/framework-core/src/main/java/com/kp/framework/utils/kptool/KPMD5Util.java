package com.kp.framework.utils.kptool;

import com.kp.framework.exception.KPServiceException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 
 * @ClassName:  util.KPMD5Util
 * @Description:MD5相关操作  
 * @author: 李鹏 
 * @date:   2018年10月17日 上午8:41:33
 */
public final class KPMD5Util {


	private KPMD5Util(){}
	
	/**
	 * 
	 * @author 李鹏
	 * @Title: md5Encryption   
	 * @Description: 把字符串加成md5格式 
	 * @param: @param str 需要加密的字符串
	 * @param: @param num 需要加密的次数  密码建议最少3次 容易破译
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: String  返回密文 
	 * @throws
	 * @date 2018年10月17日上午8:41:56
	 * @version 1.0.0
	 */
	public static String md5Encryption(String str,int num){
		try {
			int i= 0;
			while (i!= num) {
				str = bytes2Hex(str);
				i++;
			}
			return str;
		}catch (Exception e){
			throw new KPServiceException("md5加密异常");
		}
	}

	

	/**
	 * 
	 * @author 李鹏
	 * @Title: bytes2Hex   
	 * @Description: 把字符串加成md5格式    
	 * @param: @param encrypt 需要加密的字符串
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: String  返回密文
	 * @throws
	 * @date 2018年10月17日上午8:43:05
	 * @version 1.0.0
	 */
	private static String bytes2Hex(String encrypt) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			throw new KPServiceException("md5加密异常！");
		}
		md5.update(encrypt.getBytes()); // 先将字符串转换成 byte 数组，再用 byte 数组更新摘要
		byte[] nStr = md5.digest(); // 哈希计算，即加密

		String des = "";
		String tmp = null;
		for (int i = 0; i < nStr.length; i++) {
			tmp = (Integer.toHexString(nStr[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}


	/**
	 * @Author lipeng
	 * @Description 生成16位数的随机salt值
	 * @Date 2022/10/20 16:55
	 * @param
	 * @return java.lang.String
	 **/
	public static String randomSalt(){
		Random r = new Random();
		StringBuilder sb = new StringBuilder(16);
		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
		int len = sb.length();
		if (len < 16) {
			for (int i = 0; i < 16 - len; i++) {
				sb.append("0");
			}
		}
		String salt = sb.toString();
		return salt;
	}


	/**
	 * @Author lipeng
	 * @Description 加密
	 * @Date 2022/10/20 16:59
	 * @param password
	 * @param salt
	 * @return java.lang.String
	 **/
	public static String encryption(String password,String salt) {
		password = bytes2Hex(password + salt);
		char[] cs = new char[48];
		for (int i = 0; i < 48; i += 3) {
			cs[i] = password.charAt(i / 3 * 2);
			char c = salt.charAt(i / 3);
			cs[i + 1] = c;
			cs[i + 2] = password.charAt(i / 3 * 2 + 1);
		}
		return new String(cs);
	}

	public static void main(String[] args) {
		System.out.println(randomSalt());
	}

//	public static String md5Hex(String src) {
//		try {
//			MessageDigest md5 = MessageDigest.getInstance("MD5");
//			byte[] bs = md5.digest(src.getBytes());
//			new HexBin();
//
//			return new String(HexBin.encode(bs));
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//
//			return null;
//
//		}
//
//	}
}
