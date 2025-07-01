package com.kunpeng.framework.enums;


/**
 * @Author lipeng
 * @Description 是否
 * @Date 2021/10/13 16:02
 * @return
 **/
public enum YesNoEnum implements IErrorCodeEnum {

//    在大多数编程语言中，1 通常表示 true，而 0 表示 false。
    YES(1,"是"),
    NO(0, "否");

    YesNoEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }



    private String message;

    private Integer code;

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public Integer code() {
        return this.code;
    }

    public static YesNoEnum getValue(String message){
        for(YesNoEnum value : values()){
            if (value.message.equals(message)) {
                return value;
            }
        }
        return null;
    }

    public static YesNoEnum getValue(Integer code){
        for(YesNoEnum value : values()){
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
