package com.kp.framework.modules.user.enums;

/**
 * @Author lipeng
 * @Description 用户状态
 * @Date 2024/9/10
 * @return
 **/
public enum UserStatusEnum {
    INTERNSHIP(1, "实习"),
    FORMAL(2, "转正"),
    QUIT(3, "离职");


    private String message;

    private Integer code;

    UserStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String message() {
        return this.message;
    }

    public Integer code() {
        return this.code;
    }

    public static UserStatusEnum getCode(Integer code){
        for(UserStatusEnum value : values()){
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
