package com.kp.framework.utils.kptool;

import com.kp.framework.exception.KPServiceException;
import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * MD5相关操作。
 * @author lipeng
 * 2018年10月17日
 */
@UtilityClass
public final class KPMD5Util {


    /**
     * 把字符串加成md5格式。
     * @author lipeng
     * 2018年10月17日
     * @param str 需要加密的字符串
     * @param num 需要加密的次数  密码建议最少3次 容易破译
     * @return java.lang.String 返回密文
     */
    public static String md5Encryption(String str, int num) {
        try {
            int i = 0;
            while (i != num) {
                str = bytes2Hex(str);
                i++;
            }
            return str;
        } catch (Exception e) {
            throw new KPServiceException("md5加密异常");
        }
    }

    /**
     * 把字符串加成md5格式。
     * @author lipeng
     * 2018年10月17日
     * @param encrypt 需要加密的字符串
     * @return java.lang.String
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
     * 生成16位数的随机salt值。
     * @author lipeng
     * 2022/10/20
     * @return java.lang.String
     */
    public static String randomSalt() {
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
     * 加密。
     * @author lipeng
     * 2022/10/20
     * @param password 密码
     * @param salt 盐
     * @return java.lang.String
     */
    public static String encryption(String password, String salt) {
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
