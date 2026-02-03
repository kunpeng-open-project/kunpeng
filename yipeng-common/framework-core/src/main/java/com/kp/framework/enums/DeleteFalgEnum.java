package com.kp.framework.enums;


/**
 * 删除状态。
 * @author lipeng
 * 2021/10/13
 */
public enum DeleteFalgEnum {

    NORMAL(0, "正常"),
    DELETE(1, "删除");

    DeleteFalgEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    private String message;

    private Integer code;


    public String message() {
        return this.message;
    }

    public Integer code() {
        return this.code;
    }

    public static DeleteFalgEnum getValue(String message) {
        for (DeleteFalgEnum value : values()) {
            if (value.message.equals(message)) {
                return value;
            }
        }
        return null;
    }

    public static DeleteFalgEnum getValue(Integer code) {
        for (DeleteFalgEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
