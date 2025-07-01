package com.kunpeng.framework.common.enums;


public enum AuthCodeEnum {

    SUCCESS(200, "操作成功"),

    FAILED(500, "系统内部错误"),
    ACCOUNT_NUMBER_NULL(600, "账号不存在"),
    INVALID(601, "账号或密码错误"),
    REMOVED(602, "账户已注销"),
    LOCK_ACCOUNT(603, "账户已锁定"),
    FORBIDDEN_ACCOUNT(604, "账户已禁用"),
    ABNORMAL_ACCOUNT(605, "账户异常"), //一般是指一个用户名在数据库查询出多条
    NOT_LOGIN(606, "未登录或token已失效"),
    INVALID_TOKEN(607, "无效token"),
    OVERDUE_TOKEN(608, "token已过期"),
    FAILURE_TOKEN(609, "token错误或已过期"),
    ACCOUNT_NUMBER_RAPE(610, "账号在其他地方登录, 您被迫下线"),
    NOT_PERMISSIONS(611, "无操作权限"),
    API_AUTHORIZATION(700, "该授权模式只能访问api对外接口，内部接口禁止访问"); //只能访问api和 open 接口



    AuthCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }



    private String message;

    private Integer code;

    public String message() {
        return this.message;
    }

    public Integer code() {
        return this.code;
    }
}
