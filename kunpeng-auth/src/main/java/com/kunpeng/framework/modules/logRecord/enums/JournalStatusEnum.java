package com.kunpeng.framework.modules.logRecord.enums;


/**
 * @Author lipeng
 * @Description 日志级别
 * @Date 2024/7/16
 * @return
 **/
public enum JournalStatusEnum {
    NEWEST_JOURNAL(1, "最近日志"),
    HISTORY_JOURNAL(2, "历史日志");
    private String message;

    private Integer code;

    JournalStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }



    public static JournalStatusEnum getCodeValue(Integer code){
        for(JournalStatusEnum value : values()){
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
