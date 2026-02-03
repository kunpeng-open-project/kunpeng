package com.kp.framework.entity.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 操作用户信息。
 * @author lipeng
 * 2024/2/4
 */
@Data
@Accessors(chain = true)
public class OperationUserMessageBO {

    //如果是账号密码登录 就是用户id  如果是授权登录 是 项目code
    private String id;

    //用户真实姓名 或者 项目名称
    private String name;

    //手机号
    private String phone;

    //工号
    private String serial;

    //登录类型 1 普通账号登录 2 授权登录
    private Integer loginType;
}
