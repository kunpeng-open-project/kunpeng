package com.kp.framework.utils.kptool;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author lipeng
 * @Description BigDecimal
 * @Date 2021/2/19
 * @return
 **/
public final class KPBigDecimalUtils {

    private BigDecimal bigDecimal = null;

    //取整类型
    public static final int ROUND_UP = BigDecimal.ROUND_UP;//向上取整
    public static final int ROUND_DOWN = BigDecimal.ROUND_DOWN;//向下取整
    public static final int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;//四舍五入



    public KPBigDecimalUtils(BigDecimal value) {
        bigDecimal = value;
    }

    public KPBigDecimalUtils(Object value) {
        bigDecimal = new BigDecimal(value.toString());
    }


    /**
     * @Author lipeng
     * @Description 加
     * @Date 2022/6/7
     * @param value
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils add(BigDecimal value) {
        this.bigDecimal = this.bigDecimal.add(value);
        return this;
    }

    /**
     * @Author lipeng
     * @Description 加
     * @Date 2022/6/7 15:51
     * @param value
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils add(Object value) {
        this.bigDecimal = this.bigDecimal.add(new BigDecimal(value.toString()));
        return this;
    }

    /**
     * @Author lipeng
     * @Description 提供精确的加法运算(默认四舍五入，根据scale保留小数位数)
     * @Date 2022/6/7 16:03
     * @param value
     * @param scale 保留小数位数
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils add(BigDecimal value, int scale) {
        this.bigDecimal = this.bigDecimal.add(value).setScale(scale, KPBigDecimalUtils.ROUND_HALF_UP);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的加法运算(默认四舍五入，根据scale保留小数位数)
     * @Date 2022/6/7 16:04
     * @param value
     * @param scale 保留小数位数
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils add(Object value, int scale) {
        this.bigDecimal = this.bigDecimal.add(new BigDecimal(value.toString())).setScale(scale, KPBigDecimalUtils.ROUND_HALF_UP);
        return this;
    }




    /**
     * @Author lipeng
     * @Description 提供精确的加法运算
     * @Date 2022/6/7 16:15
     * @param value
     * @param scale 保留小数位数
     * @param roundingMode 取整类型
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils add(BigDecimal value, int scale, RoundingMode roundingMode) {
        this.bigDecimal = this.bigDecimal.add(value).setScale(scale, roundingMode);
        return this;
    }

    /**
     * @Author lipeng
     * @Description 提供精确的加法运算
     * @Date 2022/6/7 16:15
     * @param value
     * @param scale 保留小数位数
     * @param roundingMode 取整类型
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils add(Object value, int scale, RoundingMode roundingMode) {
        this.bigDecimal = this.bigDecimal.add(new BigDecimal(value.toString())).setScale(scale, roundingMode);
        return this;
    }


    /**
     * @Author lipeng
     * @Description  减法
     * @Date 2022/6/7 16:18
     * @param value
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils sub(BigDecimal value) {
        this.bigDecimal = this.bigDecimal.subtract(value);
        return this;
    }


    /**
     * @Author lipeng
     * @Description  减法
     * @Date 2022/6/7 16:18
     * @param value
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils sub(Object value) {
        this.bigDecimal = this.bigDecimal.subtract(new BigDecimal(value.toString()));
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的减法运算(默认四舍五入，根据scale保留小数位数)
     * @Date 2022/6/7 16:27
     * @param value
     * @param scale 保留小数位数
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils sub(BigDecimal value, int scale) {
        this.bigDecimal = this.bigDecimal.subtract(value).setScale(scale, KPBigDecimalUtils.ROUND_HALF_UP);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的减法运算(默认四舍五入，根据scale保留小数位数)
     * @Date 2022/6/7 16:27
     * @param value
     * @param scale 保留小数位数
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils sub(Object value, int scale) {
        this.bigDecimal = this.bigDecimal.subtract(new BigDecimal(value.toString())).setScale(scale, KPBigDecimalUtils.ROUND_HALF_UP);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的减法运算
     * @Date 2022/6/7 16:15
     * @param value
     * @param scale 保留小数位数
     * @param roundingMode 取整类型
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils sub(BigDecimal value, int scale, RoundingMode roundingMode) {
        this.bigDecimal = this.bigDecimal.subtract(value).setScale(scale, roundingMode);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的减法运算
     * @Date 2022/6/7 16:15
     * @param value
     * @param scale 保留小数位数
     * @param roundingMode 取整类型
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils sub(Object value, int scale, RoundingMode roundingMode) {
        this.bigDecimal = this.bigDecimal.subtract(new BigDecimal(value.toString())).setScale(scale, roundingMode);
        return this;
    }



    /**
     * @Author lipeng
     * @Description 乘法
     * @Date 2022/6/7 16:57
     * @param value
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils multiply(BigDecimal value) {
        this.bigDecimal = this.bigDecimal.multiply(value);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 乘法
     * @Date 2022/6/7 16:57
     * @param value
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils multiply(Object value) {
        this.bigDecimal = this.bigDecimal.multiply(new BigDecimal(value.toString()));
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的乘法运算(默认四舍五入，根据scale保留小数位数)
     * @Date 2022/6/7 16:27
     * @param value
     * @param scale 保留小数位数
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils multiply(BigDecimal value, int scale) {
        this.bigDecimal = this.bigDecimal.multiply(value).setScale(scale, KPBigDecimalUtils.ROUND_HALF_UP);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的乘法运算(默认四舍五入，根据scale保留小数位数)
     * @Date 2022/6/7 16:27
     * @param value
     * @param scale 保留小数位数
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils multiply(Object value, int scale) {
        this.bigDecimal = this.bigDecimal.multiply(new BigDecimal(value.toString())).setScale(scale, KPBigDecimalUtils.ROUND_HALF_UP);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的乘法运算
     * @Date 2022/6/7 16:15
     * @param value
     * @param scale 保留小数位数
     * @param roundingMode 取整类型
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils multiply(BigDecimal value, int scale, RoundingMode roundingMode) {
        this.bigDecimal = this.bigDecimal.multiply(value).setScale(scale, roundingMode);
        return this;
    }

    /**
     * @Author lipeng
     * @Description 提供精确的乘法运算
     * @Date 2022/6/7 16:15
     * @param value
     * @param scale 保留小数位数
     * @param roundingMode 取整类型
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    @Deprecated
    public KPBigDecimalUtils multiply(Object value, int scale, RoundingMode roundingMode) {
        this.bigDecimal = this.bigDecimal.multiply(new BigDecimal(value.toString())).setScale(scale, roundingMode);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的除法运算(默认四舍五入，根据scale保留小数位数)
     * @Date 2022/6/7 16:27
     * @param value
     * @param scale 保留小数位数
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils divide(Object value, int scale) {
        this.bigDecimal = this.bigDecimal.divide(new BigDecimal(value.toString()), scale, KPBigDecimalUtils.ROUND_HALF_UP);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的除法运算(默认四舍五入，根据scale保留小数位数)
     * @Date 2022/6/7 16:27
     * @param value
     * @param scale 保留小数位数
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils divide(BigDecimal value, int scale) {
        this.bigDecimal = this.bigDecimal.divide(value, scale, KPBigDecimalUtils.ROUND_HALF_UP);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的除法运算
     * @Date 2022/6/7 16:15
     * @param value
     * @param scale 保留小数位数
     * @param roundingMode 取整类型
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils divide(BigDecimal value, int scale, RoundingMode roundingMode) {
        this.bigDecimal = this.bigDecimal.divide(value, scale, roundingMode);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 提供精确的除法运算
     * @Date 2022/6/7 16:15
     * @param value
     * @param scale 保留小数位数
     * @param roundingMode 取整类型
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils divide(Object value, int scale, RoundingMode roundingMode) {
        this.bigDecimal = this.bigDecimal.divide(new BigDecimal(value.toString()), scale, roundingMode);
        return this;
    }


    /**
     * @Author lipeng
     * @Description 取余
     * @Date 2022/6/7 17:29
     * @param value
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils balance(BigDecimal value) {
        this.bigDecimal = this.bigDecimal.remainder(value);
        return this;
    }

    /**
     * @Author lipeng
     * @Description 取余
     * @Date 2022/6/7 17:29
     * @param value
     * @return com.daoben.framework.util.KPBigDecimalUtils
     **/
    public KPBigDecimalUtils balance(Object value) {
        this.bigDecimal = this.bigDecimal.remainder(new BigDecimal(value.toString()));
        return this;
    }


