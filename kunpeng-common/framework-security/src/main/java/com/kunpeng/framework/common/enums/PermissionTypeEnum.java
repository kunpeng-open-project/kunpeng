package com.kunpeng.framework.common.enums;


/**
 * @Author lipeng
 * @Description 数据权限类型
 * @Date 2024/4/26 11:12
 * @return
 **/
public enum PermissionTypeEnum {
    ONESELF(1, "仅本人数据权限"),
    CUSTOM_USER(2, "自定义用户数据"),
    ONESELF_DEPARTMENT(3, "本部门权限"),
    ONESELF_DEPARTMENT_UNDER(4, "本部门及以下权限"),
    CUSTOM(5, "自定义数据权限"),
    ALL(6, "全部数据权限");

    private String message;

    private Integer code;

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
        return null;
    }

    public static PermissionTypeEnum getCode(Integer code){
        for(PermissionTypeEnum value : values()){
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public String message() {
        return this.message;
    }

    public Integer code() {
        return this.code;
    }

}
