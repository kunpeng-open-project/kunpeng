package com.kp.framework.common.enums;

/**
 * 登录用户类型。
 * @author lipeng
 * 2024/4/19
 */
public enum LoginUserTypeEnum {

    COMMON(1, "账号登录"),
    AUTHORIZATION(2, "授权登录"),
    NOT_PASWORD(3, "免密登录"),
    SSO_LOGIN(4, "单点登录");

    private final String message;
    private final Integer code;

    LoginUserTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String message() {
        return this.message;
    }

    public Integer code() {
        return this.code;
    }


    public static LoginUserTypeEnum getCode(Integer code) {
        for (LoginUserTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
