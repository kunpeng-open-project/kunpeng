package com.kp.framework.common.enums;

/**
 * 数据权限类型。
 * @author lipeng
 * 2024/4/26
 */
public enum PermissionTypeEnum {
    ONESELF(1, "仅本人数据权限"),
    CUSTOM_USER(2, "自定义用户数据"),
    ONESELF_DEPARTMENT(3, "本部门权限"),
    ONESELF_DEPARTMENT_UNDER(4, "本部门及以下权限"),
    CUSTOM(5, "自定义数据权限"),
    ALL(6, "全部数据权限"),
    UNKNOWN(0, "未知");

    private final String message;
    private final Integer code;

    PermissionTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static PermissionTypeEnum getValue(String message){
        for(PermissionTypeEnum value : values()){
            if (value.message.equals(message)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public static PermissionTypeEnum getCode(Integer code){
        for(PermissionTypeEnum value : values()){
            if (value.code.equals(code)) {
                return value;
            }
        }
        return UNKNOWN;
    }

    public String message() {
        return this.message;
    }

    public Integer code() {
        return this.code;
    }
}