    /**
     * @Author lipeng
     * @Description 比较BigDecimal,相等返回0,num>num1返回1,num<num1返回-1
     * @Date 2022/6/7 17:37
     * @param val1
     * @param val2
     * @return int
     **/
    public static int compareTo(BigDecimal val1, BigDecimal val2) {
        return val1.compareTo(val2);
    }


    public BigDecimal build() {
        return this.bigDecimal;
    }

    /**
     * @Author lipeng
     * @Description
     * @Date 2022/6/7 18:00
     * @param scale 保留位数
     * @param roundingMode 取整类型
     * @return java.math.BigDecimal
     **/
    public BigDecimal build(int scale, RoundingMode roundingMode) {
        return this.bigDecimal.setScale(scale, roundingMode);
    }

    public String buildString() {
        return this.build().toString();
    }

    public String buildString(String symbol) {
        return this.buildString() + symbol;
    }

    public String buildString(int scale, RoundingMode roundingMode) {
//        RoundingMode.UP: 向正无穷方向舍位
//        RoundingMode.DOWN: 向零方向舍位
//        RoundingMode.CEILING: 大于等于该数的最小整数
//        RoundingMode.FLOOR: 小于等于该数的最大整数
//        RoundingMode.HALF_UP: 四舍五入
//        RoundingMode.HALF_DOWN: 五舍六入
//        RoundingMode.HALF_EVEN: 当小数位为.5时，舍入成最接近的偶数
//        RoundingMode.UNNECESSARY:无需使用舍入模式
        return this.build(scale, roundingMode).toString();
    }

