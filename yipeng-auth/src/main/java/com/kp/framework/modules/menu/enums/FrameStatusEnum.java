package com.kp.framework.modules.menu.enums;

/**
 * 菜单类型。
 * @author lipeng
 * 2024/4/25
 */
public enum FrameStatusEnum {
    INSIDE(1, "内部"),
    OUTER_CHAIN_EMBEDDED(2, "外链内嵌"),
    OUTER_CHAIN(3, "外链"),
    UNKNOWN(0, "未知");

    private final String message;
    private final Integer code;

    FrameStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public static FrameStatusEnum getCodeValue(Integer code) {
        if (code == null) return UNKNOWN;
        for (FrameStatusEnum value : values()) {
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
