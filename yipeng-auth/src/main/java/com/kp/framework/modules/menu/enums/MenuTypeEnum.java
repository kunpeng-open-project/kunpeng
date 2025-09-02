package com.kp.framework.modules.menu.enums;


/**
 * @Author lipeng
 * @Description 菜单类型
 * @Date 2024/4/25
 * @return
 **/
public enum MenuTypeEnum {

    CATALOGUE("M", "目录"),
    MENU("C", "菜单"),
    BUTTON("B", "按钮"),
    INTERFACE("I", "接口");
    private String message;

    private String code;

    MenuTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static MenuTypeEnum getValue(String message){
        for(MenuTypeEnum value : values()){
            if (value.message.equals(message)) {
                return value;
            }
        }
        return null;
    }

    public static MenuTypeEnum getCodeValue(String code){
        for(MenuTypeEnum value : values()){
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public String message() {
        return this.message;
    }

    public String code() {
        return this.code;
    }

}
