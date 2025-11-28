package com.kp.framework.utils.kptool;


import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @Author lipeng
 * @Description 校验器  对  Assert.的扩展
 * @Date 2020/9/10
 * @Param
 * @return
 **/
@UtilityClass
public final class KPVerifyUtil {

    //校验ip
    public final static String IP_REGEX = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";

    //校验定时器cron表达式
    public final static String CRON_REGEX = "^\\s*($|#|\\w+\\s*=|(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?(?:,(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?)*)\\s+(\\?|\\*|(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?(?:,(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?)*)\\s+(\\?|\\*|(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?(?:,(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?)*|\\?|\\*|(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?(?:,(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)*)\\s+(\\?|\\*|(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?(?:,(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?)*|\\?|\\*|(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?(?:,(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?)*)(|\\s)+(\\?|\\*|(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?(?:,(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?)*))$";

    //校验手机号
    public final static String PHONE_REGEX = "^[1][0-9]{10}$";
//    public final static String PHONE_REGEX = "^[1][3,4,5,7,8][0-9]{9}$";

    //校验邮箱
    public final static String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    //身份证
    public final static String IDENTITY_CARD = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$";

    //是否包含中文
    public final static String CHINESE = ".*[\\u4e00-\\u9fa5]+.*";


    //---------------------------  非空校验开始   str 校验内容   errMeg 错误信息 -----------------------------------
    public final static void notNull(Object str, String errMeg) {
        if (KPStringUtil.isEmpty(str))
            throw new IllegalArgumentException(errMeg);
    }
    public final static void notNull(String str, String errMeg) {
        if (KPStringUtil.isEmpty(str))
            throw new IllegalArgumentException(errMeg);
    }
    public final static void notNull(Integer num, String errMeg) {
        if (num == null)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void notNull(Long num, String errMeg) {
        if (num == null)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void notNull(Double num, String errMeg) {
        if (num == null)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void notNull(Date date, String errMeg) {
        if (date == null)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void notNull(BigDecimal str, String errMeg) {
        if (str == null)
            throw new IllegalArgumentException(errMeg);

        if (KPStringUtil.isEmpty(str.toString()))
            throw new IllegalArgumentException(errMeg);
    }
    public final static void notNull(List<?> list, String errMeg) {
        if (list == null)
            throw new IllegalArgumentException(errMeg);

        if (list.size() == 0)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void notNull(MultipartFile file) {
        if (file == null)
            throw new IllegalArgumentException("请选择上传的文件！");
        if (file.isEmpty() || file.getSize() == 0)
            throw new IllegalArgumentException("文件为空！");
    }
    //---------------------------  非空校验结束   str 校验内容   errMeg 错误信息 -----------------------------------






    //---------------------------  校验长度开始 str 校验内容  min最小值 max最大值  errMeg 错误信息 -----------------------------------
    public final static void length(String str, Integer min, Integer max, String errMeg) {
        if (KPStringUtil.isEmpty(str) && min == 0) return;
        KPVerifyUtil.notNull(str, errMeg);

        if (str.length() < min || str.length() > max)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void length(Integer num, Integer min, Integer max, String errMeg) {
        KPVerifyUtil.notNull(num, errMeg);

        if (num < min || num > max)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void length(BigDecimal str, Integer min, Integer max, String errMeg) {
        KPVerifyUtil.notNull(str, errMeg);

        if (str.intValue() < min || str.intValue() > max)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void length(Double str, Integer min, Integer max, String errMeg) {
        KPVerifyUtil.notNull(str, errMeg);

        if (str < min || str > max)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void length(Long str, Integer min, Integer max, String errMeg) {
        KPVerifyUtil.notNull(str, errMeg);

        if (str < min || str > max)
            throw new IllegalArgumentException(errMeg);
    }
    //---------------------------  校验长度结束   str 校验内容  min最小值 max最大值  errMeg 错误信息 -----------------------------------





    //---------------------------  最小长度开始   str 校验内容  min最小值 errMeg 错误信息 -----------------------------------
    public final static void minLength(String str, Integer min, String errMeg) {
        if (KPStringUtil.isEmpty(str)) return;

        if (min > str.length())
            throw new IllegalArgumentException(errMeg);
    }
    public final static void minLength(Integer num, Integer min, String errMeg) {
        if (KPStringUtil.isEmpty(num)) return;

        if (min > num)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void minLength(Double num, Integer min, String errMeg) {
        if (KPStringUtil.isEmpty(num)) return;

        if (min > num)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void minLength(Long num, Integer min, String errMeg) {
        if (KPStringUtil.isEmpty(num)) return;

        if (min > num)
            throw new IllegalArgumentException(errMeg);
    }
    //---------------------------  最小长度结束   str 校验内容  min最小值 errMeg 错误信息 -----------------------------------




    //---------------------------  最大长度开始   str 校验内容  max最小值 errMeg 错误信息 -----------------------------------
    public final static void maxLength(String str, Integer max, String errMeg) {
        if (KPStringUtil.isEmpty(str)) return;

        if (str.length() > max)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void maxLength(Integer str, Integer max, String errMeg) {
        if (KPStringUtil.isEmpty(str)) return;

        if (str > max)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void maxLength(Long str, Integer max, String errMeg) {
        if (KPStringUtil.isEmpty(str)) return;

        if (str > max)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void maxLength(Double str, Integer max, String errMeg) {
        if (KPStringUtil.isEmpty(str)) return;

        if (str > max)
            throw new IllegalArgumentException(errMeg);
    }
    //---------------------------  最大长度结束   str 校验内容  max最小值 errMeg 错误信息 -----------------------------------


    //---------------------------  有值就校验长度开始 str 校验内容  min最小值 max最大值  errMeg 错误信息 -----------------------------------
    public final static void lengthIsNot(String str, Integer min, Integer max, String errMeg) {
        if (min == 0 && KPStringUtil.isEmpty(str)) return;
        if (min != 0 && KPStringUtil.isEmpty(str)) throw new IllegalArgumentException(errMeg);

        if (str.length() < min || str.length() > max)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void lengthIsNot(Integer num, Integer min, Integer max, String errMeg) {
        if (KPStringUtil.isEmpty(num)) throw new IllegalArgumentException(errMeg);

        if (num < min || num > max)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void lengthIsNot(BigDecimal str, Integer min, Integer max, String errMeg) {
        if (KPStringUtil.isEmpty(str)) throw new IllegalArgumentException(errMeg);

        if (str.intValue() < min || str.intValue() > max)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void lengthIsNot(Double str, Integer min, Integer max, String errMeg) {
        if (KPStringUtil.isEmpty(str)) throw new IllegalArgumentException(errMeg);

        if (str < min || str > max)
            throw new IllegalArgumentException(errMeg);
    }
    public final static void lengthIsNot(Long str, Integer min, Integer max, String errMeg) {
        if (KPStringUtil.isEmpty(str)) throw new IllegalArgumentException(errMeg);

        if (str < min || str > max)
            throw new IllegalArgumentException(errMeg);
    }
    //---------------------------  校验长度结束   str 校验内容  min最小值 max最大值  errMeg 错误信息 -----------------------------------




    /**
     * @Author lipeng
     * @Description 二选一
     * @Date 2024/6/24
     * @param fileId1 字段
     * @param fileId1Name 字段名称
     * @param fileId2 字段
     * @param fileId2Name 字段名称
     * @param isEmpty 是否允许为空 true 允许 false  最少有一个
     * @return void
     **/
    public static void twoChoiceOne(Object fileId1, String fileId1Name, List<String> fileId2, String fileId2Name, boolean isEmpty) {
        if (isEmpty) {
            if (KPStringUtil.isEmpty(fileId1) && KPStringUtil.isEmpty(fileId2))
                return;
            if (KPStringUtil.isNotEmpty(fileId1) && KPStringUtil.isNotEmpty(fileId2))
                throw new IllegalArgumentException(fileId1Name + "和" + fileId2Name + " 只能选一个！");
        } else {
            if (KPStringUtil.isEmpty(fileId1) && KPStringUtil.isEmpty(fileId2))
                throw new IllegalArgumentException(fileId1Name + "和" + fileId2Name + "最少传一个！");

            if (KPStringUtil.isNotEmpty(fileId1) && KPStringUtil.isNotEmpty(fileId2))
                throw new IllegalArgumentException(fileId1Name + "和" + fileId2Name + " 只能选一个！");
        }
    }
    public static void twoChoiceOne(Object fileId1, String fileId1Name, Object fileId2, String fileId2Name, boolean isEmpty) {
        if (isEmpty) {
            if (KPStringUtil.isEmpty(fileId1) && KPStringUtil.isEmpty(fileId2))
                return;
            if (KPStringUtil.isNotEmpty(fileId1) && KPStringUtil.isNotEmpty(fileId2))
                throw new IllegalArgumentException(fileId1Name + "和" + fileId2Name + " 只能选一个！");
        } else {
            if (KPStringUtil.isEmpty(fileId1) && KPStringUtil.isEmpty(fileId2))
                throw new IllegalArgumentException(fileId1Name + "和" + fileId2Name + "最少传一个！");

            if (KPStringUtil.isNotEmpty(fileId1) && KPStringUtil.isNotEmpty(fileId2))
                throw new IllegalArgumentException(fileId1Name + "和" + fileId2Name + " 只能选一个！");
        }
    }
    public static void twoChoiceOne(List<Object> fileId1, String fileId1Name, List<Object> fileId2, String fileId2Name, boolean isEmpty) {
        if (isEmpty) {
            if (KPStringUtil.isEmpty(fileId1) && KPStringUtil.isEmpty(fileId2))
                return;
            if (KPStringUtil.isNotEmpty(fileId1) && KPStringUtil.isNotEmpty(fileId2))
                throw new IllegalArgumentException(fileId1Name + "和" + fileId2Name + " 只能选一个！");
        } else {
            if (KPStringUtil.isEmpty(fileId1) && KPStringUtil.isEmpty(fileId2))
                throw new IllegalArgumentException(fileId1Name + "和" + fileId2Name + "最少传一个！");

            if (KPStringUtil.isNotEmpty(fileId1) && KPStringUtil.isNotEmpty(fileId2))
                throw new IllegalArgumentException(fileId1Name + "和" + fileId2Name + " 只能选一个！");
        }
    }


    /**
     * @param errMeg
     * @param str
     * @return void
     * @Author lipeng
     * @Description 校验多个参数必穿一个
     * @Date 2023/10/23
     **/
    public final static void notNullByMultipleChoice(String errMeg, String... str) {
        boolean flag = false;
        for (int i = 0; i < str.length; i++) {
            if (KPStringUtil.isNotEmpty(str[i])) {
                flag = true;
                break;
            }
        }
        if (!flag)
            throw new IllegalArgumentException(errMeg);
    }


    /**
     * @return void
     * @Author lipeng
     * @Description 根据规则校验  不满足规则
     * @Date 2020/9/10
     * @Param [str, Regex, errMeg]
     **/
    public final static void matchesRuleMistake(String str, String Regex, String errMeg) {
        KPVerifyUtil.notNull(str, errMeg);
        Pattern pattern = Pattern.compile(Regex);
        if (pattern.matcher(str).matches())
            throw new IllegalArgumentException(errMeg);
    }


    /**
     * @param str
     * @param Regex
     * @param errMeg
     * @return void
     * @Author lipeng
     * @Description 根据规则校验  满足规则
     * @Date 2023/12/21
     **/
    public final static void matchesRule(String str, String Regex, String errMeg) {
        KPVerifyUtil.notNull(str, errMeg);
        Pattern pattern = Pattern.compile(Regex);
        if (!pattern.matcher(str).matches())
            throw new IllegalArgumentException(errMeg);
    }



    /**
     * @return void
     * @Author lipeng
     * @Description 出现的次数
     * @Date 2020/10/9 16:19
     * @Param [source, target, min, max, errMeg]
     **/
    public final static void frequency(String source, String target, Integer min, Integer max, String errMeg) {
        Integer start = source.length();
        Integer end = source.replaceAll(target, "").length();
        //出现次数
        Integer num = (start - end) / target.length();
        if (num < min || num > max)
            throw new IllegalArgumentException(errMeg);
    }


    /**
     * @param source 经验的字符串
     * @param str    指定字符串
     * @param maxNum 最大位数
     * @param errMeg 异常提醒
     * @return void
     * @Author lipeng
     * @Description 以str后的最大长度
     * @Date 2021/4/20 14:09
     **/
    public static void lengthByString(String source, String str, Integer maxNum, String errMeg) {
        if (source.indexOf(str) == -1) return;

        String newStr = source.substring(source.indexOf(str) + 1, source.length());
        if (newStr.length() > maxNum)
            throw new IllegalArgumentException(errMeg);
    }



    /**
     * @Author lipeng
     * @Description 校验邮箱
     * @Date 2025/1/2 17:44
     * @param email 邮箱
     * @param errMeg 错误提示
     * @param IsNull 是否可以为空
     * @return void
     **/
    public static void email(String email, String errMeg, Boolean IsNull) {
        if (IsNull && KPStringUtil.isEmpty(email)) {
            return;
        }else{
            KPVerifyUtil.notNull(email, errMeg);
        }
        KPVerifyUtil.matchesRule(email, KPVerifyUtil.EMAIL_REGEX, errMeg);
    }



}
