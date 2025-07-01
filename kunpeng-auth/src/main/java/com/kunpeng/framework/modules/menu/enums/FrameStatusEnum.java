package com.kunpeng.framework.modules.menu.enums;


/**
 * @Author lipeng
 * @Description 菜单类型
 * @Date 2024/4/25
 * @return
 **/
public enum FrameStatusEnum {
    INSIDE(1, "内部"),
    OUTER_CHAIN_EMBEDDED(2, "外链内嵌"),
    OUTER_CHAIN(3, "外链");
    private String message;

    private Integer code;

    FrameStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }



    public static FrameStatusEnum getCodeValue(Integer code){
        for(FrameStatusEnum value : values()){
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
