package com.kp.framework.utils.kptool;

import com.kp.framework.exception.KPUtilException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

/**
 * <p>
 * 一个增强的 Base64 工具类，提供标准和 URL 安全的 Base64 编码/解码功能，并集成了加盐机制。
 * </p>
 * <p>
 * 该工具类采用在 Base64 编码后的字符串首尾添加随机盐值的方式，
 * 有效防止对固定明文的 Base64 编码进行彩虹表攻击，增加了加密的安全性。
 * </p>
 * @author lipeng
 * @version 2.0
 * @since 2023/10/27
 */
@UtilityClass
@Slf4j
public class KPBase64Utils {

    //用于生成随机盐的字符池。包含了数字、大小写字母和常用特殊字符，以确保盐值的复杂性。
    private static final String SALT_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()_-+=[]{}|;:,.?";

    // 用于生成随机数的 {@link Random} 实例。
    private static final Random RANDOM = new Random();


    /**
     * @Author lipeng
     * @Description 标准 Base64 加密
     * @Date 2023/9/28
     * @param plainText 待编码的明文
     * @return java.lang.String 标准 Base64 编码后的字符串。
     **/
    public static String encode(String plainText) {
        if (KPStringUtil.isEmpty(plainText)) return plainText;
        return Base64.getEncoder().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * @Author lipeng
     * @Description 对url加密
     * 该算法使用 '-' 代替 '+'，使用 '_' 代替 '/'，并且默认不进行填充（即移除末尾的 '='）。
     * @Date 2023/9/28
     * @param plainText 待编码的明文
     * @return java.lang.String  URL 安全的 Base64 编码后的字符串。
     **/
    public static String encodeUrlSafe(String plainText) {
        if (KPStringUtil.isEmpty(plainText)) return plainText;
        return Base64.getUrlEncoder().withoutPadding().encodeToString(plainText.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @Author lipeng
     * @Description 对标准 Base64 编码的字符串进行解码。
     * @Date 2023/9/28
     * @param base64String  标准 Base64 编码的字符串。
     * @return java.lang.String 解码后的明文。
     **/
    public static String decode(String base64String) {
        if (KPStringUtil.isEmpty(base64String)) return base64String;
        try {
            return new String(Base64.getDecoder().decode(base64String), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new KPUtilException("Base64 解码失败，输入字符串格式不正确: " + base64String);
        }
    }

    /**
     * @Author lipeng
     * @Description 对 URL 安全的 Base64 编码的字符串进行解码。
     * @Date 2023/9/28
     * @param urlSafeBase64String URL 安全的 Base64 编码的字符串。
     * @return java.lang.String 解码后的明文
     **/
    public static String decodeUrlSafe(String urlSafeBase64String) {
        if (KPStringUtil.isEmpty(urlSafeBase64String)) return urlSafeBase64String;
        try {
            return new String(Base64.getUrlDecoder().decode(urlSafeBase64String), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new KPUtilException("URL 安全的 Base64 解码失败，输入字符串格式不正确: " + urlSafeBase64String);
        }
    }


    /**
     * @Author lipeng
     * @Description 在一个字符串的开头和结尾添加随机生成的盐。
     * 对 Base64 编码后的字符串使用此方法。
     * @Date 2023/9/28
     * @param input 原始输入字符串（推荐是 Base64 编码后的字符串）。
     * @param saltLength 盐的长度。前缀和后缀的盐将各占此长度。
     * @return java.lang.String 添加了盐的新字符串。
     **/
    public static String addSalt(String input, int saltLength) {
        if (KPStringUtil.isEmpty(input)) return input;
        if (saltLength < 0) saltLength = 10;
        String prefix = generateRandomSalt(saltLength);
        String suffix = generateRandomSalt(saltLength);
        return prefix + input + suffix;
    }


    /**
     * @Author lipeng
     * @Description 移除在字符串开头和结尾添加的盐。
     *  调用时必须提供与 {@link #addSalt(String, int)} 时相同的盐长度。
     * @Date 2023/9/28
     * @param saltedInput 加盐后的字符串。
     * @param saltLength 盐的长度。将从字符串首尾各移除此长度的字符。
     * @return java.lang.String 如果字符串长度小于 2 * saltLength，无法执行去盐操作。
     **/
    public static String removeSalt(String saltedInput, int saltLength) {
        if (KPStringUtil.isEmpty(saltedInput)) return saltedInput;
        if (saltLength < 0) saltLength = 10;

        if (saltedInput.length() < 2L * saltLength) {
            String errorMsg = String.format("字符串长度 (%d) 不足，无法移除指定长度 (%d) 的盐。", saltedInput.length(), saltLength);
            throw new KPUtilException(errorMsg);
        }
        return saltedInput.substring(saltLength, saltedInput.length() - saltLength);
    }


    /**
     * @Author lipeng
     * @Description 生成一个指定长度的随机盐字符串
     * @Date 2023/9/28
     * @param length 期望生成的盐的长度。如果 length <= 0，则返回空字符串。
     * @return java.lang.String 生成的随机盐字符串
     **/
    private static String generateRandomSalt(int length) {
        if (length <= 0) {
            return "";
        }
        // 使用 StringBuilder 效率更高
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            // 从 SALT_CHARACTERS 中随机选取一个字符
            sb.append(SALT_CHARACTERS.charAt(RANDOM.nextInt(SALT_CHARACTERS.length())));
        }
        return sb.toString();
    }



    public static void main(String[] args) {
        String original = "lipeng-lipeng-lipeng-lipeng-lipeng-lipeng-lipeng";
        int saltLen = 10;

        System.out.println("--- 标准 Base64 加解密 + 加盐去盐 示例 ---");
        System.out.println("原始明文: " + original);

        // 加密流程: 明文 -> Base64编码 -> 添加随机盐
        String base64Encoded = KPBase64Utils.encode(original);
        System.out.println("Base64编码后: " + base64Encoded);
        String encrypted = KPBase64Utils.addSalt(base64Encoded, saltLen);
        System.out.println("加盐后最终密文: " + encrypted);

        // 解密流程: 最终密文 -> 移除随机盐 -> Base64解码 -> 明文
        System.out.println("Base64解码后: " + KPBase64Utils.decode(base64Encoded));
        String decryptedBase64 = KPBase64Utils.removeSalt(encrypted, saltLen);
        System.out.println("去盐后Base64: " + decryptedBase64);
        String decrypted = KPBase64Utils.decode(decryptedBase64);
        System.out.println("去盐后解码后明文: " + decrypted);
        System.out.println("是否相等: " + original.equals(decrypted));

        System.out.println("\n--- URL 安全的 Base64 加解密 + 加盐去盐 示例 ---");
        // URL Safe 示例

        original = "https://www.toolhelper.cn/EncodeDecode/Base64";
        String urlSafeBase64Encoded = KPBase64Utils.encodeUrlSafe(original);
        System.out.println("URL安全Base64编码后: " + urlSafeBase64Encoded);
        String urlSafeEncrypted = KPBase64Utils.addSalt(urlSafeBase64Encoded, saltLen);
        System.out.println("URL安全加盐后最终密文: " + urlSafeEncrypted);

        String urlSafeDecryptedBase64 = KPBase64Utils.removeSalt(urlSafeEncrypted, saltLen);
        System.out.println("URL安全去盐后Base64: " + urlSafeDecryptedBase64);
        String urlSafeDecrypted = KPBase64Utils.decodeUrlSafe(urlSafeDecryptedBase64);
        System.out.println("URL安全解码后明文: " + urlSafeDecrypted);
        System.out.println("是否相等: " + original.equals(urlSafeDecrypted));
    }
}