    public String buildString(int scale, RoundingMode roundingMode, String symbol) {
        return this.buildString(scale, roundingMode) + symbol;
    }

//    public static void main(String[] args) {
//        System.out.println(new BigDecimal("1.9").setScale(0, ));
//    }

    public Integer buildInteger() {
        return Integer.valueOf(this.buildString());
    }

    public Integer buildInteger(RoundingMode roundingMode) {
        return Integer.valueOf(this.buildString(0, roundingMode));
    }

    public Long buildLong() {
        return Long.valueOf(this.buildString());
    }

    public Long buildLong(RoundingMode roundingMode) {
        return Long.valueOf(this.buildString(0, roundingMode));
    }

    public Double buildDouble() {
        return Double.valueOf(this.buildString());
    }

    public Double buildDouble(int scale, RoundingMode roundingMode) {
        return Double.valueOf(this.buildString(scale, roundingMode));
    }

    public Float buildFloat() {
        return Float.valueOf(this.buildString());
    }

    public Float buildFloat(int scale, RoundingMode roundingMode) {
        return Float.valueOf(this.buildString(scale, roundingMode));
    }




//    public static void main(String[] args) {
//        System.out.println(new KPBigDecimalUtils(2).add(3).multiply(6.1).sub(12.51212).build(2, KPBigDecimalUtils.ROUND_HALF_UP));
//    }





//    //建立货币格式化引用
//    private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
//
//    //建立百分比格式化引用
//    private static final NumberFormat percent = NumberFormat.getPercentInstance();
//


//
//    /**
//     * BigDecimal货币格式化
//     * @param money
//     * @return
//     */
//    public static String currencyFormat(BigDecimal money) {
//        return currency.format(money);
//    }
//
//
//    /**
//     * BigDecimal百分比格式化
//     * @param rate
//     * @return
//     */
//    public static String rateFormat(BigDecimal rate) {
//        return percent.format(rate);
//    }


}


