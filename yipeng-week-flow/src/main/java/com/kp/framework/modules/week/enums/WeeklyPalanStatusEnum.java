package com.kp.framework.modules.week.enums;

/**
 * 月计划状态。
 * @author lipeng
 * 2025/8/30
 */
public enum WeeklyPalanStatusEnum {
    NOT_STARTED(1, "未开始"),
    IN_PROGRESS(2, "进行中"),
    COMPLETED(3, "已完成"),
    DISCARD(4, "已废弃");

    private final String message;
    private final Integer code;

    WeeklyPalanStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String message() {
        return this.message;
    }

    public Integer code() {
        return this.code;
    }

    public static WeeklyPalanStatusEnum getCode(Integer code) {
        for (WeeklyPalanStatusEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
