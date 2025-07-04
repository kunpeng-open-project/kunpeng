package com.kunpeng.framework.enums;



/**
 * @Author lipeng
 * @Description 层级
 * @Date 2024/6/14
 * @return
 **/
public enum HierarchyEnum implements IErrorCodeEnum {

    TREE(1,"树型"),
    LIST(2, "列表");

    HierarchyEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }



    private String message;

    private Integer code;

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public Integer code() {
        return this.code;
    }

    public static HierarchyEnum getValue(String message){
        for(HierarchyEnum value : values()){
            if (value.message.equals(message)) {
                return value;
            }
        }
        return null;
    }

    public static HierarchyEnum getValue(Integer code){
        for(HierarchyEnum value : values()){
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
