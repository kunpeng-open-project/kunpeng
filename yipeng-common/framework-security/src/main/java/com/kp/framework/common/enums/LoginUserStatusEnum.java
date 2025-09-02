package com.kp.framework.common.enums;

/**
 * @Author lipeng
 * @Description 登录用户类型
 * @Date 2024/4/19
 * @return
 **/
public enum LoginUserStatusEnum{

    NORMAL(1, "正常"),
    FORBIDDEN(2, "禁用"),
    LOCK(3, "锁定"),
    LOGOUT(4, "注销");
    private String message;

    private Integer code;

    LoginUserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String message() {
        return this.message;
    }

    public Integer code() {
        return this.code;
    }

    public static LoginUserStatusEnum getCode(Integer code){
        for(LoginUserStatusEnum value : values()){
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
