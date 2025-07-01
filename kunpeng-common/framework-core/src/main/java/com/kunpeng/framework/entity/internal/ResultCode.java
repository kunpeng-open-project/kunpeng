package com.kunpeng.framework.entity.internal;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2020</p>
 * <p>Company: www.jfzh.com</p>
 *
 * @author Chen Haidong
 * @version 1.0
 * @date 2020/7/15  15:13
 */
public enum ResultCode {

    SUCCESS(200, "操作成功"),

    //400开头的为请求错误
    UNAUTHENTICATION(401, "暂未登录或token已经过期"),

    TOKEN_VERIFICATION_ERROR(402, "token验证失败"),
    UNAUTHORIZATION(403, "无操作权限"),
    BADARGUMENT(405, "参数错误"),
    ACCOUNT_DUPLICATE(406, "该手机号绑定多个账号，请联系管理员！"),
    ACCOUNT_NOTFOUND(407, "您的账号不存在！"),
    ACCOUNT_REGISTER_FAILED(408, "注册失败，请重试！"),
    ACCOUNT_NOTFOUND_PHONE(409, "该手机号不在管理员库中，请联系管理员！"),

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
