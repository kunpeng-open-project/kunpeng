package com.kp.framework.entity.internal;

/**
 * @Author lipeng
 * @Description 系统返回码
 * @Date 2020/7/15
 * @return
 **/
public enum ResultCode {

    SUCCESS(200, "操作成功"),

    //500开头的是系统处理错误
    FAILED(500, "系统内部错误"),
    // 需要友好提示用户，比如验证码错误，密码错误等提示
    FriendlyError(501, "业务验证错误，需要友好提示"),

    INTERFACE_VISIT_ERROR(503, "接口调用频繁"),

    TEMPORARY_FORBID_VISIT(504, "系统检查到您频繁调用该接口，已临时禁止访问"),

    PERPETUAL_FORBID_VISIT(505, "系统检查到您可能在爬取数据，已永久禁止访问");

    private Integer code;

    private String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return code;
    }

    public String message() {
        return message;
    }
}
