package com.kunpeng.framework.utils.kptool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class KPIDCardUtil {
    /**
     *
     *
     * @return
     * @throws Exception
     */

    /**
     * @Author lipeng
     * @Description 根据身份证获取年龄
     * @Date 2023/12/8 16:43
     * @param idCard
     * @return java.lang.Integer
     **/
    public static Integer getUserAge(String idCard){
        if (KPStringUtil.isEmpty(idCard)) return null;
        Pattern pattern = Pattern.compile(KPVerifyUtil.IDENTITY_CARD);
        if(!pattern.matcher(idCard).matches()) return null;

        try {
            String year = idCard.substring(6).substring(0, 4);// 得到年份
            String yue = idCard.substring(10).substring(0, 2);// 得到月份
            Date date = new Date();// 得到当前的系统时间
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String fyear = format.format(date).substring(0, 4);// 当前年份
            String fyue = format.format(date).substring(5, 7);// 月份
            int age = 0;
            if (Integer.parseInt(yue) <= Integer.parseInt(fyue)) { // 当前月份大于用户出身的月份表示已过生
                age = Integer.parseInt(fyear) - Integer.parseInt(year) + 1;
            } else {// 当前用户还没过生
                age = Integer.parseInt(fyear) - Integer.parseInt(year);
            }
            return age;
        }catch (Exception ex){}
        return null;
    }


    /**
     * @Author lipeng
     * @Description 根据身份证获取性别
     * @Date 2023/12/8 16:46
     * @param realName
     * @param idCard
     * @return java.lang.String
     **/
    public static String getUserSex(String realName,String idCard) {
        if (KPStringUtil.isEmpty(idCard)) return null;
        Pattern pattern = Pattern.compile(KPVerifyUtil.IDENTITY_CARD);
        if(!pattern.matcher(idCard).matches()) return null;

        String topName = realName.substring(0, 1);
        String sexStr = "0";
        if (idCard.length() == 15) {
            sexStr = idCard.substring(14, 15);
        } else if (idCard.length() == 18) {
            sexStr = idCard.substring(16,17);
        }
        int sexNo = Integer.parseInt(sexStr);
        return sexNo % 2 == 0 ? topName+"女士" : topName+"先生";
    }

    public static String getUserSex(String idCard) {
        if (KPStringUtil.isEmpty(idCard)) return null;
        Pattern pattern = Pattern.compile(KPVerifyUtil.IDENTITY_CARD);
        if(!pattern.matcher(idCard).matches()) return null;

        String sexStr = "0";
        if (idCard.length() == 15) {
            sexStr = idCard.substring(14, 15);
        } else if (idCard.length() == 18) {
            sexStr = idCard.substring(16,17);
        }
        int sexNo = Integer.parseInt(sexStr);
        return sexNo % 2 == 0 ? "女士" : "先生";
    }
}
